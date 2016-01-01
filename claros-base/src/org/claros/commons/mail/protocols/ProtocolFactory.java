package org.claros.commons.mail.protocols;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;

/**
 * @author Umut Gokbayrak
 */
public class ProtocolFactory {
	private ConnectionProfile profile;
	private AuthProfile auth;
	private ConnectionMetaHandler handler;

	/**
	 * 
	 * @param profile
	 * @param auth
	 * @param handler
	 */
	public ProtocolFactory(ConnectionProfile profile, AuthProfile auth, ConnectionMetaHandler handler) {
		this.profile = profile;
		this.auth = auth;
		this.handler = handler;
	}
	
	/**
	 * 
	 * @return
	 */
	public Protocol getPop3() {
		return new Pop3ProtocolImpl(profile, auth, handler);
	}

	/**
	 * 
	 * @param folder
	 * @return
	 */
	public Protocol getImap(String folder) {
		return new ImapProtocolImpl(profile, auth, handler, folder);
	}
	
	/**
	 * 
	 * @param folder
	 * @return
	 */
	public Protocol getProtocol(String folder) {
		if (profile.getProtocol().equals(Constants.POP3)) {
			return getPop3();
		} else {
			return getImap(folder);
		}
	}
}
