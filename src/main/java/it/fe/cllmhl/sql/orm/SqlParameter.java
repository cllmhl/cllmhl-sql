package it.fe.cllmhl.sql.orm;

import java.sql.PreparedStatement;

public class SqlParameter<T> {
    private Column<T> column;
    private T value;

    public SqlParameter(Column<T> column, T value) {
        this.column = column;
        this.value = value;
    }

    public boolean getIsPartOfPk() {
        return column.getIsPartOfPk();
    }

    public String getName() {
        return column.getName();
    }

    public final T getValue() {
        return value;
    }

    public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition) {
        column.setParameter(pPreparedStatement, pIntPosition, value);
    }

}
