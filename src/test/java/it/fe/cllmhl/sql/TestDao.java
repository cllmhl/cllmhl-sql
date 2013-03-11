package it.fe.cllmhl.sql;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.sql.Test.TestRowDecoder;
import it.fe.cllmhl.sql.orm.PagedResultsetDecoder;
import it.fe.cllmhl.sql.orm.SqlParameter;
import it.fe.cllmhl.sql.orm.SqlStatement;
import it.fe.cllmhl.sql.service.ISqlService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe statica deputata alla persistenza della tabella test
 */
final class TestDao {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(TestDao.class);
    public static ISqlService SQLSERVICE = ServiceLocator.getServiceByName(ISqlService.class, "POOL");

    protected static SqlStatement buildWhereConditionUsingTemplate(TestBean pTestBean) {
        mLogger.debug("Start buildWhereConditionUsingTemplate");

        SqlStatement lSqlStatement = new SqlStatement(" WHERE 1=1 ");

        // chiave
        if (pTestBean.getChiave() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.CHIAVE.getName());
            lSqlStatement.append(" = ?", new SqlParameter<Integer>(Test.CHIAVE, pTestBean.getChiave()));
        }

        // stringa
        if (pTestBean.getStringa() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.STRINGA.getName());
            lSqlStatement.append(" LIKE ?", new SqlParameter<String>(Test.STRINGA, "%" + pTestBean.getStringa() + "%"));
        }

        // importo
        if (pTestBean.getImporto() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.IMPORTO.getName());
            lSqlStatement.append(" = ?", new SqlParameter<BigDecimal>(Test.IMPORTO, pTestBean.getImporto()));
        }

        // qty
        if (pTestBean.getQty() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.QTY.getName());
            lSqlStatement.append(" = ?", new SqlParameter<Integer>(Test.QTY, pTestBean.getQty()));
        }

        // data
        if (pTestBean.getData() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.DATA.getName());
            lSqlStatement.append(" >= ?", new SqlParameter<Date>(Test.DATA, pTestBean.getData()));
        }
        if (pTestBean.getDatato() != null) {
            lSqlStatement.append(" AND ");
            lSqlStatement.append(Test.DATA.getName());
            lSqlStatement.append(" <= ?", new SqlParameter<Date>(Test.DATA, pTestBean.getDatato()));
        }

        mLogger.debug("Finish buildWhereConditionUsingTemplate returning ", lSqlStatement);

        return lSqlStatement;
    }

    protected static void deleteByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start DeleteByPrimaryKey");

        List<SqlParameter<? extends Object>> lSqlParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // chiave
        lSqlParameterList.add(new SqlParameter<Integer>(Test.CHIAVE, pTestBean.getChiave()));

        // Eseguiamo
        SQLSERVICE.deleteByPrimaryKey(Test.NAME, lSqlParameterList);
    }

    protected static TestBean insert(TestBean pTestBean) {
        mLogger.debug("Start insert");

        List<SqlParameter<? extends Object>> lSqlParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // chiave
        if (pTestBean.getChiave() != null) {
            lSqlParameterList.add(new SqlParameter<Integer>(Test.CHIAVE, pTestBean.getChiave()));
        }
        // stringa
        if (pTestBean.getStringa() != null) {
            lSqlParameterList.add(new SqlParameter<String>(Test.STRINGA, pTestBean.getStringa()));
        }
        // importo
        if (pTestBean.getImporto() != null) {
            lSqlParameterList.add(new SqlParameter<BigDecimal>(Test.IMPORTO, pTestBean.getImporto()));
        }
        // qty
        if (pTestBean.getQty() != null) {
            lSqlParameterList.add(new SqlParameter<Integer>(Test.QTY, pTestBean.getQty()));
        }
        // data
        if (pTestBean.getData() != null) {
            lSqlParameterList.add(new SqlParameter<Date>(Test.DATA, pTestBean.getData()));
        }

        SQLSERVICE.insert(Test.NAME, lSqlParameterList);
        mLogger.debug("Finish insert");
        return pTestBean;
    }

    protected static List<TestBean> loadAll() {
        return loadUsingTemplate(new TestBean(), null);
    }

    protected static TestBean loadByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start loadByPrimaryKey");

        List<SqlParameter<? extends Object>> lSqlParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // chiave
        lSqlParameterList.add(new SqlParameter<Integer>(Test.CHIAVE, pTestBean.getChiave()));

        // Eseguiamo!!
        Object lObject = SQLSERVICE.loadByPrimaryKey(Test.NAME, lSqlParameterList, new TestRowDecoder());

        mLogger.debug("finish loadByPrimaryKey");
        // Cast
        return (TestBean) lObject;
    }

    protected static List<TestBean> loadUsingTemplate(TestBean pTestBean, IPager pPager) {
        mLogger.debug("Start loadUsingTemplate");

        SqlStatement lSqlStatement = new SqlStatement("SELECT * FROM ");
        lSqlStatement.append("");
        lSqlStatement.append(Test.NAME);
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

    protected static void updateByPrimaryKey(TestBean pTestBean) {
        mLogger.debug("Start updateByPrimaryKey");

        List<SqlParameter<? extends Object>> lSqlParameterList = new ArrayList<SqlParameter<? extends Object>>();

        // chiave
        lSqlParameterList.add(new SqlParameter<Integer>(Test.CHIAVE, pTestBean.getChiave()));
        // stringa
        lSqlParameterList.add(new SqlParameter<String>(Test.STRINGA, pTestBean.getStringa()));
        // importo
        lSqlParameterList.add(new SqlParameter<BigDecimal>(Test.IMPORTO, pTestBean.getImporto()));
        // qty
        lSqlParameterList.add(new SqlParameter<Integer>(Test.QTY, pTestBean.getQty()));
        // data
        lSqlParameterList.add(new SqlParameter<Date>(Test.DATA, pTestBean.getData()));

        // Eseguiamo!!
        SQLSERVICE.updateByPrimaryKey(Test.NAME, lSqlParameterList);

        mLogger.debug("finish updateByPrimaryKey");
    }

    private TestDao() {
    }
}