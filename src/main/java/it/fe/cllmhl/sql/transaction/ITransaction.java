package it.fe.cllmhl.sql.transaction;

/**
 * A running transaction. See javax.transaction.Transaction
 * 
 * @author cllmhl
 */
public interface ITransaction {
    void commit();

    void delistResource(IResource res);

    void enlistResource(IResource res);

    Status getStatus();

    void rollback();

}
