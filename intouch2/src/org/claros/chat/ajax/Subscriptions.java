package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.controllers.TrafficController;
import org.claros.chat.threads.ChatListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

public class Subscriptions extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7617721914260091646L;

	/**
	 * Constructor of the object.
	 */
	public Subscriptions() {
		super();
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

		// get the printwriter and prepare the html
		response.setContentType("text/html");
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();

		XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
		if (conn != null) {
			String xmppUser = conn.getUser();
			if (xmppUser != null) {
				ChatListener listener = TrafficController.getListener(xmppUser);

				if (listener != null) {
					List msgs = listener.getNewSubscriptions();

					if (msgs != null && msgs.size() > 0) {
						Presence prs = null;
						String from = null;

						for (int i=0;i<msgs.size(); i++) {
							prs = (Presence)msgs.get(i);
							from = prs.getFrom();
							out.println("alert('" + from + "');");
							
							// TODO: subscribe onaylama olayı
							prs.setType(Presence.Type.subscribed);
							prs.setFrom(listener.getUser());
							prs.setTo(from);
							conn.sendPacket(prs);
						}
					}
				}
			}
		}
		out.flush();
		out.close();
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
}
