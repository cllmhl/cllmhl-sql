package it.fe.cllmhl.sql;

import it.fe.cllmhl.util.BaseBean;

public class BaseDaoBean extends BaseBean implements IPager {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer recordCount;
    private String orderBy;

    public final String getOrderBy() {
        return orderBy;
    }

    @Override
    public final Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public final Integer getPageSize() {
        return pageSize;
    }

    public final Integer getRecordCount() {
        return recordCount;
    }

    public final void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public final void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public final void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public final void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }
}
