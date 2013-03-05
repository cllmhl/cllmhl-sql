package it.fe.cllmhl.sql;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class SqlService implements ISqlService {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(SqlService.class);

    private static Map<String, SqlService> mSqlServiceInstancesMap = new HashMap<String, SqlService>();

    public static synchronized SqlService getInstance(String pStrConnectioId) {
        SqlService lSqlService = mSqlServiceInstancesMap.get(pStrConnectioId);
        if (lSqlService == null) {
            lSqlService = new SqlService(pStrConnectioId);
            mSqlServiceInstancesMap.put(pStrConnectioId, lSqlService);
        }
        return lSqlService;
    }

    private ConnectionResource mConnectionResource;

    SqlService(String pStrConnectionId) {
        mConnectionResource = new ConnectionResource(pStrConnectionId);
    }

    @Override
    public int countRecords(String pStrTableName) {
        return countRecords(pStrTableName, new SqlStatement());
    }

    @Override
    public int countRecords(String pStrTableName, SqlStatement pSqlStatement) {
        mLogger.debug("Start countRecords ", pStrTableName);

        StringBuffer lStringBufferSQL;
        lStringBufferSQL = new StringBuffer("SELECT count(*)");
        lStringBufferSQL.append(" FROM ");
        lStringBufferSQL.append(pStrTableName);

        // I add the WhereCondition
        lStringBufferSQL.append(pSqlStatement.getSql());

        List<String> lStringList = executeQuery(new SqlStatement(lStringBufferSQL, pSqlStatement.getParameters()), new ScalarResultsetDecoder());
        Integer lIntRecordCount = Integer.valueOf(lStringList.get(0));

        mLogger.debug("Finish countRecords returning ", lIntRecordCount);
        return lIntRecordCount;
    }

    @Override
    public void deleteByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList) {
        mLogger.debug("Start deleteByPrimaryKey");

        StringBuffer lStringBufferSQL = new StringBuffer("DELETE FROM ");
        lStringBufferSQL.append(pStrTableName);

        // Where condition sulla primary key
        int numberOfColumns = 0;
        for (SqlParameter lSQLParameter : pSQLParameterList) {
            numberOfColumns++;
            if (numberOfColumns == 1) {
                lStringBufferSQL.append(" WHERE ");
            } else {
                lStringBufferSQL.append(" AND ");
            }
            lStringBufferSQL.append(lSQLParameter.getColumn().getName());
            lStringBufferSQL.append("=?");
        }

        // Execute!!
        int lIntRecordUpdated = this.executeUpdate(new SqlStatement(lStringBufferSQL, pSQLParameterList));

        if (lIntRecordUpdated == 0) {
            mLogger.error("Concurrency problem in Table ", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL_MISSING_RECORD, "No record found");
        }
        if (lIntRecordUpdated > 1) {
            mLogger.error("Multiple update", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL, "Multiple delete by primary key");
        }
        mLogger.debug("finish deleteByPrimaryKey");
    }

    @Override
    public void insert(String pStrTableName, List<SqlParameter> pSQLParameterList) {
        mLogger.debug("Start insert");

        StringBuffer lStringBufferSQL = new StringBuffer();
        lStringBufferSQL.append("INSERT INTO ");
        lStringBufferSQL.append(pStrTableName);
        lStringBufferSQL.append("(");
        // Colums
        int numberOfColumns = 0;
        for (SqlParameter lSQLParameter : pSQLParameterList) {
            numberOfColumns++;
            if (numberOfColumns != 1) {
                lStringBufferSQL.append(",");
            }
            lStringBufferSQL.append(lSQLParameter.getColumn().getName());
        }
        lStringBufferSQL.append(")");

        // Values
        lStringBufferSQL.append(" values (");
        // Placeholders
        for (int i = 1; i <= numberOfColumns; i++) {
            if (i > 1) {
                lStringBufferSQL.append(",");
            }
            lStringBufferSQL.append("?");
        }
        lStringBufferSQL.append(")");

        executeUpdate(new SqlStatement(lStringBufferSQL, pSQLParameterList));
        mLogger.debug("End insert");
    }

    @Override
    public <T> T loadByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList, IRowDecoder<T> pRowDecoder) {
        mLogger.debug("Start loadByPrimaryKey");

        StringBuffer lStringBufferSQL = new StringBuffer("SELECT * FROM ");
        lStringBufferSQL.append(pStrTableName);

        // Where condition on PK
        int numberOfColumns = 0;
        for (SqlParameter lSQLParameter : pSQLParameterList) {
            numberOfColumns++;
            if (numberOfColumns == 1) {
                lStringBufferSQL.append(" WHERE ");
            } else {
                lStringBufferSQL.append(" AND ");
            }
            lStringBufferSQL.append(lSQLParameter.getColumn().getName());
            lStringBufferSQL.append("=?");
        }

        // Execute!!
        List<T> lBeanList = executeQuery(new SqlStatement(lStringBufferSQL, pSQLParameterList), new SingleRowResultsetDecoder<T>(pRowDecoder));

        if (lBeanList.size() == 1) {
            mLogger.debug("finish loadByPrimaryKey");
            return lBeanList.get(0);
        } else if (lBeanList.size() == 0) {
            mLogger.error("finish loadByPrimaryKey but no records found in Table ", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL_MISSING_RECORD, "No record found");
        } else {
            mLogger.error("More than one record have same Primary Key in Table ", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL, "More than one record have same Primary Key in Table " + pStrTableName);
        }
    }

    @Override
    public void updateByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList) {
        mLogger.debug("Start updateByPrimaryKey");

        StringBuffer lStringBufferSQL = new StringBuffer("UPDATE ");
        lStringBufferSQL.append(pStrTableName);
        lStringBufferSQL.append(" SET ");

        List<SqlParameter> lSQLParameterList = new ArrayList<SqlParameter>();

        // Columns
        int numberOfColumns = 0;
        for (SqlParameter lSQLParameter : pSQLParameterList) {
            if (lSQLParameter.getColumn().getIsPartOfPk()) {
                continue;
            }
            numberOfColumns++;
            if (numberOfColumns != 1) {
                lStringBufferSQL.append(",");
            }
            lStringBufferSQL.append(lSQLParameter.getColumn().getName());
            lStringBufferSQL.append("=?");
            lSQLParameterList.add(lSQLParameter);
        }

        // Where condition on primary key
        numberOfColumns = 0;
        for (SqlParameter lSQLParameter : pSQLParameterList) {
            if (!lSQLParameter.getColumn().getIsPartOfPk()) {
                continue;
            }
            numberOfColumns++;
            if (numberOfColumns == 1) {
                lStringBufferSQL.append(" WHERE ");
            } else {
                lStringBufferSQL.append(" AND ");
            }
            lStringBufferSQL.append(lSQLParameter.getColumn().getName());
            lStringBufferSQL.append("=?");
            lSQLParameterList.add(lSQLParameter);
        }

        // Execute!!
        int lIntRecordUpdated = this.executeUpdate(new SqlStatement(lStringBufferSQL, lSQLParameterList));

        if (lIntRecordUpdated == 0) {
            mLogger.error("Concurrency problem in Table ", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL_MISSING_RECORD, "No record found");
        }
        if (lIntRecordUpdated > 1) {
            mLogger.error("Multiple update", pStrTableName);
            throw new UncheckedException(SqlErrors.SQL, "Multiple update by primary key");
        }
        mLogger.debug("finish updateByPrimaryKey");
    }

    @Override
    public <T> List<T> executeQuery(SqlStatement pSqlStatement, IResultSetDecoder<T> pResultSetDecoder) {
       return  mConnectionResource.executeQuery(pSqlStatement, pResultSetDecoder);
    }

    @Override
    public int executeUpdate(SqlStatement pSqlStatement) {
        return mConnectionResource.executeUpdate(pSqlStatement);
    }
}
