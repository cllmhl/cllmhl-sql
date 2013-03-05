package it.fe.cllmhl.sql.transaction;

import it.fe.cllmhl.core.IServiceFactory;

public class IResTransactionServiceFactory implements IServiceFactory<IResTransactionService> {
	
	@Override
	public IResTransactionService getService() {
		return TransactionServiceFactory.getCurrent();
	}

	@Override
	public Class<IResTransactionService> getType() {
		return IResTransactionService.class;
	}

}
