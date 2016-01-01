package org.claros.commons.utility.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.claros.commons.utility.CommonUtility;
import org.claros.commons.utility.Formatter;

/**
 * @author Umut Gokbayrak
 */
public class FormatterImpl implements Formatter {
	private static CommonUtility commonUtility = new CommonUtilityImpl();

	private FormatterImpl() {
		super();
	}

	/**
	 * 
	 * @param df
	 * @param number
	 * @return
	 */
	private String format(DecimalFormat df, Object number) {
		if (number == null) {
			return null;
		}
		
		number = commonUtility.checkDecimalFormat(number);

		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		try {
			return df.format(Double.parseDouble(number.toString()));
		} catch (NumberFormatException e) {
			return number.toString();
		}
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#formatDecimal(java.lang.Object, boolean)
	 */
	public String formatDecimal(Object number, boolean groupingActive) {
		if (groupingActive) {
			return format(new DecimalFormat("#,##0.00"), number);
		} else {
			return format(new DecimalFormat("0.00"), number);
		}
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#formatInteger(java.lang.Object, boolean)
	 */
	public String formatInteger(Object number, boolean groupingActive) {
		if (groupingActive) {
			return format(new DecimalFormat("#,##0"), number);
		} else {
			return format(new DecimalFormat("##0"), number);
		}
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#formatSensitive(java.lang.Object, boolean, int)
	 */
	public String formatSensitive(Object number, boolean groupingActive, int digits) {
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

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#formatNumberWithPattern(java.lang.Object, java.lang.String)
	 */
	public String formatNumberWithPattern(Object obj, String pattern) {
		return format(new DecimalFormat(pattern), obj);
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#formatDateWithPattern(java.lang.Object, java.lang.String)
	 */
	public String formatDateWithPattern(Object date, String pattern) {
		if (date != null && date instanceof Timestamp) {
			date = new Date(((Timestamp)date).getTime());
		}
		
		if (date != null && date instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.Formatter#getDouble(java.lang.Object)
	 */
	public double getDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
		obj = commonUtility.checkDecimalFormat(obj);
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
