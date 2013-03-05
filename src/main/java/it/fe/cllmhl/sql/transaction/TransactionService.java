package it.fe.cllmhl.sql.transaction;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;

/**
 * Transaction Service from ALL the point of view. See javax.transaction.TransactionManager/UserTransaction
 * 
 * @author cllmhl
 */
class TransactionService implements IAppTransactionService, IResTransactionService {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TransactionService.class);

    private ITransaction mTransaction;
    private int nestingLevel;

    @Override
    public void begin() {
        if (mTransaction == null) {
            mLogger.info("Starting a new transaction ");
            mTransaction = new Transaction();
            nestingLevel = 0;
        } else {
            mLogger.info("Joining an existing transaction ");
            nestingLevel++;
        }
    }

    @Override
    public void commit() {
        if (nestingLevel == 0) {
            mLogger.info("Transaction commit! ");
            mTransaction.commit();
            mTransaction = null;
        } else {
            nestingLevel--;
        }
    }

    @Override
    public Status getStatus() {
        if (mTransaction == null) {
            return Status.STATUS_NO_TRANSACTION;
        }
        return mTransaction.getStatus();
    }

    @Override
    public ITransaction getTransaction() {
        return mTransaction;
    }

    @Override
    public void rollback() {
        if (nestingLevel == 0) {
            mLogger.info("Transaction rollback! ");
            mTransaction.rollback();
            mTransaction = null;
        } else {
            nestingLevel--;
        }
    }

}
