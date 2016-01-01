package org.claros.commons.mail.utility;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.claros.commons.configuration.PropertyFile;

/**
 * @author Umut Gokbayrak
 *
 */
public class Utility {
	private Utility() {
		super();
	}

	/**
	 * 
	 * @param addr
	 * @return
	 */
	public static String[] addressArrToStringArr(Address[] addr) {
		if (addr != null && addr.length > 0) {
			String[] str = new String[addr.length];
			for (int j = 0; j < addr.length; j++) {
				InternetAddress add = (InternetAddress) addr[j];
				String personal = org.claros.commons.utility.Utility.doCharsetCorrections(add.getPersonal());
				String address = org.claros.commons.utility.Utility.doCharsetCorrections(add.getAddress());

				if (personal != null && personal.length() > 0) {
					if (address != null && address.length() > 0) {
						str[j] = personal + " <" + address + ">";
					} else {
						str[j] = personal;
					}
				} else {
					if (address != null && address.length() > 0) {
						str[j] = address;
					} else {
						str[j] = "";
					}
				}
			}
			return str;
		}
		return null;
	}

	/**
	 * 
	 * @param addr
	 * @return
	 */
	public static String addressArrToString(Address[] addr) {
		String addrStr = "";
		String str[] = addressArrToStringArr(addr);
		if (str != null && str.length > 0) {
			addrStr = "";
			for (int i = 0; i < str.length; i++) {
				addrStr += str[i] + ", ";
			}
		}
		String msg = org.claros.commons.utility.Utility.extendedTrim(addrStr, ",");
		return org.claros.commons.utility.Utility.doCharsetCorrections(msg);
	}

	/**
	 * 
	 * @param addr
	 * @return
	 */
	public static String[] addressArrToStringArrShort(Address[] addr) {
		if (addr != null && addr.length > 0) {
			String[] str = new String[addr.length];
			for (int j = 0; j < addr.length; j++) {
				InternetAddress add = (InternetAddress) addr[j];
				String personal = org.claros.commons.utility.Utility.doCharsetCorrections(add.getPersonal());
				String address = org.claros.commons.utility.Utility.doCharsetCorrections(add.getAddress());

				if (personal != null && personal.length() > 0) {
					str[j] = personal;
				} else if (address != null && address.length() > 0) { 
					str[j] = address;
				} else {
					str[j] = "Unknown";
				}
			}
			return str;
		}
		return null;
	}
	
	/**
	 * 
	 * @param addr
	 * @return
	 */
	public static String addressArrToStringShort(Address[] addr) {
		String addrStr = "";
		String str[] = addressArrToStringArrShort(addr);
		if (str != null && str.length > 0) {
			addrStr = "";
			for (int i = 0; i < str.length; i++) {
				addrStr += str[i] + ", ";
			}
		}
		String msg = org.claros.commons.utility.Utility.extendedTrim(addrStr, ",");
		msg =  org.claros.commons.utility.Utility.doCharsetCorrections(msg);
		return msg;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Address[] stringToAddressArray(String str) throws Exception {
		if (str == null)
			return null;
		
		str = org.claros.commons.utility.Utility.extendedTrim(str, ",");
		StringTokenizer token = new StringTokenizer(str, ",");
		if (token.countTokens() != 0) {
			Address[] outAddr = new Address[token.countTokens()];
			int counter = 0;
			while (token.hasMoreTokens()) {
				String addr = token.nextToken().trim();
				addr = org.claros.commons.utility.Utility.replaceAllOccurances(addr, "&lt;", "<");
				addr = org.claros.commons.utility.Utility.replaceAllOccurances(addr, "&gt;", ">");
				String fullname = "";
				String email = "";
				int j;
				try {
					if ((j = addr.indexOf("<")) > 0) {
						fullname = org.claros.commons.utility.Utility.extendedTrim(addr.substring(0, j).trim(), "\"");
						email = org.claros.commons.utility.Utility.extendedTrim(org.claros.commons.utility.Utility.extendedTrim(addr.substring(j + 1), ">"), "\"").trim();
						String charset = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.charset");
						outAddr[counter] = new InternetAddress(email, fullname, charset);
					} else {
						outAddr[counter] = new InternetAddress(addr);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (AddressException e) {
					e.printStackTrace();
				}
				counter++;
			}
			return outAddr;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	public static String sizeToHumanReadable(long size) {
		String out = Math.round(size / 1024) + "K";
		if (out.equals("0K")) {
			out = size + "B";
		}
		return out;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	public static String stripHTMLTags(String message) {
		StringBuffer returnMessage = new StringBuffer(message);
		try {
			int startPosition = message.indexOf("<"); // encountered the first opening brace
			int endPosition = message.indexOf(">"); // encountered the first closing braces
			while (startPosition != -1) {
				returnMessage.delete(startPosition, endPosition + 1); // remove the tag
				returnMessage.insert(startPosition, " ");
				startPosition = (returnMessage.toString()).indexOf("<"); // look for the next opening brace
				endPosition = (returnMessage.toString()).indexOf(">"); // look for the next closing brace
			}
		} catch (Throwable e) {
			// do nothing sier
		}
		return returnMessage.toString();
	}
}
