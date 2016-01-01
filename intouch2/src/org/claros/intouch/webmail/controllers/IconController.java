package org.claros.intouch.webmail.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

import org.claros.commons.configuration.Paths;

public class IconController {
	private static Properties propMimes = new Properties();
	private static Properties propExt = new Properties();
	private static String defaultIcon = "binary.png";
	private static Locale loc = new Locale("en", "US");

	static {
        try {
        	FileInputStream is = new FileInputStream(new File(Paths.getCfgFolder() + "/mime.properties"));
			propMimes.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        try {
        	FileInputStream is = new FileInputStream(new File(Paths.getCfgFolder() + "/extension.properties"));
			propExt.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param mime
	 * @return
	 */
	public static String findIconByMime(String mime) {
		if (mime == null) {
			return defaultIcon;
		}
		mime = mime.toLowerCase(loc).trim();
		if (mime.indexOf(";") > 0) {
			mime = mime.substring(0, mime.indexOf(";"));
		}
		if (mime.indexOf(" ") > 0) {
			mime = mime.substring(0, mime.indexOf(" "));
		}
		
		String ico = propMimes.getProperty(mime);
		if (ico == null) {
			if (mime.indexOf("/") > 0) {
				String mimeType = mime.substring(0, mime.indexOf("/"));
				ico = propMimes.getProperty(mimeType + "/*", defaultIcon);
			}
		}
		return ico;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String findIconByName(String name) {
		if (name != null && name.trim().length() > 0) {
			int pos = name.lastIndexOf(".");
			if (pos > 0) {
				String ext = name.substring(pos + 1);
				if (ext != null && ext.trim().length() > 0) {
					String mime = propExt.getProperty(ext.toLowerCase());
					String ico = propMimes.getProperty(mime);
					if (ico == null) {
						if (mime.indexOf("/") > 0) {
							String mimeType = mime.substring(0, mime.indexOf("/"));
							ico = propMimes.getProperty(mimeType + "/*", defaultIcon);
						}
					}
					return ico;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String findMimeByName(String name) {
		if (name != null && name.trim().length() > 0) {
			int pos = name.lastIndexOf(".");
			if (pos > 0) {
				String ext = name.substring(pos + 1);
				if (ext != null && ext.trim().length() > 0) {
					String mime = propExt.getProperty(ext.toLowerCase());
					return mime;
				}
			}
		}
		return null;
	}

}
