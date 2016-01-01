package org.claros.intouch.webmail.factory;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.webmail.controllers.DbInboxControllerImpl;
import org.claros.intouch.webmail.controllers.ImapInboxControllerImpl;
import org.claros.intouch.webmail.controllers.InboxController;

/**
 * @author Umut Gokbayrak
 */
public class InboxControllerFactory {
	private AuthProfile auth;
	private ConnectionProfile profile;
	private ConnectionMetaHandler handler;
	
	/**
	 * used to disable default contstructor
	 */
	@SuppressWarnings("unused")
	private InboxControllerFactory() {
		super();
	}
	
	public InboxControllerFactory(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		this.auth = auth;
		this.profile = profile;
		this.handler = handler; 
	}
	
	public InboxController getInboxController() {
		if (profile.getProtocol().equals(Constants.POP3)) {
			return new DbInboxControllerImpl(auth, profile, handler);
		} else {
			return new ImapInboxControllerImpl(auth, profile, handler);
		}
	}
}
