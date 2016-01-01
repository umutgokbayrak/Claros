package org.claros.commons.utility.impl;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.claros.commons.utility.CommonUtility;

/**
 * 
 * @author Umut Gokbayrak
 *
 */
public class CommonUtilityImpl implements CommonUtility {
	
	private final static char trChars[] = {'\u0131', '\u0130', '\u015F', '\u015E', '\u011F', '\u011E', '\u00fd', '\u00dd', '\u00fe', '\u00de', '\u00f0', '\u00d0', '\u00E7', '\u00C7', '\u00FC', '\u00DC', '\u00F6', '\u00D6'};
	private final static char enChars[] = {'i', 'I', 's', 'S', 'g', 'G', 'i', 'I', 's', 'S', 'g', 'G', 'c', 'C', 'u', 'U', 'o', 'O'};
	private final static char enLowerCaseChars[] = {'i', 'i', 's', 's', 'g', 'g', 'i', 'i', 's', 's', 'g', 'g', 'c', 'c', 'u', 'u', 'o', 'o'};
	private final static String trUnicodes[] = {"&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", "&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", "&#231;", "&#199;", "&#252;", "&#220;", "&#246;", "&#214;"};

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#replaceAllOccurances(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String replaceAllOccurances(String str, String from, String to) {
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

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#extendedTrim(java.lang.String, java.lang.String)
	 */
	public String extendedTrim(String str, String trimStr) {
		if (str == null || str.length() == 0)
			return str;
		for (str = str.trim(); str.startsWith(trimStr); str = str.substring(trimStr.length()).trim());
		for (; str.endsWith(trimStr); str = str.substring(0, str.length() - trimStr.length()).trim());
		return str;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#checkDecimalFormat(java.lang.Object)
	 */
	public Object checkDecimalFormat(Object number) {
		String str = null;
		try {
			str = number.toString();
			int posDot = str.indexOf(".");
			int posComma = str.indexOf(",");

			if (posComma > posDot) {
				str = replaceAllOccurances(str, ".", "");
				str = replaceAllOccurances(str, ",", ".");
			} else if (posComma == -1 && posDot > 0) {
				int lastPosDot = str.lastIndexOf(".");
				if (posDot != lastPosDot) {
					str = replaceAllOccurances(str, ".", "");
				}
			}
		} catch (Exception e) {
			str = null;
		}
		return str;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#doCharsetCorrections(java.lang.String)
	 */
	public String doCharsetCorrections(String str) {
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

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#htmlSpecialChars(java.lang.String)
	 */
	public String htmlSpecialChars(String str) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(str);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\'') {
				result.append("&#039;");
			} else if (character == '(') {
				result.append("&#040;");
			} else if (character == ')') {
				result.append("&#041;");
			} else if (character == '#') {
				result.append("&#035;");
			} else if (character == '%') {
				result.append("&#037;");
			} else if (character == ';') {
				result.append("&#059;");
			} else if (character == '+') {
				result.append("&#043;");
			} else if (character == '-') {
				result.append("&#045;");
			} else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#regExpSpecialChars(java.lang.String)
	 */
	public String regExpSpecialChars(String str) {
	    final StringBuilder result = new StringBuilder();

		final StringCharacterIterator iterator = new StringCharacterIterator(str);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '.') {
				result.append("\\.");
			} else if (character == '\\') {
				result.append("\\\\");
			} else if (character == '?') {
				result.append("\\?");
			} else if (character == '*') {
				result.append("\\*");
			} else if (character == '+') {
				result.append("\\+");
			} else if (character == '&') {
				result.append("\\&");
			} else if (character == ':') {
				result.append("\\:");
			} else if (character == '{') {
				result.append("\\{");
			} else if (character == '}') {
				result.append("\\}");
			} else if (character == '[') {
				result.append("\\[");
			} else if (character == ']') {
				result.append("\\]");
			} else if (character == '(') {
				result.append("\\(");
			} else if (character == ')') {
				result.append("\\)");
			} else if (character == '^') {
				result.append("\\^");
			} else if (character == '$') {
				result.append("\\$");
			} else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#xmlSpecialChars(java.lang.String)
	 */
	public String xmlSpecialChars(String str) {
	    final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(str);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\'') {
				result.append("&#039;");
			} else if (character == '&') {
				result.append("&amp;");
			} else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#convertTRCharsToHtmlSafe(java.lang.String)
	 */
	public String convertTRCharsToHtmlSafe(String str) {
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

	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#convertHtmlSafeToTRChars(java.lang.String)
	 */
	public String convertHtmlSafeToTRChars(String str) {
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
	
	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#convertTRCharsToENChars(java.lang.String)
	 */
	public String convertTRCharsToENChars(String str) {
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
	
	/* (non-Javadoc)
	 * @see org.claros.commons.utility.impl.CommonUtility#convertTRCharsToENLowerCaseChars(java.lang.String)
	 */
	public String convertTRCharsToENLowerCaseChars(String str) {
		if ((str == null) || (str.length() == 0))
			return "";

		int pos = -1;
		for (int i = 0; i < trChars.length; i++) {
			while ((pos = str.indexOf(trChars[i])) != -1) {
				str = str.substring(0, pos) + enLowerCaseChars[i] + str.substring(pos+1, str.length());
			}
		}
		return str;
	}
}
