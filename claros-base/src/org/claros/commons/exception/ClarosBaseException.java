package org.claros.commons.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.configuration.Paths;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.utility.Utility;

/**
 * @author Umut Gokbayrak
 */
public class ClarosBaseException extends ServletException {
	private static final long serialVersionUID = -2268010989574881830L;
	private static Log log = LogFactory.getLog(ClarosBaseException.class);
	private String errorKey;
	private String userMessage;
	private String systemMessage;
	private Exception nestedException;
	private static Configuration msgs;

	public ClarosBaseException() {
		super();
	}

	public ClarosBaseException(String errorKey) {
		this.errorKey = errorKey;
		this.userMessage = msgs.getString(this.errorKey);
		log.error("An exception is created. Message: " + userMessage);
	}

	public ClarosBaseException(Exception nestedException, String errorKey) {
		this.nestedException = nestedException;

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bOut);
		this.nestedException.printStackTrace(out);
		this.systemMessage = bOut.toString();
		try {
			bOut.close();
		} catch (IOException e) {}
		this.errorKey = errorKey;
		this.userMessage = msgs.getString(errorKey);
		log.error("An exception is created. Message: " + userMessage);
	}

	public ClarosBaseException(Exception nestedException) {
		this.nestedException = nestedException;

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bOut);
		this.nestedException.printStackTrace(out);
		this.systemMessage = bOut.toString();
		log.error("An exception is created. System Message: " + this.systemMessage);
		try {
			bOut.close();
		} catch (IOException e) {}
	}
	
	static {
		try {
			String configPath = "/config/config.xml";
			Configuration configXml = PropertyFile.getConfiguration(configPath);
			String resourceType = configXml.getString("error-handling.resource-type");
			if (resourceType != null) {
				if (resourceType.equals("FILE")) {
					log.debug("Error messages are read from FILE resource");
					String resourceFile = configXml.getString("error-handling.resource-path");
					if (resourceFile != null) {
						if (resourceFile.indexOf("%respath%") >= 0) {
							resourceFile = Utility.replaceAllOccurances(resourceFile, "%respath%", Paths.getResFolder());
						}
						log.debug("Error messages are read from path: " + resourceFile);
						msgs = new PropertiesConfiguration(resourceFile);
						log.debug("Error messages file initialized successfully");
					}
				} else if (resourceType.equals("DB")) {
					// TODO: not implemented yet
				}
			} else {
//				log.fatal("Could not initialize error messages file. Check the config-error statements in config.xml file. resource-type can not be read.");
			}
		} catch (Exception e) {
//			log.fatal("Could not initialize error messages file. Check the config-error statements in config.xml file", e);
		}
	}

	public String getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(String systemMessage) {
		this.systemMessage = systemMessage;
	}

}
