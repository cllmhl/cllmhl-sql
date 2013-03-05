package it.fe.cllmhl.sql;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.sql.TestTable.TestRowDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe statica deputata alla persistenza della tabella test
 */
final class TestDao {
    /** IL DBManager di riferimento */
    protected static final SqlService SQLSERVICE;

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TestDao.class);

    private TestDao() {
    }

    static {
        SQLSERVICE = SqlService.getInstance("TEST");
    }

    protected static TestBean insert(TestBean pTestBean) {
        mLogger.debug("Start insert");

        List<SqlParameter> lSqlParameterList = new ArrayList<SqlParameter>();

        // chiave
        if (pTestBean.getChiave() != null)
            lSqlParameterList.add(new SqlParameter(TestTable.chiave, pTestBean.getChiave()));
        // stringa
        if (pTestBean.getStringa() != null)
            lSqlParameterList.add(new SqlParameter(TestTable.stringa, pTestBean.getStringa()));
        // importo
        if (pTestBean.getImporto() != null)
            lSqlParameterList.add(new SqlParameter(TestTable.importo, pTestBean.getImporto()));
        // qty
        if (pTestBean.getQty() != null)
            lSqlParameterList.add(new SqlParameter(TestTable.qty, pTestBean.getQty()));
        // data
        if (pTestBean.getData() != null)
            lSqlParameterList.add(new SqlParameter(TestTable.data, pTestBean.getData()));

        SQLSERVICE.insert(TestTable.getName(), lSqlParameterList);
        mLogger.debug("Finish insert");
        return pTestBean;
    }

    protected static void updateByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start updateByPrimaryKey");

        List<SqlParameter> lSqlParameterList = new ArrayList<SqlParameter>();

        // chiave
        lSqlParameterList.add(new SqlParameter(TestTable.chiave, pTestBean.getChiave()));
        // stringa
        lSqlParameterList.add(new SqlParameter(TestTable.stringa, pTestBean.getStringa()));
        // importo
        lSqlParameterList.add(new SqlParameter(TestTable.importo, pTestBean.getImporto()));
        // qty
        lSqlParameterList.add(new SqlParameter(TestTable.qty, pTestBean.getQty()));
        // data
        lSqlParameterList.add(new SqlParameter(TestTable.data, pTestBean.getData()));

        // Eseguiamo!!
        SQLSERVICE.updateByPrimaryKey(TestTable.getName(), lSqlParameterList);

        mLogger.debug("finish updateByPrimaryKey");
    }

    protected static void deleteByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start DeleteByPrimaryKey");

        List<SqlParameter> lSqlParameterList = new ArrayList<SqlParameter>();

        // chiave
        lSqlParameterList.add(new SqlParameter(TestTable.chiave, pTestBean.getChiave()));

        // Eseguiamo
        SQLSERVICE.deleteByPrimaryKey(TestTable.getName(), lSqlParameterList);
    }

    protected static List<TestBean> loadAll() {
        return loadUsingTemplate(new TestBean(), null);
    }

    protected static List<TestBean> loadUsingTemplate(TestBean pTestBean, IPager pPager) {
        mLogger.debug("Start loadUsingTemplate");

        SqlStatement lSqlStatement = new SqlStatement("SELECT * FROM ");
        lSqlStatement.append("");
        lSqlStatement.append(TestTable.getName());
        lSqlStatement.append(" ");

        // Preparo ed aggiungo la condizione di where
        lSqlStatement.append(buildWhereConditionUsingTemplate(pTestBean));

        // order by
        if (pTestBean.getOrderBy() != null) {
            lSqlStatement.append(" ORDER BY ");
            lSqlStatement.append(pTestBean.getOrderBy());
        }

        List<TestBean> lTestBeanList = SQLSERVICE.executeQuery(lSqlStatement, new PagedResultsetDecoder<>(pPager, new TestRowDecoder()));

        mLogger.debug("End LoadUsingTemplate");

        return lTestBeanList;
    }

    protected static TestBean loadByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start loadByPrimaryKey");

        List<SqlParameter> lSqlParameterList = new ArrayList<SqlParameter>();

        // chiave
        lSqlParameterList.add(new SqlParameter(TestTable.chiave, pTestBean.getChiave()));

        // Eseguiamo!!
        Object lObject = SQLSERVICE.loadByPrimaryKey(TestTable.getName(), lSqlParameterList, new TestRowDecoder());

        mLogger.debug("finish loadByPrimaryKey");
        // Cast
        return (TestBean) lObject;
    }

    protected static SqlStatement buildWhereConditionUsingTemplate(TestBean pTestBean) {
        mLogger.debug("Start buildWhereConditionUsingTemplate");

        SqlStatement lSqlStatement = new SqlStatement(" WHERE 1=1 ");

        // chiave
        if (pTestBean.getChiave() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.chiave.getName());
            lSqlStatement.append(" = ?", new SqlParameter(TestTable.chiave, pTestBean.getChiave()));
        }

        // stringa
        if (pTestBean.getStringa() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.stringa.getName());
            lSqlStatement.append(" LIKE ?", new SqlParameter(TestTable.stringa, "%" + pTestBean.getStringa() + "%"));
        }

        // importo
        if (pTestBean.getImporto() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.importo.getName());
            lSqlStatement.append(" = ?", new SqlParameter(TestTable.importo, pTestBean.getImporto()));
        }

        // qty
        if (pTestBean.getQty() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.qty.getName());
            lSqlStatement.append(" = ?", new SqlParameter(TestTable.qty, pTestBean.getQty()));
        }

        // data
        if (pTestBean.getData() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.data.getName());
            lSqlStatement.append(" >= ?", new SqlParameter(TestTable.data, pTestBean.getData()));
        }
        if (pTestBean.getDatato() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(TestTable.data.getName());
            lSqlStatement.append(" <= ?", new SqlParameter(TestTable.data, pTestBean.getDatato()));
        }

        mLogger.debug("Finish buildWhereConditionUsingTemplate returning ", lSqlStatement);

        return lSqlStatement;
    }
}