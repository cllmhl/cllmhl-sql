package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class StringMapper implements IJdbcMapper<String> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(StringMapper.class);

    @Override
    public String getValue(ResultSet pResultSet, String pStrColumnName) {
        String lObjValue = null;
        try {
            lObjValue = pResultSet.getString(pStrColumnName);
            if (pResultSet.wasNull()) {
                lObjValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting String value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObjValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, String pObjParameter) {
        try {
            pPreparedStatement.setString(pIntPosition, pObjParameter);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting String value ", pObjParameter, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
