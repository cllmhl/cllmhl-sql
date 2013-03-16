package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class IntegerMapper implements ISqlMapper<Integer> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(IntegerMapper.class);

    @Override
    public Integer getValue(ResultSet pResultSet, String pStrColumnName) {
        Integer lObjValue = null;
        try {
            lObjValue = pResultSet.getInt(pStrColumnName);
            if (pResultSet.wasNull()) {
                lObjValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Integer value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObjValue;
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
