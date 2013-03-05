package it.fe.cllmhl.sql.transaction;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;

public final class TransactionServiceFactory {
	
	private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TransactionService.class);
	
	private static final ThreadLocal<TransactionService> TRANSACTION_SERVICE = new ThreadLocal<TransactionService>();

	public static TransactionService createOrGetCurrent() {
		TransactionService lTransactionService = TRANSACTION_SERVICE.get();
		if (lTransactionService == null) {
            mLogger.debug("Creating a new use TransactionService for this thread");
            lTransactionService = new TransactionService();
            TRANSACTION_SERVICE.set(lTransactionService);
        } else {
            mLogger.debug("Using the TransactionService already instantiated for this thread ");
        }
		return lTransactionService;
	}
	
	public static TransactionService getCurrent() {
		return TRANSACTION_SERVICE.get();
	}
	
	private TransactionServiceFactory(){}
}
