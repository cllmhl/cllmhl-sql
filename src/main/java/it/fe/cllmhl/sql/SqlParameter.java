package it.fe.cllmhl.sql;



public class SqlParameter {
    private Column column;
    private Object value;

    public SqlParameter(Column column, Object value) {
        this.column = column;
        this.value = value;
    }

    public final Column getColumn() {
        return column;
    }

    public final Object getValue() {
        return value;
    }

    public final void setColumn(Column column) {
        this.column = column;
    }

    public final void setValue(Object value) {
        this.value = value;
    }
}
