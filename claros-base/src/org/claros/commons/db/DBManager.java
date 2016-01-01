package org.claros.commons.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.db.handler.ItemResultSetHandler;
import org.claros.commons.db.handler.ListResultSetHandler;

/**
 * @author Umut Gokbayrak
 *
 */
public class DBManager {
    private static Log log = LogFactory.getLog(DBManager.class);
    private static Configuration sqls = null;
    
    /**
     * Runs a query on the and returns the result as a list. 
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @return List
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
	public static List<Object> queryList(String dbId, String sqlKey, Object params[]) throws SQLException {
    	String sql = getSqlFromKey(sqlKey);
    	log.debug("Running List query sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId);
    	QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
        List<Object> result = null;
        try {
            result = (List<Object>)run.query(sql, params, new ListResultSetHandler());
        } catch (SQLException e) {
            log.error("Error while querying... Key:" + sqlKey + " params: " + params, e);
            throw e;
        }
		log.debug("Sql List query returned " + ((result == null) ? null : result.toString()));
        return result;
    }

    /**
     * Runs a query on db and returns the result row (single row) with values in a HashMap
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @return HashMap
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> queryItem(String dbId, String sqlKey, Object params[]) throws SQLException {
    	String sql = getSqlFromKey(sqlKey);
    	log.debug("Running Item query sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId);
        QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
        HashMap<String, Object> result = null;
        try {
            result = (HashMap<String, Object>)run.query(sql, params, new ItemResultSetHandler());
        } catch (SQLException e) {
            log.error("Error while querying... Key:" + sqlKey + " params: " + params, e);
            throw e;
        }
		log.debug("Sql Item query returned " + ((result == null) ? null : result.toString()));
        return result;
    }
    
    /**
     * Runs query and populates a specific POJO with the values from the row.
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @param clsBean
     * @return A POJO Class
     * @throws SQLException
     */
    public static Object queryBean(String dbId, String sqlKey, Object params[], Class<Object> clsBean) throws SQLException {
    	String sql = getSqlFromKey(sqlKey);
    	log.debug("Running Bean query sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId + " on bean: " + clsBean);
		QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
		ResultSetHandler h = new BeanHandler(clsBean);
		Object result = run.query(getSqlFromKey(sqlKey), params, h);
		log.debug("Sql Bean query returned " + ((result == null) ? null : result.toString()));
		return result;
    }

    /**
     * Runs an update sql on db
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @throws SQLException
     */
    public static void update(String dbId, String sqlKey, Object params[]) throws SQLException {
		String sql = getSqlFromKey(sqlKey);
    	log.debug("Running sql update, sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId);
		QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
		int result = run.update(sql, params);
		log.debug("Sql Update returned Result: " + result);
    }

    /**
     * Runs a delete sql on db
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @throws SQLException
     */
    public static void delete(String dbId, String sqlKey, Object params[]) throws SQLException {
		String sql = getSqlFromKey(sqlKey);
    	log.debug("Running sql delete, sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId);
		QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
		int result = run.update(sql, params);
		log.debug("Sql Delete returned Result: " + result);
    }

    /**
     * Runs an insert sql on db
     * 
     * @param dbId
     * @param sqlKey
     * @param params
     * @throws SQLException
     */
    public static void insert(String dbId, String sqlKey, Object params[]) throws SQLException {
		String sql = getSqlFromKey(sqlKey);
    	log.debug("Running sql insert, sql: " + sql + " with params: " + ((params == null) ? null : params.toString()) + " on dbId: " + dbId);
		QueryRunner run = new QueryRunner(DbConfigList.getDataSourceById(dbId));
		int result = run.update(sql, params);
		log.debug("Sql Insert returned Result: " + result);
    }
    
    /**
     * reads a sql from the sql.properties file.
     * @param sqlKey
     * @return
     * @deprecated
     */
    private static String getSqlFromKey(String sqlKey) {
        return sqls.getString(sqlKey);
    }

    static {
        try {
            sqls = PropertyFile.getConfiguration("/resources/sql.properties");
        } catch (Exception e) {
            log.fatal("Can not read sql.properties file. Look what is happening. It will crash probably!!!", e);
        }
    }
}
