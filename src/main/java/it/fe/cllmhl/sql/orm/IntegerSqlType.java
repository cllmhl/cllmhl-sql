package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class IntegerSqlType implements ISqlType<Integer> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(IntegerSqlType.class);

    @Override
    public Integer getValue(ResultSet pResultSet, String pStrColumnName) {
        Integer lIntValue = null;
        try {
            lIntValue = pResultSet.getInt(pStrColumnName);
            if (pResultSet.wasNull()) {
                lIntValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Integer value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lIntValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, Integer pObjParameter) {
        try {
            pPreparedStatement.setInt(pIntPosition, pObjParameter);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Integer value ", pObjParameter, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
