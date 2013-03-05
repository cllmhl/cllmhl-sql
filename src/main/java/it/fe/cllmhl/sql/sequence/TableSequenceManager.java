package it.fe.cllmhl.sql.sequence;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;
import it.fe.cllmhl.sql.SqlErrors;
import it.fe.cllmhl.sql.transaction.IAppTransactionService;

public final class TableSequenceManager {
    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TableSequenceManager.class);
    private static IAppTransactionService mTransactionService = ServiceLocator.getService(IAppTransactionService.class);

    public static synchronized Integer getPrimaryKeyValue(String pStrTableName) {
        mLogger.debug("getPrimaryKeyValue start");

        Integer lIntPrimaryKeyValue = null;
        Integer lIntNewPrimaryKeyValue = null;
        TableSequenceBean lTableSequenceBean;

        mTransactionService.begin();
        boolean commit = false;
        try {
            lTableSequenceBean = new TableSequenceBean();
            lTableSequenceBean.setIdTable(pStrTableName);
            try {
                lTableSequenceBean = TableSequenceDao.loadByPrimaryKey(lTableSequenceBean);
            } catch (UncheckedException e) {
                if (e.getError() == SqlErrors.SQL_MISSING_RECORD) {
                    //FIXME: configure!
                    lTableSequenceBean.setKeyValue(100);
                    TableSequenceDao.insert(lTableSequenceBean);
                }
            }
            lIntPrimaryKeyValue = lTableSequenceBean.getKeyValue();
            lIntNewPrimaryKeyValue = lIntPrimaryKeyValue + 1;
            lTableSequenceBean.setKeyValue(lIntNewPrimaryKeyValue);
            TableSequenceDao.updateByPrimaryKey(lTableSequenceBean);
            commit = true;
        } finally {
            if (commit) {
                mTransactionService.commit();
            } else {
                mTransactionService.rollback();
            }
        }
        return lIntPrimaryKeyValue;
    }

    private TableSequenceManager() {
    }
}
