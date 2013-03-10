package it.fe.cllmhl.sql.service;

import it.fe.cllmhl.core.INamedServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class SqlServiceFactory implements INamedServiceFactory<ISqlService> {
	
	private static Map<String,ISqlService> mSqlServiceInstancesMap = new HashMap<String,ISqlService>();

	@Override
	public synchronized ISqlService getService(String pStrDatasourceId) {
		ISqlService lSqlService = mSqlServiceInstancesMap.get(pStrDatasourceId);
		if (lSqlService == null) {
		    lSqlService = new SqlService(pStrDatasourceId);
			mSqlServiceInstancesMap.put(pStrDatasourceId, lSqlService);
		}
		return lSqlService;
	}

	@Override
	public Class<ISqlService> getType() {
		return ISqlService.class;
	}
}
