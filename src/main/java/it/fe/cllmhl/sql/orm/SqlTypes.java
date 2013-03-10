package it.fe.cllmhl.sql.orm;

public interface SqlTypes {
    ISqlType<String> VARCHAR = new StringSqlType();
    ISqlType<Integer> INTEGER = new IntegerSqlType();
}
