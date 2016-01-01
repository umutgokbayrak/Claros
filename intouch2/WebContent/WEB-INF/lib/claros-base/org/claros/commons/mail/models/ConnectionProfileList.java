package org.claros.commons.mail.models;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Umut Gokbayrak
 */
public class ConnectionProfileList {
	private static Log log = LogFactory.getLog(ConnectionProfileList.class);
	public static HashMap<String, ConnectionProfile> conList = new HashMap<String, ConnectionProfile>();

	/**
	 * 
	 */
	public ConnectionProfileList() {
		super();
	}
	
	public void addConnectionProfile(ConnectionProfile con) {
		if (con == null) return;
		conList.put(con.getShortName(), con);
	}

	public static HashMap<String, ConnectionProfile> getConList() {
		return conList;
	}
	
	public static ConnectionProfile getProfileByShortName(String shortName) {
		ConnectionProfile con = (ConnectionProfile)conList.get(shortName);
		if (con == null) {
			log.warn("The Shortname searched at the ConnectionProfileList does not correspond to a ConnectionProfile");
			return null;
		}
		return con;
	}
}
