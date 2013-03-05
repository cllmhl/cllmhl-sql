package it.fe.cllmhl.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface ISqlType<T> {
	T getValue(ResultSet pResultSet, String pStrColumnName);
	void setParameter(PreparedStatement pPreparedStatement, int pIntPosition, T pObjParameter);
}

