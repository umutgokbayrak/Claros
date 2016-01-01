package org.claros.intouch.common.utility;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.claros.commons.db.DbConfigList;

import com.jenkov.mrpersister.itf.IGenericDao;

/**
 * @author Umut Gokbayrak
 */
public class Utility {

	public static IGenericDao getDbConnection() throws Exception {
		return getDbConnection("file");
	}

	public static IGenericDao getTxnDbConnection() throws Exception {
		return getDbConnection("file");
	}

	public static IGenericDao getDbConnection(String name) throws Exception {
		BasicDataSource bs = (BasicDataSource)DbConfigList.getDataSourceById(name);
		
		Connection con = bs.getConnection();
		return Constants.persistMan.getGenericDaoFactory().createDao(con);
	}
	
	public static IGenericDao getTxnDbConnection(String name) throws Exception {
		Connection con = DbConfigList.getDataSourceById(name).getConnection();
		con.setAutoCommit(false);
		return Constants.persistMan.getGenericDaoFactory().createDao(con);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlCheck(String str) {
		if (str == null) {
			return "";
		}
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "<", "&lt;");
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, ">", "&gt;");
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "\"", "&quot");
		return str;
	}

	public static String tooltipCheck(String str) {
		str = htmlCheck(str);
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "[", "(");
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "]", ")");
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "\"", "");
		str = org.claros.commons.utility.Utility.replaceAllOccurances(str, "'", "");
		return str;
	}

}
