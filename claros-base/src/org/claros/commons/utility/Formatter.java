package org.claros.commons.utility;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Umut Gokbayrak
 */
public class Formatter {

	private Formatter() {
		super();
	}

	/**
	 * 
	 * @param df
	 * @param number
	 * @return
	 */
	private static String format(DecimalFormat df, Object number) {
		if (number == null) {
			return null;
		}
		number = Utility.checkDecimalFormat(number);

		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		try {
			if (!number.equals("-")) {
				return df.format(Double.parseDouble(number.toString()));
			} else {
				return number.toString();
			}
		} catch (NumberFormatException e) {
			return number.toString();
		}
	}

	/**
	 * 
	 * @param number
	 * @param groupingActive
	 * @return
	 */
	public static String formatDecimal(Object number, boolean groupingActive) {
		if (groupingActive) {
			return format(new DecimalFormat("#,##0.00"), number);
		} else {
			return format(new DecimalFormat("0.00"), number);
		}
	}

	/**
	 * 
	 * @param number
	 * @param groupingActive
	 * @return
	 */
	public static String formatInteger(Object number, boolean groupingActive) {
		if (groupingActive) {
			return format(new DecimalFormat("#,##0"), number);
		} else {
			return format(new DecimalFormat("##0"), number);
		}
	}

	/**
	 * 
	 * @param number
	 * @param groupingActive
	 * @param digits
	 * @return
	 */
	public static String formatSensitive(Object number, boolean groupingActive, int digits) {
		String digitStr = "";
		for (int i = 0; i < digits; i++) {
			digitStr += "0";
		}
		if (groupingActive) {
			return format(new DecimalFormat("#,##0." + digitStr), number);
		} else {
			return format(new DecimalFormat("0." + digitStr), number);
		}
	}

	/**
	 * 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String formatWithPattern(Object number, String pattern) {
		return format(new DecimalFormat(pattern), number);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Object date, String pattern) {
		if (date != null && date instanceof Timestamp) {
			date = new Date(((Timestamp)date).getTime());
		}
		
		if (date != null && date instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static double getDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
		obj = Utility.checkDecimalFormat(obj);
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
