package org.claros.commons.errors;

/**
 * Thrown when a configuration problem exists. 
 * 
 * @author Umut Gokbayrak
 *
 */
public class ConfigurationException extends SystemException {
	private static final long serialVersionUID = -7096999158341345804L;

	public ConfigurationException() {
		super();
		
	}

	public ConfigurationException(Exception nestedException, String errorMessage) {
		super(nestedException, errorMessage);
	}

	public ConfigurationException(Exception nestedException) {
		super(nestedException);
	}

	public ConfigurationException(String errorMessage) {
		super(errorMessage);
	}
	
}
