package it.fe.cllmhl.sql;

import it.fe.cllmhl.util.ReflectionUtil;

import java.sql.ResultSet;
import java.util.List;

public final class ReflectionRowDecoder<T> implements IRowDecoder<T> {

    private List<Column> columns;
    private Class<T> beanClass;

    public ReflectionRowDecoder(List<Column> columns, Class<T> beanClass) {
        this.columns = columns;
        this.beanClass = beanClass;
    }

    @Override
    public T decodeRow(ResultSet pResultSet) {
        T lBean = ReflectionUtil.createInstance(beanClass);
        for (Column lColumn : columns) {
            ReflectionUtil.setBeanProperty(lBean, lColumn.getJavaName(), lColumn.getValue(pResultSet));
        }
        return lBean;
    }
}
