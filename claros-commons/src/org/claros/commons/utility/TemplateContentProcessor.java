package org.claros.commons.utility;

import org.claros.commons.errors.ConfigurationException;

public interface TemplateContentProcessor {

	/**
	 * Merges the dataMap and Template
	 * 
	 * @return The merged data
	 * @throws ConfigurationException
	 */
	public String merge() throws ConfigurationException;

}