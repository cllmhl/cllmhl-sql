package it.fe.cllmhl.sql.orm;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface JdbcMapping {
    IJdbcMapper<String> STRING = new StringMapper();
    IJdbcMapper<Integer> INTEGER = new IntegerMapper();
    IJdbcMapper<Date> DATE = new DateMapper();
    IJdbcMapper<Time> TIME = new TimeMapper();
    IJdbcMapper<Timestamp> TIMESTAMP = new TimestampMapper();
    IJdbcMapper<BigDecimal> BIGDECIMAL = new BigDecimalMapper();
}
