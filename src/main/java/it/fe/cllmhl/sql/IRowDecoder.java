package it.fe.cllmhl.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowDecoder<T> {
    T decodeRow(ResultSet pResultSet) throws SQLException;
}
