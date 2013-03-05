package it.fe.cllmhl.sql.transaction;

/**
 * Transaction Service from the point of view of the resource participating. See javax.transaction.TransactionManager
 * 
 * @author cllmhl
 */
public interface IResTransactionService {

    Status getStatus();

    ITransaction getTransaction();

}
