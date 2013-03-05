package it.fe.cllmhl.sql;

import it.fe.cllmhl.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public final class Column {

	//Proprieta di ogni colonna
	private String name;
	private ISqlType<Object> sqlType;
	private boolean isPartOfPk;

	//Costruttore usato dai DAO
	public Column (String name, ISqlType<Object> sqlType, boolean isPartOfPk){
		this.name = name;
		this.sqlType = sqlType;
		this.isPartOfPk = isPartOfPk;
	}

	//Costruttore usato per join con alias..
	public Column (String name, ISqlType<Object> sqlType){
		this.name = name;
		this.sqlType = sqlType;
		this.isPartOfPk = false;
	}

    public String getName() {
        return name;
    }

    public ISqlType<Object> getSqlType() {
        return sqlType;
    }

    public boolean getIsPartOfPk() {
        return isPartOfPk;
    }

	//Gestione del decode sul resultset
	public Object getValue(ResultSet pResultSet) {
		return sqlType.getValue(pResultSet, name);
	}

	//Gestione del set parameter sul PreparedStatement
	public void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, Object pIntParameter) {
		sqlType.setParameter(pPreparedStatement, pIntPosition, pIntParameter);
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
