package it.fe.cllmhl.sql.transaction;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class Transaction implements ITransaction {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(Transaction.class);
    private List<IResource> resources = new ArrayList<IResource>();
    private Status status = Status.STATUS_ACTIVE;

    @Override
    public void commit() {
        status = Status.STATUS_COMMITTING;
        for (IResource lResource : resources) {
            mLogger.info("Transaction commit for resource ", lResource);
            lResource.commit();
        }
        status = Status.STATUS_COMMITTED;
    }

    @Override
    public void delistResource(IResource res) {
        resources.remove(res);
    }

    @Override
    public void enlistResource(IResource res) {
        resources.add(res);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void rollback() {
        status = Status.STATUS_ROLLING_BACK;
        for (IResource lResource : resources) {
            mLogger.info("Transaction rollback for resource ", lResource);
            lResource.rollback();
        }
        status = Status.STATUS_ROLLEDBACK;
    }

}
