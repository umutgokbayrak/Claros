package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.comparator.ComparatorDate;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.controllers.InboxController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.factory.InboxControllerFactory;

public class ListNewMailHeadersService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6686687365451641031L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.print("<items>");
		
		AuthProfile auth = getAuthProfile(request);

		// prepare variables
		List headers = null;
		ConnectionMetaHandler handler = getConnectionHandler(request);
		ConnectionProfile profile = getConnectionProfile(request);

		FolderControllerFactory foldFact = null;
		FolderController folderCont = null;
		String currFolder = org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile);

		try {
			if (auth == null) {
				throw new org.claros.commons.exception.NoPermissionException();
			}
			InboxControllerFactory inFact = new InboxControllerFactory(auth, profile, handler);
			InboxController inCont = inFact.getInboxController();
			handler = inCont.checkEmail();
			request.getSession().setAttribute("handler", handler);
			foldFact = new FolderControllerFactory(auth, profile, handler);
			folderCont = foldFact.getFolderController();

			// time to fetch the headers
			headers = folderCont.getHeadersByFolder(currFolder);

			// sort the headers
			Collections.sort(headers, new ComparatorDate(false));
			
			// organize and generate XML from the headers.
			if (headers != null) {
				EmailHeader tmp = null;
				for (int i=0;i<headers.size();i++) {
					tmp = (EmailHeader)headers.get(i);
					if (tmp.getUnread().booleanValue() == true) {
						out.print("<item>");
						out.print("<id> " + tmp.getMessageId() + "</id>");
						out.print("<from> " + Utility.htmlSpecialChars(tmp.getFromShown()) + "</from>");
						out.print("<subject> " + Utility.htmlSpecialChars(tmp.getSubject()) + "</subject>");
						out.print("</item>");
					}
				}
			}
			out.print("</items>");
			out.print("<result>0</result>");
		} catch (Exception e) {
			out.print("</items>");
			out.print("<result>1</result>");
		}
		out.print("</data>");
	}

}
 