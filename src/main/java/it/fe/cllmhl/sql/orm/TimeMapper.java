package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

class TimeMapper implements IJdbcMapper<Time> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TimeMapper.class);

    @Override
    public Time getValue(ResultSet pResultSet, String pStrColumnName) {
        Time lObjValue = null;
        try {
            lObjValue = pResultSet.getTime(pStrColumnName);
            if (pResultSet.wasNull()) {
                lObjValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Time value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObjValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, Time pObjValue) {
        try {
            pPreparedStatement.setTime(pIntPosition, pObjValue);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Time value ", pObjValue, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
