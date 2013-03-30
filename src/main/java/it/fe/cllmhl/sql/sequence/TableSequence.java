package it.fe.cllmhl.sql.sequence;

import it.fe.cllmhl.sql.orm.Column;
import it.fe.cllmhl.sql.orm.IRowDecoder;
import it.fe.cllmhl.sql.orm.SqlMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface TableSequence {
    
    //table
    String NAME = "table_sequence";

    //columns
    Column<String> ID_TABLE = new Column<String>("id_table", SqlMapping.STRING, true);
    Column<Integer> KEY_VALUE = new Column<Integer>("key_value", SqlMapping.INTEGER, false);



    final class TableSequenceRowDecoder implements IRowDecoder<TableSequenceBean> {
        private static List<Column<? extends Object>> columns = new ArrayList<Column<? extends Object>>();

        static {
            columns.add(ID_TABLE);
            columns.add(KEY_VALUE);
        }

        @Override
        public TableSequenceBean decodeRow(ResultSet pResultSet) throws SQLException {
            TableSequenceBean lTableSequenceBean = new TableSequenceBean();
            lTableSequenceBean.setIdTable(ID_TABLE.getValue(pResultSet));
            lTableSequenceBean.setKeyValue(KEY_VALUE.getValue(pResultSet));
            return lTableSequenceBean;
        }
    }
}
