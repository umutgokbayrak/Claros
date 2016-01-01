package org.claros.intouch.common.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.Paths;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.intouch.common.utility.Utility;

import com.jenkov.mrpersister.itf.IGenericDao;

public class BaseService extends HttpServlet {
	private static final long serialVersionUID = -2549828936277983597L;
	private static HashMap configs = new HashMap();

	/**
	 * Searches for the variable in various places and returns it.
	 * @param request
	 * @param name
	 * @return Object
	 */
	public Object getVariable(HttpServletRequest request, String name) {
		Object obj = request.getParameter(name);
		if (obj == null) {
			obj = request.getAttribute(name);
			if (obj == null) {
			    obj = request.getSession().getAttribute(name);
			    if (obj == null) {
			        obj = getCookie(request, name);
			        if (obj == null) {
				        obj = getServletContext().getAttribute(name);
			        }
			    }
			}
		}
		return obj;
	}

	/**
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public String getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ConnectionProfile getConnectionProfile(HttpServletRequest request) {
		return (ConnectionProfile)request.getSession().getAttribute("profile");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ConnectionMetaHandler getConnectionHandler(HttpServletRequest request) {
		return (ConnectionMetaHandler)request.getSession().getAttribute("handler");
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public IGenericDao getDbConnection() throws Exception {
		return Utility.getDbConnection("file");
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IGenericDao getDbConnection(String name) throws Exception {
		return Utility.getDbConnection(name);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public AuthProfile getAuthProfile(HttpServletRequest request) {
		return (AuthProfile)request.getSession().getAttribute("auth");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ArrayList getUserSettings(HttpServletRequest request) {
		return (ArrayList)request.getSession().getAttribute("prefs");
	}

	/**
	 * 
	 * @param req
	 * @param key
	 * @return
	 */
	public String getText(HttpServletRequest req, String key) {
		try {
			String lang = (String)getVariable(req, "lang");
			if (lang == null) lang = "en";
			Locale loc = new Locale("en");
			try {
				loc = new Locale(lang);
			} catch (Exception e) {}
			
			String clsPath = Paths.getClsFolder();
			
			Configuration config = (Configuration)configs.get(lang);
			if (config == null) {
				config = new PropertiesConfiguration(new File(clsPath + "/org/claros/intouch/i18n/lang_" + loc + ".properties"));
				configs.put(lang, config);
			}
			return config.getString(key);
		} catch (ConfigurationException e) {
			return null;
		}
	}
}
