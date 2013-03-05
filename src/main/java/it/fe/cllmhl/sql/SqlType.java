package it.fe.cllmhl.sql;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public enum SqlType implements ISqlType<Object> {
    INTEGER(Types.INTEGER, Integer.class) {
        @Override
        protected Integer getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getInt(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setInt(pIntPosition, (Integer) pObject);
        }
    },
    VARCHAR(Types.VARCHAR, String.class) {
        @Override
        protected String getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getString(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setString(pIntPosition, (String) pObject);
        }
    },
    DATE(Types.DATE, Date.class) {
        @Override
        protected Date getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getDate(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setDate(pIntPosition, (Date) pObject);
        }
    },
    TIME(Types.TIME, Time.class) {
        @Override
        protected Time getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getTime(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setTime(pIntPosition, (Time) pObject);
        }
    },
    TIMESTAMP(Types.TIMESTAMP, Timestamp.class) {
        @Override
        protected Timestamp getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getTimestamp(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setTimestamp(pIntPosition, (Timestamp) pObject);
        }
    },
    DECIMAL(Types.DECIMAL, BigDecimal.class) {
        @Override
        protected BigDecimal getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getBigDecimal(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setBigDecimal(pIntPosition, (BigDecimal) pObject);
        }
    },
    CHAR(Types.CHAR, String.class) {
        @Override
        protected String getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getString(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setString(pIntPosition, (String) pObject);
        }
    },
    LONGVARCHAR(Types.LONGVARCHAR, String.class) {
        @Override
        protected String getJdbcValue(ResultSet pResultSet, String pStrColumnName) throws SQLException {
            return pResultSet.getString(pStrColumnName);
        }

        @Override
        protected void setJdbcValue(PreparedStatement pPreparedStatement, int pIntPosition, Object pObject) throws SQLException {
            pPreparedStatement.setString(pIntPosition, (String) pObject);
        }
    };

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(SqlType.class);

    private int sqlType;
    private Class<?> javaType;

    private static final Map<Integer, SqlType> SQLTYPE_TO_ENUM = new HashMap<Integer, SqlType>();

    static {
        for (SqlType lSqlType : values()) {
            SQLTYPE_TO_ENUM.put(lSqlType.sqlType, lSqlType);
        }
    }

    public static SqlType fromSqlType(int sqlType) {
        return SQLTYPE_TO_ENUM.get(sqlType);
    }

    SqlType(int sqlType, Class<?> javaType) {
        this.sqlType = sqlType;
        this.javaType = javaType;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    protected abstract Object getJdbcValue(ResultSet resultSet, String columnName) throws SQLException;

    @Override
    public Object getValue(ResultSet pResultSet, String pStrColumnName) {
        Object lObject = null;
        try {
            lObject = this.getJdbcValue(pResultSet, pStrColumnName);
            if (pResultSet.wasNull()) {
                lObject = null;
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while getting Integer value for column ", pStrColumnName);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
        return lObject;
    }

    protected abstract void setJdbcValue(PreparedStatement preparedStatement, int position, Object parameter) throws SQLException;

    @Override
    public void setParameter(PreparedStatement pPreparedStatement, int pIntPos, Object pObject) {
        try {
            if (pObject == null) {
                pPreparedStatement.setNull(pIntPos, sqlType);
            } else {
                setJdbcValue(pPreparedStatement, pIntPos, pObject);
            }
        } catch (SQLException e) {
            mLogger.error(e, "Error while setting Integer value ", pObject, " at position ", pIntPos);
            throw new UncheckedException(e, SqlErrors.SQL, e.getMessage());
        }
    }
}
