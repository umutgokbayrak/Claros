package org.claros.intouch.webmail.factory;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.webmail.controllers.DbFolderControllerImpl;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.controllers.ImapFolderControllerImpl;

/**
 * @author Umut Gokbayrak
 */
public class FolderControllerFactory {
	private AuthProfile auth;
	private ConnectionProfile profile;
	private ConnectionMetaHandler handler;
	
	/**
	 * used to disable default contstructor
	 */
	@SuppressWarnings("unused")
	private FolderControllerFactory() {
		super();
	}
	
	public FolderControllerFactory(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		this.auth = auth;
		this.profile = profile;
		this.handler = handler; 
	}
	
	public FolderController getFolderController() {
		if (profile.getProtocol().equals(Constants.POP3)) {
			return new DbFolderControllerImpl(auth, profile, handler);
		} else {
			return new ImapFolderControllerImpl(auth, profile, handler);
		}
	}

}
