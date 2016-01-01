package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.factory.FolderControllerFactory;

public class GetUnreadCountService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2778234149147865257L;

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
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
		ConnectionProfile profile = getConnectionProfile(request);
		
		try {
			FolderControllerFactory foldFact = new FolderControllerFactory(getAuthProfile(request), profile, handler);
			Integer count = null;
			if (profile.getProtocol().equals(Constants.IMAP)) {
				count = foldFact.getFolderController().countUnreadMessages(Constants.FOLDER_INBOX(profile));
			} else {
				count = foldFact.getFolderController().countTotalMessages(Constants.FOLDER_INBOX(profile));
			}
			int iCount = -1;
			if (count != null) {
				iCount = count.intValue();
			} 
			out.print(iCount);
		} catch (Exception e) {
			out.print("-1");
		}
	}
}
