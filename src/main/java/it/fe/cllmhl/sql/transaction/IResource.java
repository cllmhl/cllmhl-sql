package it.fe.cllmhl.sql.transaction;

/**
 * A transactionable resource. See avax.transaction.XAResource
 * 
 * @author cllmhl
 */
public interface IResource {
    void commit();

    void rollback();
}
