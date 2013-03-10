package it.fe.cllmhl.sql.orm;

import java.util.ArrayList;
import java.util.List;

public class SqlStatement {
    private StringBuffer sql;
    private List<SqlParameter<? extends Object>> parameters;

    public SqlStatement(StringBuffer sql, List<SqlParameter<? extends Object>> parameters) {
		this.sql = sql;
		this.parameters = parameters;
	}

	public SqlStatement(String sql) {
		this.sql = new StringBuffer(sql);
		this.parameters = new ArrayList<SqlParameter<? extends Object>>();
	}

	public SqlStatement() {
		this.sql  = new StringBuffer();
		this.parameters = new ArrayList<SqlParameter<? extends Object>>();
	}

	public String getSql() {
        return sql.toString();
    }

    public void setSql(String sql) {
        this.sql = new StringBuffer(sql);
    }

    public List<SqlParameter<? extends Object>> getParameters() {
        return parameters;
    }

    public void setParameters(List<SqlParameter<? extends Object>> parameters) {
        this.parameters = parameters;
    }
    
	public void append(String sql) {
        this.sql.append(sql);
	}
	
    public void append(String sql, SqlParameter<? extends Object> parameter){
        this.parameters.add(parameter);
    };
    
	public void append(SqlStatement statement) {
		this.sql.append(statement.getSql());
		this.parameters.addAll(statement.getParameters());
	}

	public String toString() {
		StringBuffer lStringBuffer = new StringBuffer(getSql());
		for(SqlParameter<? extends Object> parameter : parameters){
		    int firstPlaceholderIndex = lStringBuffer.indexOf("?");
		    lStringBuffer.replace(firstPlaceholderIndex, firstPlaceholderIndex+1, String.valueOf(parameter.getValue()));
		}
		return lStringBuffer.toString();
	}
}
