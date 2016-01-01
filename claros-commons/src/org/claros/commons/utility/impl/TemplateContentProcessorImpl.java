package org.claros.commons.utility.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.claros.commons.errors.ConfigurationException;
import org.claros.commons.utility.TemplateContentProcessor;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Reads a Freemarker Template file and data map provided parses it and
 * merges to create an output to be displayed.
 * 
 * @author Umut Gokbayrak
 *
 */
public class TemplateContentProcessorImpl implements TemplateContentProcessor {
	private static String encoding;
	private static String language;
	private static String variant;
	private PropertiesConfiguration config;
	private static Map<String, Template> templates = new HashMap<String, Template>();
	
	private Template template;
	private Map<String, Object> dataMap;

	/**
	 * Disable default constructor from creating a new instance.
	 */
	public TemplateContentProcessorImpl() {
		super();

		
	}
	
	/**
	 * General purpose constructor which greately simplifies things. 
	 * 
	 * @param templatePath Freemarker Template classpath
	 * @param dataMap Fremarker Data Map to be merged.
	 */
	public TemplateContentProcessorImpl(final String templatePath, final Map<String, Object> dataMap) {
		// read language settings from the config.properties file.
		try {
			if (config == null) {
				config = new PropertiesConfiguration("freemarker.properties");
			}
		} catch (org.apache.commons.configuration.ConfigurationException e1) {
			e1.printStackTrace();
		}
		
		if (config != null && (encoding == null || language == null || variant == null)) {
			encoding = config.getString("encoding");
			language = config.getString("language");
			variant = config.getString("variant");
		}
		try {
			setTemplatePath(templatePath);
		} catch (IOException e) {
			this.template = null;
		}
		setDataMap(dataMap);
	}
	
	/**
	 * This method reads the template from classpath and 
	 * deals with file encoding and language facilities.
	 * 
	 * @param filePath
	 * @return Template
	 * @throws IOException
	 */
	private Template readTemplate(final String filePath) throws IOException {
		Template template = templates.get(filePath);
		template = null;
		if (template == null) {
			Configuration cfg = new Configuration();
			if (config != null) {
				cfg.setEncoding(new Locale(language, variant), encoding);
				cfg.setOutputEncoding(encoding);
				cfg.setDefaultEncoding(encoding);
				cfg.setLocale(new Locale(language, variant));
			}
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			
			String classPath = filePath.substring(0, filePath.lastIndexOf("/"));
			String templateName = filePath.substring(filePath.lastIndexOf("/") + 1);
			cfg.setClassForTemplateLoading(TemplateContentProcessorImpl.class, classPath);
			template = cfg.getTemplate(templateName, encoding);
			templates.put(filePath, template);
		}
		return template;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.TemplateContentProcessor#merge()
	 */
	public String merge() throws ConfigurationException {
		if (config != null) {
			template.setEncoding(encoding);
			template.setLocale(new Locale(language, variant));
			template.setOutputEncoding(encoding);
		}

		String result = null;
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();

			Writer out = null;
			if (config != null) {
				out = new OutputStreamWriter(bs, encoding);
			} else {
				out = new OutputStreamWriter(bs);
			}
			template.process(dataMap, out);
			out.flush();
			
			if (config != null) {
				result = new String(bs.toByteArray(), encoding);
			} else {
				result = new String(bs.toByteArray());
			}
		} catch (Exception e) {
			throw new ConfigurationException(e, "Unable to merge template and data for template: ");
		}
		return result;
	}
	
	/**
	 * Sets the data map to be merged with the template
	 * @param dataMap
	 */
	private void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * Full classpath for the template file (.ftl) where it resides. 
	 * <pre>Example: com/foo/bar/template.ftl</pre>
	 * 
	 * @param templatePath
	 * @throws IOException
	 */
	private void setTemplatePath(String templatePath) throws IOException {
		this.template = readTemplate(templatePath);
	}

	public void setConfig(PropertiesConfiguration config) {
		this.config = config;
	}
}
