package it.fe.cllmhl.sql.orm;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface SqlMapping {
    ISqlMapper<String> STRING = new StringMapper();
    ISqlMapper<Integer> INTEGER = new IntegerMapper();
    ISqlMapper<Date> DATE = new DateMapper();
    ISqlMapper<Time> TIME = new TimeMapper();
    ISqlMapper<Timestamp> TIMESTAMP = new TimestampMapper();
    ISqlMapper<BigDecimal> BIGDECIMAL = new BigDecimalMapper();
}
