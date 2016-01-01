package org.claros.commons.db.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * @author Umut Gokbayrak
 *
 */
public class ListResultSetHandler implements ResultSetHandler {
    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet rs) throws SQLException {
        List<Object> out = new ArrayList<Object>();

        String name = null;
        Object value = null;
        int cols = -1;
        ResultSetMetaData meta = null;
        HashMap<String, Object> map = null;
        boolean hasItem = false;
        while (rs.next()) {
            hasItem = true;
            meta = rs.getMetaData();
            cols = meta.getColumnCount();
            map = new HashMap<String, Object>(cols);
            
            for (int i = 0; i < cols; i++) {
                name = meta.getColumnName(i+1);
                value = rs.getObject(i + 1);
                map.put(name, value);
            }
            out.add(map);
        }
        if (!hasItem) {
            return null;
        }
        return out;
    }
}
