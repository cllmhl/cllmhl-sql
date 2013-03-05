package it.fe.cllmhl.sql;

public interface IResultSetDecoder<T> extends IRowDecoder<T> {
    int getFirstResult();

    int getMaxResults();
}
