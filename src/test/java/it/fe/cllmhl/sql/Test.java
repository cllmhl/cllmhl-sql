package it.fe.cllmhl.sql;

import it.fe.cllmhl.sql.orm.Column;
import it.fe.cllmhl.sql.orm.IRowDecoder;
import it.fe.cllmhl.sql.orm.JdbcMapping;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface Test {
	String NAME = "test";
    
    Column<Integer> CHIAVE = new Column<Integer>("chiave",JdbcMapping.INTEGER,true);
    Column<String> STRINGA = new Column<String>("stringa",JdbcMapping.STRING,false);
    Column<BigDecimal> IMPORTO = new Column<BigDecimal>("importo",JdbcMapping.BIGDECIMAL,false);
    Column<Integer> QTY = new Column<Integer>("qty",JdbcMapping.INTEGER,false);
    Column<Date> DATA = new Column<Date>("data",JdbcMapping.DATE,false); 

    
    final class TestRowDecoder implements IRowDecoder<TestBean> {
        private static List<Column<? extends Object>> columns = new ArrayList<Column<? extends Object>>();

        static {
            columns.add(CHIAVE);
            columns.add(STRINGA);
            columns.add(IMPORTO);
            columns.add(QTY);
            columns.add(DATA);
        }

        @Override
        public TestBean decodeRow(ResultSet pResultSet) throws SQLException {
            TestBean lTestBean = new TestBean();
            lTestBean.setChiave(CHIAVE.getValue(pResultSet));
            lTestBean.setStringa(STRINGA.getValue(pResultSet));
            lTestBean.setImporto(IMPORTO.getValue(pResultSet));
            lTestBean.setQty(QTY.getValue(pResultSet));
            lTestBean.setData(DATA.getValue(pResultSet));
            return lTestBean;
        }
    }
}
