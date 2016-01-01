package org.claros.commons.mail.protocols;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Umut Gokbayrak
 */
public class SmtpAuthenticator extends Authenticator {
	private String username;
	private String password;
	
	public SmtpAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		PasswordAuthentication pa = new PasswordAuthentication(username, password);
		return pa;					
	}

}
