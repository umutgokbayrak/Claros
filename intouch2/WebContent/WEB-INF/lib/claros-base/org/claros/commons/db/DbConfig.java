package org.claros.commons.db;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.claros.commons.configuration.Paths;
import org.claros.commons.utility.Utility;


public class DbConfig {
	private String id;
	private String database;
	private String driver;
	private String login;
	private String password;
	private DataSource dataSource;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		database = Utility.replaceAllOccurances(database, "%dbpath%", Paths.getDbFolder());
		this.database = database;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the data source or creates a new one.
	 * @return
	 */
	public DataSource getDataSource() {
		if (dataSource == null) {
	        BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName(driver);
	        ds.setUsername(login);
	        ds.setPassword(password);
	        ds.setUrl(database);
//	        ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	        ds.setInitialSize(2);
	        ds.setMaxActive(50);
	        ds.setMaxIdle(20);
	        ds.setMaxWait(5000);
	        dataSource = ds;
		}
		return dataSource;
	}

	/**
	 * toString implementation
	 */
	public String toString() {
		String out = "id=" + id + "\n" +
					 "database=" + database + "\n" + 
					 "driver=" + driver + "\n" + 
					 "login=" + login + "\n" + 
					 "password=" + password + "\n"; 
		return out;
	}
}
