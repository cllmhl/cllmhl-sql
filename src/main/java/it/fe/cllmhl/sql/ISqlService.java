package it.fe.cllmhl.sql;

import java.util.List;

public interface ISqlService {

    //Query
	int countRecords(String pStrTableName);
	int countRecords(String pStrTableName, SqlStatement pSqlStatement);
	<T> T loadByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList, IRowDecoder<T> pRowDecoder);
	<T> List<T> executeQuery(SqlStatement pSqlStatement, IResultSetDecoder<T> pResultSetDecoder);
	
	//Update
	void insert(String pStrTableName, List<SqlParameter> pSQLParameterList);
	void updateByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList);
	void deleteByPrimaryKey(String pStrTableName, List<SqlParameter> pSQLParameterList);
	int executeUpdate(SqlStatement pSqlStatement);
}