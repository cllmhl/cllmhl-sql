package it.fe.cllmhl.sql.transaction;

/**
 * Transaction Service from the point of view of the client application. See javax.transaction.UserTransaction
 * @author cllmhl
 */
public interface IAppTransactionService {

    void begin();

    void commit();

    void rollback();
}
