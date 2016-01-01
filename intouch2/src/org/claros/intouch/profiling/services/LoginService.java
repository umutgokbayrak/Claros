package org.claros.intouch.profiling.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.MailAuth;
import org.claros.commons.auth.exception.LoginInvalidException;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.exception.ServerDownException;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.ConnectionProfileList;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;

public class LoginService extends HttpServlet {
	private static final long serialVersionUID = -7565470602365037845L;
	private static Log log = LogFactory.getLog(LoginService.class);

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

		log.debug("Fetching the server profile from the config.xml file");
		
		try {
			HashMap map = ConnectionProfileList.getConList();
			if (map != null) {
				Set set = map.keySet();
				if (set == null) {
					log.fatal("Mail server profile is not accessable. Can't continue. :( Sorry. Please check your config.xml file.1");
					throw new SystemException();
				}
				Object arr[] = set.toArray();
				if (arr == null || arr.length <= 0) {
					log.fatal("Mail server profile is not accessable. Can't continue. :( Sorry. Please check your config.xml file.2");
					throw new SystemException();
				}
				ConnectionProfile profile = ConnectionProfileList.getProfileByShortName((String)arr[0]);
				if (profile == null) {
					log.fatal("Mail server profile is not accessable. Can't continue. :( Sorry. Please check your config.xml file.3");
					throw new SystemException();
				}
				log.debug("I've got the mail server profile. Keep on... " + profile.toString());
				
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				if (username != null && password != null) {
					AuthProfile auth = new AuthProfile();
					auth.setUsername(username);
					auth.setPassword(password);
					ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
					log.debug("Starting authentication");

					try {
						handler = MailAuth.authenticate(profile, auth, handler);
						if (handler != null) {
							log.debug("Authentication was successful... :) Good news!");
							
							request.getSession().setAttribute("handler", handler);
							request.getSession().setAttribute("auth", auth);
							request.getSession().setAttribute("profile", profile);

							// create default mailboxes if not exists
							try {
								log.debug("Trying to create default mailbox folders if they don't exist.");

								FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
								FolderController foldCont = factory.getFolderController();
								foldCont.createDefaultFolders();
							} catch (Exception e) {
								log.error("unable to create default folders!!!! It will probably fail but giving a try", e);
							}

							log.debug("Everything was fine. Sending an OK signal to the UI");
							out.print("ok");
						} else {
							log.debug("Can't authenticate. username and password is most probably wrong.1");
							out.print("no");
						}
					} catch (LoginInvalidException e) {
						log.debug("Can't authenticate. username and password is most probably wrong.3");
						out.print("no");
					} catch (ServerDownException e) {
						log.error("Can't reach the server. Please make sure everything is fine at config.xml file and server is up and running.");
						out.print("system");
					}
				} else {
					log.debug("Can't authenticate. username and password is most probably wrong.2");
					out.print("no");
				}
			} else {
				throw new SystemException();
			}
		} catch (SystemException e) {
			log.error("Can't get mail server list. Please make sure everything is fine at config.xml file.", e);
			out.print("system");
		} catch (Throwable e) {
			log.error("Unknown error. Please check logs for more information. ", e);
			out.print("system");
		}
	}
}
