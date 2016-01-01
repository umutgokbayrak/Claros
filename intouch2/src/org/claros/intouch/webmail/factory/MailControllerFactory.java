package org.claros.intouch.webmail.factory;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.webmail.controllers.DbMailControllerImpl;
import org.claros.intouch.webmail.controllers.ImapMailControllerImpl;
import org.claros.intouch.webmail.controllers.MailController;

/**
 * @author Umut Gokbayrak
 */
public class MailControllerFactory {
	private AuthProfile auth;
	private ConnectionProfile profile;
	private ConnectionMetaHandler handler;
	private String folder;
	
	/**
	 * used to disable default contstructor
	 */
	@SuppressWarnings("unused")
	private MailControllerFactory() {
		super();
	}
	
	public MailControllerFactory(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler, String folder) {
		this.auth = auth;
		this.profile = profile;
		this.handler = handler; 
		this.folder = folder;
	}
	
	public MailController getMailController() {
		if (profile.getProtocol().equals(Constants.POP3)) {
			return new DbMailControllerImpl(auth, profile, handler, folder);
		} else {
			return new ImapMailControllerImpl(auth, profile, handler, folder);
		}
	}

}
