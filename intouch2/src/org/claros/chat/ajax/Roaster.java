package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.utility.Utility;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class Roaster extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926350754674872925L;
	private static final Locale loc = new Locale("en", "US"); 

	/**
	 * Constructor of the object.
	 */
	public Roaster() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");

		String act = request.getParameter("act");
		if (act.equals("save")) {
			String buddy = request.getParameter("newBuddy").toLowerCase(loc);
			buddy = convertToJabber(buddy);
			
			Presence pr = new Presence(Presence.Type.subscribe);
			pr.setTo(buddy);
			conn.sendPacket(pr);
		} else if (act.equals("remove")) {
			String buddy = request.getParameter("removeBuddy");
			RosterEntry en = null;
			for (Iterator i=conn.getRoster().getEntries().iterator(); i.hasNext(); ) {
				en = (RosterEntry)i.next();
				if (en.getUser().equals(buddy)) {
					try {
						conn.getRoster().removeEntry(en);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		out.println("ok");
	}
	
	/**
	 * 
	 * @param buddy
	 * @return
	 */
	private static String convertToJabber(String buddy) {
		if (buddy.indexOf("hotmail.com") > 0) {
			int atPos = buddy.indexOf("@");
			buddy = buddy.substring(0, atPos) + "%" + buddy.substring(atPos + 1) + "@" + Utility.msnTransport;
		} else if (buddy.indexOf("icq.com") > 0) {
			int atPos = buddy.indexOf("@");
			buddy = buddy.substring(0, atPos) + "%" + buddy.substring(atPos + 1) + "@" + Utility.icqTransport;
		} else if (buddy.indexOf("aol.com") > 0) {
			int atPos = buddy.indexOf("@");
			buddy = buddy.substring(0, atPos) + "%" + buddy.substring(atPos + 1) + "@" + Utility.aolTransport;
		} else if (buddy.indexOf("yahoo.com") > 0) {
			int atPos = buddy.indexOf("@");
			buddy = buddy.substring(0, atPos) + "%" + buddy.substring(atPos + 1) + "@" + Utility.yahooTransport;
		}
		return buddy;
	}
}
