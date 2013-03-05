package it.fe.cllmhl.sql;

import it.fe.cllmhl.core.IError;

public enum SqlErrors implements IError {
    SQL(100),
    // load/update/delete for a missing record
    SQL_MISSING_RECORD(101);
    private final int code;

    SqlErrors(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getResourceBundle() {
        return "cllmhl-sql";
    }
}
