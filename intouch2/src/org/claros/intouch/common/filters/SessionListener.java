package org.claros.intouch.common.filters;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.claros.intouch.profiling.services.LogoutService;
import org.claros.intouch.webmail.services.DeleteAllAttachmentsService;

/**
 * @version 	1.0
 * @author		Umut Gökbayrak
 */
public class SessionListener implements HttpSessionListener {
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession sess = (HttpSession)arg0.getSession();
		List atts = (List)sess.getAttribute("attachments");
		if (atts != null) {
			DeleteAllAttachmentsService.deleteAll(atts);
		}
		LogoutService.logoutMail(sess);
		LogoutService.logoutChat(sess);
		sess.invalidate();
	}
	
}
