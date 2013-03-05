package it.fe.cllmhl.sql.transaction;

import it.fe.cllmhl.core.IServiceFactory;

public class IAppTransactionServiceFactory implements IServiceFactory<IAppTransactionService> {
	
	@Override
	public IAppTransactionService getService() {
		return TransactionServiceFactory.createOrGetCurrent();
	}

	@Override
	public Class<IAppTransactionService> getType() {
		return IAppTransactionService.class;
	}

}
