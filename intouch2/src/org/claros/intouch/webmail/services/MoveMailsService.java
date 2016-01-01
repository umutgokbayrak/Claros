package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.MailControllerFactory;

public class MoveMailsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1791821901766371404L;
	private static Log log = LogFactory.getLog(DeleteMailService.class);

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
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();

		String ids = request.getParameter("ids");
		String srcFolder = URLDecoder.decode(request.getParameter("source"), "UTF-8");
		String targetFolder = URLDecoder.decode(request.getParameter("target"), "UTF-8");
		
		if (ids != null && srcFolder != null && targetFolder != null) {
			try {
				AuthProfile auth = getAuthProfile(request);
				ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
				ConnectionProfile profile = (ConnectionProfile)request.getSession().getAttribute("profile");

				MailControllerFactory factory = new MailControllerFactory(auth, profile, handler, srcFolder);
				MailController mailCont = factory.getMailController();
				
				// determine the ids of the messages to be deleted or moved to the trash folder
				StringTokenizer token = new StringTokenizer(ids.substring(1), "_");
				int size = token.countTokens();
				
				int msgs[] = new int[size];
				int counter = 0;
				while (token.hasMoreTokens()) {
					msgs[counter] = Integer.parseInt(token.nextToken());
					counter++;
				}

				// action time
				mailCont.moveEmails(msgs, targetFolder);
			} catch (Exception e) {
				log.info("error moving mail", e);
				out.print("fail");
			}
		}
		out.print("ok");

	}

}
