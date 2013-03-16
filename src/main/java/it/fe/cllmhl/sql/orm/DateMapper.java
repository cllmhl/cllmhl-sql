package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DateMapper implements ISqlMapper<Date> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(DateMapper.class);

    @Override
    public Date getValue(ResultSet pResultSet, String pStrColumnName) {
        Date lDateValue = null;
        try {
            lDateValue = pResultSet.getDate(pStrColumnName);
            if (pResultSet.wasNull()) {
                lDateValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Date value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lDateValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, Date pObjValue) {
        try {
            pPreparedStatement.setDate(pIntPosition, pObjValue);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Date value ", pObjValue, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
