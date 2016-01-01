package org.claros.commons.db.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * @author Umut Gokbayrak
 *
 */
public class ItemResultSetHandler implements ResultSetHandler {

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet rs) throws SQLException {
        String name = null;
        Object value = null;
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        if (cols == 0) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>(cols);
        
        for (int i = 0; i < cols; i++) {
            name = meta.getColumnName(i+1);
            value = rs.getObject(i + 1);
            map.put(name, value);
        }
        return map;
    }
}
