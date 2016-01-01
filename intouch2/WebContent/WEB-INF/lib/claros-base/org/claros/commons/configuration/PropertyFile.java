package org.claros.commons.configuration;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.exception.SystemException;
import org.claros.commons.utility.Utility;

/**
 * 
 * @author Umut Gökbayrak
 *
 */
public class PropertyFile {
	private static Log log = LogFactory.getLog(Utility.class);
	
	/**
	 * Reads a xml file or properties file and returns it. 
	 * You must define the fileName relative to the WEB-INF directory. 
	 * For example /config/config.xml means /WEB-INF/config/config.xml
	 * from the web source directory. 
	 * @param fileName
	 * @return Configuration
	 * @throws ConfigurationException 
	 */
	public static Configuration getConfiguration(String fileName) throws ConfigurationException, SystemException {
		Configuration config = null;
		try {
			if (fileName != null) {
				File f = new File(Paths.getPrefix() + "/WEB-INF" + fileName);
				
				if (fileName.endsWith(".xml")) {
					config = new XMLConfiguration(f);
				} else {
					config = new PropertiesConfiguration(f);
				}
			}
		} catch (ConfigurationException e) {
			log.error("Property file " + fileName + " could not be loaded. Configuration problem.", e);
			throw e;
		} catch (Exception e) {
			log.error("Property file " + fileName + " could not be loaded. System problem.", e);
			throw new SystemException(e, "configuration.property.generic");
		}
		return config;
	}
}
