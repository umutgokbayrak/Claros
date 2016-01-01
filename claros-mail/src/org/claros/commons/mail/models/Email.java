package org.claros.commons.mail.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.claros.commons.mail.utility.Utility;

/**
 * @author Umut Gokbayrak
 *
 */
public class Email implements Serializable {
	private static final long serialVersionUID = 318395800499501554L;
	private Long msgId;
	private transient ArrayList<EmailPart> parts = new ArrayList<EmailPart>();
	private EmailHeader baseHeader;
	private ArrayList<String> headers = new ArrayList<String>();
	private String bodyText;

	public ArrayList<EmailPart> getParts() {
		return parts;
	}

	public void setParts(ArrayList<EmailPart> parts) {
		this.parts = parts;
	}

	public boolean addHeader(String name, Object value) {
		headers.add(name + (char)6 + value);
		return true;
	}
	
	/**
	 * @return
	 */
	public ArrayList<String> getHeaders() {
		return headers;
	}

	/**
	 * @param list
	 */
	public void setHeaders(ArrayList<String> list) {
		headers = list;
	}

	class HeaderPair {
		String name;
		Object value; 
	}

	/**
	 * @return
	 */
	public EmailHeader getBaseHeader() {
		return baseHeader;
	}

	/**
	 * @param header
	 */
	public void setBaseHeader(EmailHeader header) {
		baseHeader = header;
	}
	/**
	 * @return
	 */
	public boolean isCcExists() {
		return (getBaseHeader().getCc() != null);
	}

	/**
	 * @return
	 */
	public boolean isDateExists() {
		return (getBaseHeader().getDate() != null);
	}

	public String getTo() {
		return Utility.addressArrToString(getBaseHeader().getTo());
	}

	public String getFrom() {
		String from = Utility.addressArrToString(getBaseHeader().getFrom());
		if (from.equals("")) {
			from = "-";
		}
		return from;
	}

	public String getCc() {
		return Utility.addressArrToString(getBaseHeader().getCc());
	}

	public Date getDate() {
		return getBaseHeader().getDate();
	}

	public String getSubject() {
		String subject = org.claros.commons.utility.Utility.doCharsetCorrections(getBaseHeader().getSubject());
		if (subject == null || subject.length() == 0) {
			subject = "No Subject";
		}
		return subject;
	}

	/**
	 * @return
	 */
	public String getBodyText() {
		return bodyText;
	}

	/**
	 * @param string
	 */
	public void setBodyText(String string) {
		bodyText = string;
	}

	/**
	 * @return
	 */
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * @param long1
	 */
	public void setMsgId(Long long1) {
		msgId = long1;
	}

}
