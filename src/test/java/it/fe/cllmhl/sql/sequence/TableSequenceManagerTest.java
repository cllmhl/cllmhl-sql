package it.fe.cllmhl.sql.sequence;

import it.fe.cllmhl.core.CoreErrors;
import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.transaction.IAppTransactionService;
import junit.framework.Assert;

import org.junit.Test;

public class TableSequenceManagerTest {
    
    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TableSequenceManagerTest.class);

    @Test
    public void testGetPrimaryKeyValue() {
        Integer lIntPkValue = TableSequenceManager.getPrimaryKeyValue("test");
        Assert.assertNotNull(lIntPkValue);
    }

    @Test
    public void testNestedTransaction() {
        IAppTransactionService mTransactionService = ServiceLocator.getService(IAppTransactionService.class);
        mTransactionService.begin();
        boolean commit = false;
        try {
            TableSequenceManager.getPrimaryKeyValue("test");
            TableSequenceManager.getPrimaryKeyValue("test");
            //commit = true;
        } catch (UncheckedException e) {// to avoid double logging
            throw e;
        } catch (Throwable t) {
            mLogger.error(t);
            throw new UncheckedException(CoreErrors.FATAL, t.getMessage());
        } finally {
            if (commit)
                mTransactionService.commit();
            else
                mTransactionService.rollback();
        }
        Integer lIntPkValue = TableSequenceManager.getPrimaryKeyValue("test");
        Assert.assertNotNull(lIntPkValue);
    }
}
