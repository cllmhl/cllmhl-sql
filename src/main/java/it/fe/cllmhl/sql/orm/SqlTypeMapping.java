package it.fe.cllmhl.sql.orm;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public enum SqlTypeMapping {
    INTEGER(Types.INTEGER, Integer.class),
    VARCHAR(Types.VARCHAR, String.class),
    DATE(Types.DATE, Date.class),
    TIME(Types.TIME, Time.class),
    TIMESTAMP(Types.TIMESTAMP, Timestamp.class),
    DECIMAL(Types.DECIMAL, BigDecimal.class),
    CHAR(Types.CHAR, String.class),
    LONGVARCHAR(Types.LONGVARCHAR, String.class);

    private int sqlType;
    private Class<?> javaType;

    private static final Map<Integer, SqlTypeMapping> SQLTYPE_TO_ENUM = new HashMap<Integer, SqlTypeMapping>();

    static {
        for (SqlTypeMapping lSqlTypeMapping : values()) {
            SQLTYPE_TO_ENUM.put(lSqlTypeMapping.sqlType, lSqlTypeMapping);
        }
    }

    public static SqlTypeMapping fromSqlType(int sqlType) {
        return SQLTYPE_TO_ENUM.get(sqlType);
    }

    SqlTypeMapping(int sqlType, Class<?> javaType) {
        this.sqlType = sqlType;
        this.javaType = javaType;
    }

    public int getSqlType() {
        return sqlType;
    }
    
    public Class<?> getJavaType() {
        return javaType;
    }
}
