package it.fe.cllmhl.sql.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowDecoder<T> {
    T decodeRow(ResultSet pResultSet) throws SQLException;
}
