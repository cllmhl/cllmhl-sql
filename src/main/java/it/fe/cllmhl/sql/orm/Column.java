package it.fe.cllmhl.sql.orm;

import it.fe.cllmhl.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public final class Column<T> {

	//Proprieta di ogni colonna
	private String name;
	private ISqlType<T> sqlType;
	private boolean isPartOfPk;

	//Costruttore usato dai DAO
	public Column (String name, ISqlType<T> sqlType, boolean isPartOfPk){
		this.name = name;
		this.sqlType = sqlType;
		this.isPartOfPk = isPartOfPk;
	}

	//Costruttore usato per join con alias..
	public Column (String name, ISqlType<T> sqlType){
		this.name = name;
		this.sqlType = sqlType;
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
		return sqlType.getValue(pResultSet, name);
	}

	//Gestione del set parameter sul PreparedStatement
	public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, T pValue) {
		sqlType.setParameter(pPreparedStatement, pIntPosition, pValue);
	}
	
	//Nome della variabile java
	public String getJavaName() {
		return StringUtil.convertToJavaVariableName(name);
	}

	@Override
	public String toString() {
		return name + "(" + sqlType + ")";
	}
}
