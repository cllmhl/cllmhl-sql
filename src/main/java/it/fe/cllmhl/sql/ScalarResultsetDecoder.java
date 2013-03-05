package it.fe.cllmhl.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScalarResultsetDecoder implements IResultSetDecoder<String> {
    @Override
    public String decodeRow(ResultSet pResultSet) throws SQLException {
        return pResultSet.getString(1);
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
