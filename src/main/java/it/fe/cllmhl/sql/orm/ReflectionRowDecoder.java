package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.util.ReflectionUtil;

import java.sql.ResultSet;
import java.util.List;

public final class ReflectionRowDecoder<T> implements IRowDecoder<T> {

    private List<Column<? extends Object>> columns;
    private Class<T> beanClass;

    public ReflectionRowDecoder(List<Column<? extends Object>> columns, Class<T> beanClass) {
        this.columns = columns;
        this.beanClass = beanClass;
    }

    @Override
    public T decodeRow(ResultSet pResultSet) {
        T lBean = ReflectionUtil.createInstance(beanClass);
        for (Column<? extends Object> lColumn : columns) {
            ReflectionUtil.setBeanProperty(lBean, lColumn.getJavaName(), lColumn.getValue(pResultSet));
        }
        return lBean;
    }
}
