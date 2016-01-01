package org.claros.commons.configuration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.db.DbConfig;
import org.claros.commons.db.DbConfigList;
import org.claros.commons.exception.FatalException;
import org.claros.commons.utility.Utility;
import org.xml.sax.SAXException;

public class Initiator extends HttpServlet {
    private static final long serialVersionUID = -3372714955993332699L;

    private static Log log = null;

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException if an error occure
     */
    public void init() throws ServletException {
        // first: initiate paths so that we can find files.
        // everything else depends on this. So it is very
        // important
    	try {
            initiatePaths();
    	} catch (Exception e) {
    		System.out.println("Claros Base System could not initialize paths on the web application.");
    		throw new FatalException(e);
    	}

        // now initiate logger.
    	try {
            initiateLogger();
            log = LogFactory.getLog(Initiator.class);
    	} catch (Exception e) {
    		System.out.println("Claros Base System could not initialize logging paths.");
    		throw new FatalException(e);
    	}

        // now initiate the db's and start database pool. 
        try {
			initiateDb();
			
			HashMap<String, DbConfig>dbList = DbConfigList.getDbList();
			
			if (dbList != null) {
			    Iterator<String> iter = dbList.keySet().iterator();
			    DbConfig db = null;
			    while (iter.hasNext()) {
			        db = (DbConfig)dbList.get(iter.next());
			        db.getDataSource();
			        log.debug("DbConfig read with parameters. " + db.toString());
			    }
			}
		} catch (Exception e) {
		    log.fatal("Claros Base System could not initialize the databases");
			throw new FatalException(e);
		}
       
        // are there any batch jobs?
        
    }

	public void destroy() {
		HashMap<String, DbConfig> dbList = DbConfigList.getDbList();
		Set<String> keys = dbList.keySet();
		if (keys != null) {
			Iterator<String> iter = keys.iterator();
			String tmp = null;
			while (iter.hasNext()) {
				tmp = (String)iter.next();
				DbConfig tmpConf = (DbConfig)dbList.get(tmp);
				BasicDataSource ds = (BasicDataSource)tmpConf.getDataSource();
				if (ds != null) {
					try {
						ds.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						ds = null;
					}
				}
			}
		}
		DbConfigList.dbList = null;
	}

	private void initiateDb() {
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.addObjectCreate("claros-config/db-config", "org.claros.commons.db.DbConfigList");
            digester.addObjectCreate("claros-config/db-config/db", "org.claros.commons.db.DbConfig");
            digester.addCallMethod("claros-config/db-config/db/id", "setId", 0);
            digester.addCallMethod("claros-config/db-config/db/database", "setDatabase", 0);
            digester.addCallMethod("claros-config/db-config/db/driver", "setDriver", 0);
            digester.addCallMethod("claros-config/db-config/db/login", "setLogin", 0);
            digester.addCallMethod("claros-config/db-config/db/password", "setPassword", 0);
            digester.addSetNext("claros-config/db-config/db", "addDbConfig", "org.claros.commons.db.DbConfig");
            digester.parse(new File(Paths.getCfgFolder() + "/config.xml"));
        } catch (IOException e) {
            log.fatal("Could not find config.xml file in your config path.(" + Paths.getCfgFolder() + ")", e);
        } catch (SAXException e) {
            log.fatal("Could not validate config.xml file or could not read its contents", e);
        }
    }

    /**
     * find the paths. 
     */
    private void initiatePaths() {
    	ServletContext context = getServletContext();
    	String contextRealPath = context.getRealPath("/");
    	System.out.println("Claros Servlet Context Real Path: " + contextRealPath);

        String prefix = Utility.replaceAllOccurances(contextRealPath, "\\", "/");
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        System.out.println("Claros Init Path Prefix: " + prefix);
        Paths.setPrefix(prefix);
        Paths.setLogFolder(prefix + "/WEB-INF/log");
        System.out.println("Claros Log Folder: " + Paths.getLogFolder());
        Paths.setCfgFolder(prefix + "/WEB-INF/config");
        System.out.println("Claros Config Folder: " + Paths.getCfgFolder());
        Paths.setLibFolder(prefix + "/WEB-INF/lib");
        Paths.setResFolder(prefix + "/WEB-INF/resources");
        Paths.setClsFolder(prefix + "/WEB-INF/classes");
        Paths.setDbFolder(prefix + "/WEB-INF/db");
    }

    /**
     * initate the log4j
     */
    private void initiateLogger() {
        try {
        	System.out.println("Log4j initiated");
        	// DOMConfigurator.configure(Paths.getCfgFolder() + "/log.properties");
        	/*
            FileInputStream is = new FileInputStream();
            prop.load(is);
            is.close();

            Iterator<Object> iter = prop.keySet().iterator();
            String key = null;
            String val = null;
            while (iter.hasNext()) {
                key = (String) iter.next();
                val = prop.getProperty(key);
                if (val != null) {
                    val = Utility.replaceAllOccurances(val, "%logpath%", Paths.getLogFolder());
                    prop.setProperty(key, val);
                }
            }
            PropertyConfigurator.configure(prop);
            */
        } catch (Exception e) {
        	System.setProperty("log4j.rootCategory", "debug, stdout");
            System.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
            System.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        }
    }
}
