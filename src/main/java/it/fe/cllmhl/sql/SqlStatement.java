package it.fe.cllmhl.sql;

import java.util.ArrayList;
import java.util.List;

class SqlStatement {
    private StringBuffer sql;
    private List<SqlParameter> parameters;

    public SqlStatement(StringBuffer sql, List<SqlParameter> parameters) {
		this.sql = sql;
		this.parameters = parameters;
	}

	public SqlStatement(String sql) {
		this.sql = new StringBuffer(sql);
		this.parameters = new ArrayList<SqlParameter>();
	}

	public SqlStatement() {
		this.sql  = new StringBuffer();
		this.parameters = new ArrayList<SqlParameter>();
	}

	public String getSql() {
        return sql.toString();
    }

    public void setSql(String sql) {
        this.sql = new StringBuffer(sql);
    }

    public List<SqlParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<SqlParameter> parameters) {
        this.parameters = parameters;
    }
    
	public void append(String sql) {
        this.sql.append(sql);
	}
	
    public void append(String sql, SqlParameter parameter){
        this.parameters.add(parameter);
    };
    
	public void append(SqlStatement statement) {
		this.sql.append(statement.getSql());
		this.parameters.addAll(statement.getParameters());
	}

	public String toString() {
		StringBuffer lStringBuffer = new StringBuffer(getSql());
		for(SqlParameter parameter : parameters){
		    int firstPlaceholderIndex = lStringBuffer.indexOf("?");
		    lStringBuffer.replace(firstPlaceholderIndex, firstPlaceholderIndex+1, String.valueOf(parameter.getValue()));
		}
		return lStringBuffer.toString();
	}
}
