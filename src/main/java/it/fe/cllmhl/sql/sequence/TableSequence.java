package it.fe.cllmhl.sql.sequence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import it.fe.cllmhl.sql.Column;
import it.fe.cllmhl.sql.IRowDecoder;
import it.fe.cllmhl.sql.ReflectionRowDecoder;
import it.fe.cllmhl.sql.SqlType;

interface TableSequence {

	String NAME = "table_sequence";
    
    Column ID_TABLE = new Column("id_table",SqlType.VARCHAR,true);
    Column KEY_VALUE = new Column("key_value",SqlType.INTEGER,false);
    
    
    final class TableSequenceRowDecoder implements IRowDecoder<TableSequenceBean> {
        private static List<Column> columns = new ArrayList<Column>();
       
        static {
            columns.add(ID_TABLE);
            columns.add(KEY_VALUE);
        }
        
        public TableSequenceBean decodeRow(ResultSet pResultSet) {
            ReflectionRowDecoder<TableSequenceBean> lReflectionRowDecoder = new ReflectionRowDecoder<TableSequenceBean>(columns, TableSequenceBean.class);
            return lReflectionRowDecoder.decodeRow(pResultSet);
        }
    }


}
