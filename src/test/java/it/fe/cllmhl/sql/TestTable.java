package it.fe.cllmhl.sql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestTable {
	private static String name = "test";
    private static List<Column> columns = new ArrayList<Column>();
    
    public static Column chiave = new Column("chiave",SqlType.INTEGER,true);
    public static Column stringa = new Column("stringa",SqlType.VARCHAR,false);
    public static Column importo = new Column("importo",SqlType.DECIMAL,false);
    public static Column qty = new Column("qty",SqlType.INTEGER,false);
    public static Column data = new Column("data",SqlType.DATE,false); 

    
	static {
		columns.add(chiave);
		columns.add(stringa);
		columns.add(importo);
		columns.add(qty);
		columns.add(data);
	}

	public static String getName() {
    	return name;
	}

	public static List<Column> getColumns() {
		return columns;
	}

    protected static final class TestRowDecoder implements IRowDecoder<TestBean> {
        public TestBean decodeRow(ResultSet pResultSet) {
            ReflectionRowDecoder<TestBean> lReflectionRowDecoder = new ReflectionRowDecoder<TestBean>(columns, TestBean.class);
            return (TestBean) lReflectionRowDecoder.decodeRow(pResultSet);
        }
    }
}
