package org.claros.commons.utility;

import java.net.URLDecoder;
import java.util.Locale;

public class Utility {
	
	private final static char trChars[] = {'\u0131', '\u0130', '\u015F', '\u015E', '\u011F', '\u011E', 
											'\u00fd', '\u00dd', '\u00fe', '\u00de', '\u00f0', '\u00d0', 
											'\u00E7', '\u00C7', '\u00FC', '\u00DC', '\u00F6', '\u00D6'};
	private final static char enChars[] = {'i', 'I', 's', 'S', 'g', 'G', 'i', 'I', 's', 'S', 
											'g', 'G', 'c', 'C', 'u', 'U', 'o', 'O'};
	private final static char enLowerCaseChars[] = {'i', 'i', 's', 's', 'g', 'g', 'i', 'i', 's', 's', 
											'g', 'g', 'c', 'c', 'u', 'u', 'o', 'o'};
	private final static String trUnicodes[] = {"&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", 
											"&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", 
											"&#231;", "&#199;", "&#252;", "&#220;", "&#246;", "&#214;"};
	
	private final static char trDirtyChars[] = { '\u00fd', '\u00dd', '\u00fe', '\u00de', '\u00f0', '\u00d0' };
	private final static char trCleanChars[] = { '\u0131', '\u0130', '\u015F', '\u015E', '\u011F', '\u011E' };

	/**
	 * 
	 * @param str
	 * @param from
	 * @param to
	 * @return
	 */
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

	/**
	 * 
	 * @param str
	 * @param trimStr
	 * @return
	 */
	public static String extendedTrim(String str, String trimStr) {
		if (str == null || str.length() == 0)
			return str;
		for (str = str.trim(); str.startsWith(trimStr); str = str.substring(trimStr.length()).trim());
		for (; str.endsWith(trimStr); str = str.substring(0, str.length() - trimStr.length()).trim());
		return str;
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static Object checkDecimalFormat(Object number) {
		String str = "-";
		try {
			str = number.toString();
			int posDot = str.indexOf(".");
			int posComma = str.indexOf(",");

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

	/**
	 * 
	 * @param str
	 * @return
	 */
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
		/*
		str = Utility.replaceAllOccurances(str,"Åz","\u015E");
		str = Utility.replaceAllOccurances(str,"Äz","\u011E");
		str = Utility.replaceAllOccurances(str,"Ã‡","\u00C7");
		str = Utility.replaceAllOccurances(str,"Ä°","\u0130");
		str = Utility.replaceAllOccurances(str,"Ã–","\u00D6");
		str = Utility.replaceAllOccurances(str,"Ã¼","\u00FC");
		str = Utility.replaceAllOccurances(str,"ÅŸ","\u015F");
		str = Utility.replaceAllOccurances(str,"ÄŸ","\u011F");
		str = Utility.replaceAllOccurances(str,"Ã§","\u00E7");
		str = Utility.replaceAllOccurances(str,"Ä±","\u0131");
		str = Utility.replaceAllOccurances(str,"Ã¶","\u00F6");
		*/
		return str;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
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

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static String convertTRCharsToHtmlSafe(String str) {
		if ((str == null) || (str.length() == 0))
			return "";

		int pos = -1;
		for (int i = 0; i < trChars.length; i++) {
			while ((pos = str.indexOf(trChars[i])) != -1) {
				str = str.substring(0, pos)
				+ trUnicodes[i] 
				+ str.substring(pos+1, str.length());
			}
		}
		return str;
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static String convertHtmlSafeToTRChars(String str) {
		if ((str == null) || (str.length() == 0))
			return "";

		int pos = -1;
		for (int i = 0; i < trUnicodes.length; i++) {
			if (i<6 || i>11) {
				while ((pos = str.indexOf(trUnicodes[i])) != -1) {
					str = str.substring(0, pos)
					+ trChars[i] 
					+ str.substring(pos+trUnicodes[i].length());
				}
			}
		}
		return str;
	}
	
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public static String updateTRChars(String str) {
		if ((str == null) || (str.length() == 0))
			return "";
		String ret = "";
		try{
			ret = javax.mail.internet.MimeUtility.decodeText(str);
		}
		catch(Exception e){
		}
		String strLowerCase = ret.toLowerCase(new Locale("en", "US"));
		if(strLowerCase.startsWith("=?iso-8859-9?q?")) {
			ret = ret.substring(15);
			if(strLowerCase.endsWith("?=")) {
				ret = ret.substring(0, ret.length()-2);
			}
			else
			{
				int pos = -1;
				while ((pos = ret.indexOf("?=")) != -1) {
					ret = ret.substring(0, pos) 
						+ ret.substring(pos+2, ret.length());
				}
			}
			try {
				ret = ret.replace('=', '%');
				ret = URLDecoder.decode(ret, "iso-8859-9");
			} catch(Exception ex) { }
		}
		for (int i = 0; i < trDirtyChars.length; i++) {
			int pos = -1;
			while ((pos = ret.indexOf(trDirtyChars[i])) != -1) {
				ret = ret.substring(0, pos)
					+ trCleanChars[i] 
					+ ret.substring(pos+1, ret.length());
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public static String convertTRCharsToENChars(String str) {
		if ((str == null) || (str.length() == 0))
			return "";

		int pos = -1;
		for (int i = 0; i < trChars.length; i++) {
			while ((pos = str.indexOf(trChars[i])) != -1) {
				str = str.substring(0, pos)
				+ enChars[i] 
				+ str.substring(pos+1, str.length());
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public static String convertTRCharsToENLowerCaseChars(String str) {
		if ((str == null) || (str.length() == 0))
			return "";

		int pos = -1;
		for (int i = 0; i < trChars.length; i++) {
			while ((pos = str.indexOf(trChars[i])) != -1) {
				str = str.substring(0, pos)
				+ enLowerCaseChars[i] 
				+ str.substring(pos+1, str.length());
			}
		}
		return str;
	}
}
