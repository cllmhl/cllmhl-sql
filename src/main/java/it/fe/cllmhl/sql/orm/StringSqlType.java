package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class StringSqlType implements ISqlType<String> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(StringSqlType.class);

    @Override
    public String getValue(ResultSet pResultSet, String pStrColumnName) {
        String lStrValue = null;
        try {
            lStrValue = pResultSet.getString(pStrColumnName);
            if (pResultSet.wasNull()) {
                lStrValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Integer value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lStrValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, String pObjParameter) {
        try {
            pPreparedStatement.setString(pIntPosition, pObjParameter);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Integer value ", pObjParameter, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
