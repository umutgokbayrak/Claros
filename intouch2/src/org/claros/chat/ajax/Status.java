package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;

public class Status extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -831691288847101256L;

	/**
	 * Constructor of the object.
	 */
	public Status() {
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
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

		XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
		String act = request.getParameter("act");
		if (act.equals("load")) {
			Presence prs = null;
			if (prs != null) {
				Mode md = prs.getMode();
				String msg = (prs.getStatus() != null) ? prs.getStatus() : "";
				String stattxt = "offline";

				if (md != null) {
					if (md.equals(Presence.Mode.available)) {
						stattxt = "available";
					} else if (md.equals(Presence.Mode.away)) {
						stattxt = "away";
					} else if (md.equals(Presence.Mode.chat)) {
						stattxt = "chat";
					} else if (md.equals(Presence.Mode.dnd)) {
						stattxt = "disturb";
					} else if (md.equals(Presence.Mode.xa)) {
						stattxt = "extended_away";
					}
				}

				out.write("<data>");
				out.write("<status>" + stattxt + "</status>");
				out.write("<message>" + msg + "</message>");
				out.write("</data>");
			}
		} else if (act.equals("save")) {
			String newStat = request.getParameter("newstat");
			String newMsg = request.getParameter("newstatmsg");

			Presence pr = new Presence(Presence.Type.available);
			if (newMsg != null) {
				pr.setStatus(newMsg);
			}
			if (newStat != null) { 
				if (newStat.equals("available")) {
					pr.setMode(Presence.Mode.available);
				} else if (newStat.equals("away")) {
					pr.setMode(Presence.Mode.away);
				} else if (newStat.equals("disturb")) {
					pr.setMode(Presence.Mode.dnd);
				}
				conn.sendPacket(pr);
			}
			out.write("<data><result>ok</result></data>");
		}
		
		out.flush();
		out.close();
	}
}
