package it.fe.cllmhl.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleRowResultsetDecoder<T> implements IResultSetDecoder<T> {

    private IRowDecoder<T> rowdecoder;

    public SingleRowResultsetDecoder(IRowDecoder<T> rowdecoder) {
        this.rowdecoder = rowdecoder;
    }

    @Override
    public T decodeRow(ResultSet pResultSet) throws SQLException {
        return rowdecoder.decodeRow(pResultSet);
    }

    @Override
    public int getFirstResult() {
        return 1;
    }

    @Override
    public int getMaxResults() {
        return 1;
    }

}
