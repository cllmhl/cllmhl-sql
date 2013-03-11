package it.fe.cllmhl.sql;

import it.fe.cllmhl.sql.sequence.TableSequenceManager;

import java.util.List;

public final class TestManager {

    public static int countRecords() {
        return TestDao.SQLSERVICE.countRecords(Test.NAME);
    }

    public static int countRecordsUsingTemplate(TestBean pTestBean) {
        return TestDao.SQLSERVICE.countRecords(Test.NAME, TestDao.buildWhereConditionUsingTemplate(pTestBean));
    }

    public static void deleteByPrimaryKey(TestBean pTestBean) {
        TestDao.deleteByPrimaryKey(pTestBean);
    }

    public static void insert(TestBean pTestBean) {
        Integer lIntPrimaryKeyValue = TableSequenceManager.getPrimaryKeyValue(Test.NAME);
        pTestBean.setChiave(lIntPrimaryKeyValue);
        TestDao.insert(pTestBean);
    }

    public static List<TestBean> loadAll() {
        return TestDao.loadAll();
    }

    public static List<TestBean> loadAllUsingTemplate(TestBean pTestBean) {
        return TestDao.loadUsingTemplate(pTestBean, null);
    }

    public static TestBean loadByPrimaryKey(TestBean pTestBean) {
        return TestDao.loadByPrimaryKey(pTestBean);
    }

    public static List<TestBean> loadPageUsingTemplate(TestBean pTestBean, IPager pPager) {
        return TestDao.loadUsingTemplate(pTestBean, pPager);
    }

    public static void updateByPrimaryKey(TestBean pTestBean) {
        TestDao.updateByPrimaryKey(pTestBean);
    }

    private TestManager() {
    }
}