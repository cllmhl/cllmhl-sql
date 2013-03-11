package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

class TimestampMapper implements IJdbcMapper<Timestamp> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TimestampMapper.class);

    @Override
    public Timestamp getValue(ResultSet pResultSet, String pStrColumnName) {
        Timestamp lObjValue = null;
        try {
            lObjValue = pResultSet.getTimestamp(pStrColumnName);
            if (pResultSet.wasNull()) {
                lObjValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Timestamp value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObjValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, Timestamp pObjValue) {
        try {
            pPreparedStatement.setTimestamp(pIntPosition, pObjValue);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Timestamp value ", pObjValue, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
