package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BigDecimalMapper implements ISqlMapper<BigDecimal> {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(BigDecimalMapper.class);

    @Override
    public BigDecimal getValue(ResultSet pResultSet, String pStrColumnName) {
        BigDecimal lObjValue = null;
        try {
            lObjValue = pResultSet.getBigDecimal(pStrColumnName);
            if (pResultSet.wasNull()) {
                lObjValue = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting BigDecimal value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObjValue;
    }

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, BigDecimal pObjValue) {
        try {
            pPreparedStatement.setBigDecimal(pIntPosition, pObjValue);
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting BigDecimal value ", pObjValue, " at position ", pIntPosition);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }

}
