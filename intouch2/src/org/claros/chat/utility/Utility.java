package org.claros.chat.utility;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.db.DbConfigList;

import com.jenkov.mrpersister.PersistenceManager;
import com.jenkov.mrpersister.itf.IGenericDao;

public class Utility {
	public static PersistenceManager persistMan = new PersistenceManager();
	private static Log log = LogFactory.getLog(Utility.class);
	public static String msnTransport = "";
	public static String yahooTransport = "";
	public static String icqTransport = "";
	public static String aolTransport = "";
	
	static {
		try {
			msnTransport = PropertyFile.getConfiguration("/config/config.xml").getString("chat.msn-transport");
		} catch (Exception e) {
			log.debug("unable to get msn chat domain", e);
		}
		try {
			yahooTransport = PropertyFile.getConfiguration("/config/config.xml").getString("chat.yahoo-transport");
		} catch (Exception e) {
			log.debug("unable to get yahoo chat domain", e);
		}
		try {
			icqTransport = PropertyFile.getConfiguration("/config/config.xml").getString("chat.icq-transport");
		} catch (Exception e) {
			log.debug("unable to get icq chat domain", e);
		}
		try {
			aolTransport = PropertyFile.getConfiguration("/config/config.xml").getString("chat.aol-transport");
		} catch (Exception e) {
			log.debug("unable to get aol chat domain", e);
		}
	}

	public static IGenericDao getDbConnection() throws Exception {
		return getDbConnection("file");
	}

	public static IGenericDao getTxnDbConnection() throws Exception {
		return getDbConnection("file");
	}

	public static IGenericDao getDbConnection(String name) throws Exception {
		BasicDataSource bs = (BasicDataSource)DbConfigList.getDataSourceById(name);
//		log.debug("Number active: " + bs.getNumActive());
//		log.debug("Number idle: " + bs.getNumIdle());
		
		Connection con = bs.getConnection();
		return persistMan.getGenericDaoFactory().createDao(con);
	}
	
	public static IGenericDao getTxnDbConnection(String name) throws Exception {
		Connection con = DbConfigList.getDataSourceById(name).getConnection();
		con.setAutoCommit(false);
		return persistMan.getGenericDaoFactory().createDao(con);
	}

	public static String replaceAllOccurances(String str, String from, String to) {
		if (str == null || str.length() == 0) {
			return str;
		} else if (str.length() == 1 && str.equals(from)) {
			return to;
		} else if (str.length() == 1 && !str.equals(from)) {
			return str;
		}
		int j = -1;
		while ((j = str.indexOf(from)) >= 0) {
			str = str.substring(0, j) + (char)5 + str.substring(j + from.length());
		}

		int i = -1;
		while ((i = str.indexOf((char)5)) >= 0) {
			str = str.substring(0, i) + to + str.substring(i + 1);
		}

		return str;
	}

	public static String extendedTrim(String str, String trimStr) {
		if (str == null || str.length() == 0)
			return str;
		for (str = str.trim(); str.startsWith(trimStr); str = str.substring(trimStr.length()).trim());
		for (; str.endsWith(trimStr); str = str.substring(0, str.length() - trimStr.length()).trim());
		return str;
	}

	public static Object checkDecimalFormat(Object number) {
		String str = "-";
		try {
			str = number.toString();
			int posDot = str.indexOf(".");
			int posComma = str.indexOf(",");

			/*
			 * Double.parseDouble ile bu çakar. Nokta ile virgülün yer değiştirmesi lazım
			 */
			if (posComma > posDot) {
				str = Utility.replaceAllOccurances(str, ".", "");
				str = Utility.replaceAllOccurances(str, ",", ".");
			} else if (posComma == -1 && posDot > 0) {
				int lastPosDot = str.lastIndexOf(".");
				if (posDot != lastPosDot) {
					str = Utility.replaceAllOccurances(str, ".", "");
				}
			}
		} catch (Exception e) {
			str = "-";
		}
		return str;
	}

	public static String doCharsetCorrections(String str) {
		if (str == null) return "";
		
		String extraChars[] = {"\u00FD","\u00DD","\u00FE","\u00DE","\u00F0","\u00D0"};
		String unicode[] = {"\u0131", "\u0130", "\u015F", "\u015E", "\u011F", "\u011E"};
		for (int i=0;i<extraChars.length;i++) {
			while (str.indexOf(extraChars[i]) != -1) {
				String tmp = str;
				str = tmp.substring(0, tmp.indexOf(extraChars[i])) 
					+ unicode[i] + tmp.substring (tmp.indexOf(extraChars[i])+1, tmp.length());
			}
		}
		return str;
	}

	public static String htmlSpecialChars(String input) {
		StringBuffer filtered;
		try {
			filtered = new StringBuffer(input.length());
			char c;
			for (int i = 0; i < input.length(); i++) {
				c = input.charAt(i);
				if (c == '<') {
					filtered.append("&lt;");
				} else if (c == '>') {
					filtered.append("&gt;");
				} else if (c == '"') {
					filtered.append("&quot;");
				} else if (c == '&') {
					filtered.append("&amp;");
				} else {
					filtered.append(c);
				}
			}
		} catch (Exception e) {
			return input;
		}
		return (filtered.toString());
	}

}
