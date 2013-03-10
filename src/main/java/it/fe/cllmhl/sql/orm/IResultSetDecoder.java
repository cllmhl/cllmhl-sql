package it.fe.cllmhl.sql.orm;


public interface IResultSetDecoder<T> extends IRowDecoder<T> {
    int getFirstResult();

    int getMaxResults();
}
