package it.fe.cllmhl.sql.service;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;
import it.fe.cllmhl.sql.orm.IResultSetDecoder;
import it.fe.cllmhl.sql.orm.SqlParameter;
import it.fe.cllmhl.sql.orm.SqlStatement;
import it.fe.cllmhl.sql.transaction.IResTransactionService;
import it.fe.cllmhl.sql.transaction.IResource;
import it.fe.cllmhl.sql.transaction.ITransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class ConnectionResource implements IResource {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(ConnectionResource.class);
    private String mStrConnectionId = null;

    // ThreadLocal for transaction management
    private ThreadLocal<Connection> mConnection = new ThreadLocal<Connection>();

    public ConnectionResource(String pStrConnectionId) {
        mStrConnectionId = pStrConnectionId;
    }

    private Connection getConnection() throws SQLException {
        mLogger.debug("getConnection start");
        Connection lConnection;
        // our behavior depends on transaction status
        ITransaction lCurrentTransaction = getCurrentTransaction();
        // Under transaction
        if (lCurrentTransaction != null) {
            lConnection = mConnection.get();
            // first request for a connection on this transaction
            if (lConnection == null) {
                mLogger.debug("We do NOT have a connection in ThreadLocal map! We build the transaction resource ");
                lConnection = DatasourceManager.getConnection(mStrConnectionId);
                mLogger.debug("Setting autocommit to false on ", lConnection);
                lConnection.setAutoCommit(false);
                mLogger.info("We register this connection with the transaction ");
                lCurrentTransaction.enlistResource(this);
                mConnection.set(lConnection);
                // The connection is already fine for this transaction
            } else {
                mLogger.debug("We have a connection in ThreadLocal map! We use it ", lConnection);
            }
            // NOT under transaction
        } else {
            lConnection = DatasourceManager.getConnection(mStrConnectionId);
        }
        mLogger.debug("getConnection finish returning ", lConnection);
        return lConnection;
    }

    private void releaseResources(ResultSet pResultSet, Statement pStatement, Connection pConnection) {
        mLogger.debug("releaseResources start on connection ", pConnection);
        if (pResultSet != null) {
            try {
                pResultSet.close();
            } catch (SQLException e) {
                mLogger.error(e, "Error closing the resultset");
            }
        }
        if (pStatement != null) {
            try {
                pStatement.close();
            } catch (SQLException e) {
                mLogger.error(e, "Error closing the Statement");
            }
        }
        // We close the connection only if we are NOT Under transaction
        if (getCurrentTransaction() == null) {
            mLogger.debug("We are NOT under transaction so we close and release the connection ", pConnection);
            mConnection.remove();
            if (pConnection != null) {
                try {
                    pConnection.close();
                } catch (SQLException e) {
                    mLogger.error(e, "Error closing the Statement");
                }
            }
        } else {
            mLogger.debug("We are under transaction so we DO NOT close and release the connection ", pConnection);
        }
        mLogger.debug("releaseResources finish on connection ", pConnection);
    }

    public <T> List<T> executeQuery(SqlStatement pSqlStatement, IResultSetDecoder<T> pResultSetDecoder) {
        mLogger.info("Start executeQuery ", pSqlStatement);

        Connection lConnection = null;
        PreparedStatement lPreparedStatement = null;
        ResultSet lResultSet = null;
        List<T> lBeanList;

        try {
            // We get the connection
            lConnection = getConnection();
            // Create the PreparedStatement
            lPreparedStatement = lConnection.prepareStatement(pSqlStatement.getSql());
            // Parameters setting
            setParameters(lPreparedStatement, pSqlStatement.getParameters());
            // Execute
            lResultSet = lPreparedStatement.executeQuery();
            // Decode
            lBeanList = decodeRows(lResultSet, pResultSetDecoder);
        } catch (SQLException e) {
            mLogger.error(e);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        } finally {
            releaseResources(lResultSet, lPreparedStatement, lConnection);
        }
        mLogger.debug("End executeQuery");
        return lBeanList;
    }

    public int executeUpdate(SqlStatement pSqlStatement) {
        Connection lConnection = null;
        PreparedStatement lPreparedStatement = null;
        int lIntRecords;

        mLogger.info("Start executeUpdate: ", pSqlStatement);
        try {
            // We get the connection
            lConnection = getConnection();
            // Create the PreparedStatement
            lPreparedStatement = lConnection.prepareStatement(pSqlStatement.getSql());
            // Parameters setting
            setParameters(lPreparedStatement, pSqlStatement.getParameters());
            // Execute
            lIntRecords = lPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            mLogger.error(e, "SQL error code ", e.getErrorCode());
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        } finally {
            releaseResources(null, lPreparedStatement, lConnection);
        }
        mLogger.debug("Finish executeUpdate on ", "" + lIntRecords, " records");
        return lIntRecords;
    }

    @Override
    public void commit() {
        Connection lConnection = mConnection.get();
        mLogger.debug("commit start on connection ", lConnection);
        try {
            lConnection.commit();
            lConnection.setAutoCommit(true);
        } catch (SQLException e) {
            mLogger.error(e);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        } finally {
            try {
                lConnection.close();
            } catch (SQLException e) {
                mLogger.error(e);
            }
            mConnection.remove();
        }
        mLogger.debug("commit finish on connection ", lConnection);
    }

    @Override
    public void rollback() {
        Connection lConnection = mConnection.get();
        mLogger.debug("rollback start on connection ", lConnection);
        try {
            lConnection.rollback();
            lConnection.setAutoCommit(true);
        } catch (SQLException e) {
            mLogger.error(e);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        } finally {
            try {
                lConnection.close();
            } catch (SQLException e) {
                mLogger.error(e);
            }
            mConnection.remove();
        }
        mLogger.debug("rollback finish on connection ", lConnection);
    }

    private <T> List<T> decodeRows(ResultSet pResultSet, IResultSetDecoder<T> pResultSetDecoder) throws SQLException {
        mLogger.debug("Start decodeRows");
        List<T> lBeanList = new java.util.ArrayList<T>();
        boolean lBolAfterFirstAndBeforeLast = true;

        // I go to the first record to decode
        int lIntRecordCount = 0;
        while (lBolAfterFirstAndBeforeLast && (lIntRecordCount < pResultSetDecoder.getFirstResult())) {
            lBolAfterFirstAndBeforeLast = pResultSet.next();
            lIntRecordCount++;
        }

        // Counter of the decoded rows
        int lIntDecodedRows = 0;
        // I try to decode lIntRowsToDecode
        if (lBolAfterFirstAndBeforeLast) {
            do {
                lBeanList.add(pResultSetDecoder.decodeRow(pResultSet));
                lIntDecodedRows++;
            } while (pResultSet.next() && (lIntDecodedRows < pResultSetDecoder.getMaxResults()));
        }

        mLogger.debug("Finish decodeRows ", lIntDecodedRows, " rows decoded");
        return lBeanList;
    }

    private void setParameters(PreparedStatement pPreparedStatement, List<SqlParameter<? extends Object>> pSQLParameterList) {
        int lIntPosition = 1;
        for (SqlParameter<? extends Object> lSqlParameter : pSQLParameterList) {
            mLogger.debug("Setting parameter ", lIntPosition, " to ", lSqlParameter.getValue());
            lSqlParameter.setParameter(pPreparedStatement, lIntPosition);
            lIntPosition++;
        }
    }

    private ITransaction getCurrentTransaction() {
        mLogger.debug("getCurrentTransaction start");
        ITransaction lCurrentTransaction = null;
        IResTransactionService mTransactionService = ServiceLocator.getService(IResTransactionService.class);
        // Under transaction
        if ((mTransactionService != null) && (mTransactionService.getTransaction() != null)) {
            mLogger.info("We are under transaction. We register this connection with the transaction ");
            lCurrentTransaction = mTransactionService.getTransaction();
        } else {
            mLogger.debug("We are NOT under transaction");
        }
        mLogger.debug("getCurrentTransaction finish returning ", lCurrentTransaction);
        return lCurrentTransaction;
    }
}
