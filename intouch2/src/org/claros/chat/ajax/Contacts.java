package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.claros.chat.models.Contact;
import org.claros.chat.utility.RosterComparator;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;

public class Contacts extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3626097157006365935L;

	/**
	 * Constructor of the object.
	 */
	public Contacts() {
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

		// get the printwriter and prepare the html
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		
		XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");

		if (conn != null) {
			Roster roster = conn.getRoster();
			roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
			ArrayList contacts = new ArrayList();
			RosterEntry en = null;
			Contact cn = null;
			for (Iterator i=roster.getEntries().iterator(); i.hasNext(); ) {
				try {
					en = (RosterEntry)i.next();
					cn = new Contact();
					cn.setName(en.getName());
					
					if (en.getUser().indexOf("msn") == 0) {
						System.out.println("ok");
					}
					
					if (cn.getName() == null || cn.getName().equals("")) {
						cn.setName(en.getUser());
						if (cn.getName().indexOf("@") > 0) {
							cn.setName(cn.getName().substring(0, cn.getName().indexOf("@")));
						}
					}
					
					if (cn.getName().indexOf("%") > 0) {
						cn.setName(cn.getName().substring(0, cn.getName().indexOf("%")));
					}
					
					RosterPacket.ItemStatus st = en.getStatus();
					if (st != null && st.equals(RosterPacket.ItemStatus.SUBSCRIPTION_PENDING)) {
						cn.setStatus("pending");
					}
					
					Presence pr = roster.getPresence(en.getUser());
					if (pr != null) {
						Presence.Type tp = pr.getType();
						if (tp.equals(Presence.Type.available)) {
							cn.setPresence("available");
							
							Presence.Mode md = pr.getMode();
							if (md != null) {
								if (md.equals(Presence.Mode.available)) {
									cn.setPresence("available");
								} else if (md.equals(Presence.Mode.away)) {
									cn.setPresence("away");
								} else if (md.equals(Presence.Mode.chat)) {
									cn.setPresence("chat");
								} else if (md.equals(Presence.Mode.dnd)) {
									cn.setPresence("disturb");
								} else if (md.equals(Presence.Mode.xa)) {
									cn.setPresence("extended_away");
								}
								if (pr.getStatus() != null && !pr.getStatus().equals("")) {
									cn.setMessage(pr.getStatus());
								}
							}
						}
					}
					if (cn.getPresence() == null) {
						cn.setPresence("offline");
					}
					cn.setUser(en.getUser());
					contacts.add(cn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Collections.sort(contacts, new RosterComparator());
			request.getSession().setAttribute("contacts", contacts);

			// all contacts are set, now generate the output
			Contact tmp = null;
			out.print("<!-- contacts -->");
			for (int i=0; i<contacts.size(); i++) {
				try {
					tmp = (Contact)contacts.get(i);
					if (tmp.getPresence().equals("offline")) {
						out.println("<div id=\"contact\" onclick=\"openChat(this);\" ondblclick=\"openChat(this);\" onmouseover=\"showInfoWin(this);\" onmouseout=\"unhoverContact(this);\" " +
								"user=\"" + tmp.getUser() + "\" username=\"" + tmp.getName() + "\" presence=\"" + tmp.getPresence() + "\" msg=\"" + tmp.getMessage() + "\" style=\"display:none;\">");
					} else {
						out.println("<div id=\"contact\" onclick=\"openChat(this);\" ondblclick=\"openChat(this);\" onmouseover=\"showInfoWin(this);\" onmouseout=\"unhoverContact(this);\" " +
								"user=\"" + tmp.getUser() + "\" username=\"" + tmp.getName() + "\" presence=\"" + tmp.getPresence() + "\" msg=\"" + tmp.getMessage() + "\">");
					}
						
					out.println("<table width=\"134\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"20\">");
					out.println("  <tr>");
					String color = "gray";
					if (tmp.getPresence().equals("available")) {
						color = "green";
					} else if (tmp.getPresence().equals("away")) {
						color = "orange";
					} else if (tmp.getPresence().equals("chat")) {
						color = "green";
					} else if (tmp.getPresence().equals("disturb")) {
						color = "red";
					} else if (tmp.getPresence().equals("extended_away")) {
						color = "red";
					} else if (tmp.getPresence().equals("invisible")) {
						color = "orange";
					}
					out.println("	<td width=\"10\"><img src=\"images/chat/indicators/" + color + ".gif\" id=\"indicator\"/></td>");
					out.println("	<td width=\"124\">");
					out.println("		<div id=\"contactname\">");
					out.println("			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"124\" height=\"20\">");
					out.println("				<tr><td nowrap=\"nowrap\"><a href=\"#\">" + tmp.getName() + "</a></td>");
					out.println("				</tr>");
					out.println("			</table>");
					out.println("		</div>");
					out.println("	</td>");
					out.println("  </tr>");
					out.println("</table>");
					out.println("</div>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			out.print("<br /><br /><br /><div align=\"center\"><img src=\"images/chat/loading.gif\" width=\"32\" height=\"32\">");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public static String findNameByUser(HttpSession sess, String user) {
		ArrayList contacts = (ArrayList)sess.getAttribute("contacts");
		if (contacts != null) {
			Contact tmp = null;
			String adr = null;
			for (int i=0; i<contacts.size(); i++) {
				tmp = (Contact)contacts.get(i);
				adr = tmp.getUser().substring(0);
				if (adr.indexOf("@") > 0) {
					adr = adr.substring(0, adr.indexOf("@"));
				}
				if (adr.equals(user)) {
					if (tmp.getName() != null) {
						return tmp.getName();
					}
				}
			}
		}
		return user;
	}

}
