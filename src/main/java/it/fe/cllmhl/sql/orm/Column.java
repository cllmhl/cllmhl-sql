package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public final class Column<T> {

	//Proprieta di ogni colonna
	private String name;
	private IJdbcMapper<T> jdbcMapper;
	private boolean isPartOfPk;

	//Costruttore usato dai DAO
	public Column (String name, IJdbcMapper<T> jdbcMapper, boolean isPartOfPk){
		this.name = name;
		this.jdbcMapper = jdbcMapper;
		this.isPartOfPk = isPartOfPk;
	}

	//Costruttore usato per join con alias..
	public Column (String name, IJdbcMapper<T> jdbcMapper){
		this.name = name;
		this.jdbcMapper = jdbcMapper;
		this.isPartOfPk = false;
	}

    public String getName() {
        return name;
    }

    public boolean getIsPartOfPk() {
        return isPartOfPk;
    }

	//Gestione del decode sul resultset
	public T getValue(ResultSet pResultSet) {
		return jdbcMapper.getValue(pResultSet, name);
	}

	//Gestione del set parameter sul PreparedStatement
	public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, T pValue) {
		jdbcMapper.setParameter(pPreparedStatement, pIntPosition, pValue);
	}
	
	//Nome della variabile java
	public String getJavaName() {
		return StringUtil.convertToJavaVariableName(name);
	}

	@Override
	public String toString() {
		return name + "(" + jdbcMapper + ")";
	}
}
