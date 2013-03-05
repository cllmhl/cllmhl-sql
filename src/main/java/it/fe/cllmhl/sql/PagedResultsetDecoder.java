package it.fe.cllmhl.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PagedResultsetDecoder<T> implements IResultSetDecoder<T> {

    private int firstResult = 0;
    private int maxResults = Integer.MAX_VALUE;
    private IRowDecoder<T> rowdecoder;

    public PagedResultsetDecoder(IPager pager, IRowDecoder<T> rowdecoder) {
        firstResult = ((pager.getPageNumber() - 1) * pager.getPageSize()) + 1;
        maxResults = pager.getPageSize();
        this.rowdecoder = rowdecoder;
    }

    @Override
    public T decodeRow(ResultSet pResultSet) throws SQLException {
        return rowdecoder.decodeRow(pResultSet);
    }

    @Override
    public int getFirstResult() {
        return firstResult;
    }

    @Override
    public int getMaxResults() {
        return maxResults;
    }
}
