package it.fe.cllmhl.sql.sequence;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.sql.orm.SqlParameter;
import it.fe.cllmhl.sql.sequence.TableSequence.TableSequenceRowDecoder;
import it.fe.cllmhl.sql.service.ISqlService;

import java.util.ArrayList;
import java.util.List;

final class TableSequenceDao {
    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TableSequenceDao.class);

    private static ISqlService mSqlService = ServiceLocator.getServiceByName(ISqlService.class, "POOL");

    public static TableSequenceBean insert(TableSequenceBean pTableSequenceBean) {
        mLogger.debug("Start insert");

        List<SqlParameter<? extends Object>> lSqlParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // idTable
        if (pTableSequenceBean.getIdTable() != null) {
            lSqlParameterList.add(new SqlParameter<String>(TableSequence.ID_TABLE, pTableSequenceBean.getIdTable()));
        }
        // table_sequence.key_value
        if (pTableSequenceBean.getKeyValue() != null) {
            lSqlParameterList.add(new SqlParameter<Integer>(TableSequence.KEY_VALUE, pTableSequenceBean.getKeyValue()));
        }

        mSqlService.insert(TableSequence.NAME, lSqlParameterList);
        mLogger.debug("Finish insert");
        return pTableSequenceBean;
    }

    public static TableSequenceBean loadByPrimaryKey(TableSequenceBean pTableSequenceBean) {
        mLogger.debug("Start loadByPrimaryKey");

        List<SqlParameter<? extends Object>> lSQLParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // table_sequence.id_table
        lSQLParameterList.add(new SqlParameter<String>(TableSequence.ID_TABLE, pTableSequenceBean.getIdTable()));

        // Execute!!
        TableSequenceBean lTableSequenceBean = mSqlService.loadByPrimaryKey(TableSequence.NAME, lSQLParameterList, new TableSequenceRowDecoder());

        mLogger.debug("finish loadByPrimaryKey");
        return lTableSequenceBean;
    }

    public static void updateByPrimaryKey(TableSequenceBean pTableSequenceBean) {
        mLogger.debug("Start updateByPrimaryKey");

        List<SqlParameter<? extends Object>> lSQLParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // table_sequence.id_table
        lSQLParameterList.add(new SqlParameter<String>(TableSequence.ID_TABLE, pTableSequenceBean.getIdTable()));

        // table_sequence.key_value
        lSQLParameterList.add(new SqlParameter<Integer>(TableSequence.KEY_VALUE, pTableSequenceBean.getKeyValue()));

        // Execute!!
        mSqlService.updateByPrimaryKey(TableSequence.NAME, lSQLParameterList);

        mLogger.debug("finish updateByPrimaryKey");
    }

    private TableSequenceDao() {}
}
