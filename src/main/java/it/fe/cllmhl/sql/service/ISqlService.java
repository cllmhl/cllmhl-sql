package it.fe.cllmhl.sql.service;

import it.fe.cllmhl.sql.orm.IResultSetDecoder;
import it.fe.cllmhl.sql.orm.IRowDecoder;
import it.fe.cllmhl.sql.orm.SqlParameter;
import it.fe.cllmhl.sql.orm.SqlStatement;

import java.util.List;

public interface ISqlService {

    //Query
	int countRecords(String pStrTableName);
	int countRecords(String pStrTableName, SqlStatement pSqlStatement);
	<T> T loadByPrimaryKey(String pStrTableName, List<SqlParameter<? extends Object>> pSQLParameterList, IRowDecoder<T> pRowDecoder);
	<T> List<T> executeQuery(SqlStatement pSqlStatement, IResultSetDecoder<T> pResultSetDecoder);
	
	//Update
	void insert(String pStrTableName, List<SqlParameter<? extends Object>> pSQLParameterList);
	void updateByPrimaryKey(String pStrTableName, List<SqlParameter<? extends Object>> pSQLParameterList);
	void deleteByPrimaryKey(String pStrTableName, List<SqlParameter<? extends Object>> pSQLParameterList);
	int executeUpdate(SqlStatement pSqlStatement);
}