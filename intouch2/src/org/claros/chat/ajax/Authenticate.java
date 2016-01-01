package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.chat.controllers.TrafficController;
import org.claros.chat.threads.ChatListener;
import org.claros.chat.threads.ChatSender;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Authenticate extends HttpServlet {
	private static Log log = LogFactory.getLog(Authenticate.class);
	private static final ConnectionConfiguration googleConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6002125607784963740L;

	/**
	 * Constructor of the object.
	 */
	public Authenticate() {
		super();
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		request.getSession().setAttribute("user", username);
		String password = request.getParameter("password");
		String server = request.getParameter("server");

		request.getSession().setAttribute("defaultDomain", null);
		try {
			XMPPConnection connection = null;
			if (server == null || server.equals("Google Talk")) {
				request.getSession().setAttribute("defaultDomain", "gmail.com");
				try {
					if (log.isDebugEnabled()) {
					// 	googleConfig.setDebuggerEnabled(true);
					}
					googleConfig.setReconnectionAllowed(true);
					
					connection = new XMPPConnection(googleConfig);
					
					if (username.indexOf("@") > 0) {
						username = username.substring(0, username.indexOf("@"));
					}
				} catch (Exception e1) {
					throw e1;
				}
			} else {
				try {
					int port = 5222;
					if (server.indexOf(":") > 0) {
						try {
							port = Integer.parseInt(server.substring(server.indexOf(":") + 1));
							server = server.substring(0, server.indexOf(":"));
						} catch (Exception e) {}
					}
					request.getSession().setAttribute("defaultDomain", server);

					ConnectionConfiguration config = new ConnectionConfiguration(server, port, "claros.org");
					config.setExpiredCertificatesCheckEnabled(false);
					config.setNotMatchingDomainCheckEnabled(false);
					config.setSelfSignedCertificateEnabled(true);
					config.setReconnectionAllowed(true);

					if (log.isDebugEnabled()) {
					//	config.setDebuggerEnabled(true);
					}

					connection = new XMPPConnection(config);
				} catch (Exception e1) {
					throw e1;
				}
			}

			try {
				connection.connect();
				connection.login(username, password);
				log.debug("connection established for user: " + username);
			} catch (XMPPException e) {
				log.info(e);
				// maybe user doesn't exist so try to create one.
				try {
					connection.connect();
					connection.getAccountManager().createAccount(username, password);
					connection.login(username, password);
				} catch (Exception e1) {
					log.info(e1);
					throw e1;
				}
			}
			request.getSession().setAttribute("conn", connection);

			// start the user's chat listener thread
			String user = connection.getUser();
			if (request.getSession().getAttribute("listener") == null) {
				ChatListener list = new ChatListener(user, connection);
				TrafficController.addListener(user, list);
				list.start();
				log.debug("listener created and added to the traffic controller for user: " + username);
			}

			if (request.getSession().getAttribute("sender") == null) {
				// start user's chat sender thread
				String dd = (String)request.getSession().getAttribute("defaultDomain");
				ChatSender sender = new ChatSender(username, connection, dd);
				TrafficController.addSender(user, sender);
				sender.start();
				log.debug("sender created and added to the traffic controller for user: " + username);
			}
			
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
		log.debug("authentication complete finishing servlet for user: " + username);
	}
}
