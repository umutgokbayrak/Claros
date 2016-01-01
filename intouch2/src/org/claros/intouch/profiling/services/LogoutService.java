package org.claros.intouch.profiling.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.claros.chat.controllers.TrafficController;
import org.claros.chat.threads.ChatListener;
import org.claros.chat.threads.ChatSender;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.protocols.Protocol;
import org.claros.commons.mail.protocols.ProtocolFactory;
import org.jivesoftware.smack.XMPPConnection;

public class LogoutService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 418575745334176421L;

	/**
	 * Constructor of the object.
	 */
	public LogoutService() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		logoutMail(request.getSession(false));
		logoutChat(request.getSession(false));

		out.print("ok");
	}

	/**
	 * 
	 * @param request
	 */
	public static void logoutMail(HttpSession sess) {
		try {
			ConnectionProfile profile = getConnectionProfile(sess);
			if (profile != null) {
				ConnectionMetaHandler handler = getConnectionHandler(sess);
				AuthProfile auth = getAuthProfile(sess);
				ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
				if (factory != null) {
					Protocol protocol = factory.getProtocol(null);
					if (protocol != null) {
						protocol.disconnect();
					}
				}
			}
		} catch (Throwable e) {
		}
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
		doGet(request, response);
	}

	/**
	 * 
	 * @param request
	 */
	public static void logoutChat(HttpSession sess) {
		if (sess != null) {
			String user = null;
			try {
				XMPPConnection conn = (XMPPConnection)sess.getAttribute("conn");
				if (conn != null) {
					user = conn.getUser();
					conn.disconnect();
					sess.setAttribute("conn", null);
				}
			} catch (Throwable e) {}

			try {
				ChatListener listener = TrafficController.getListener(user);
				if (listener != null) {
					listener.terminate();
					if (user != null) {
						TrafficController.removeListener(user);
					}
				}
			} catch (Throwable e) {}
			
			try {
				ChatSender sender = TrafficController.getSender(user);
				if (sender != null) {
					sender.terminate();
					if (user != null) {
						TrafficController.removeSender(user);
					}
				}
			} catch (Throwable e) {}

			try {
//				sess.invalidate();
			} catch (Throwable e) {}
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static ConnectionProfile getConnectionProfile(HttpSession sess) {
		return (ConnectionProfile)sess.getAttribute("profile");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static ConnectionMetaHandler getConnectionHandler(HttpSession sess) {
		return (ConnectionMetaHandler)sess.getAttribute("handler");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static AuthProfile getAuthProfile(HttpSession sess) {
		return (AuthProfile)sess.getAttribute("auth");
	}
}
