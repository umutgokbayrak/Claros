package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.controllers.TrafficController;
import org.claros.chat.models.Queue;
import org.claros.chat.threads.ChatListener;
import org.claros.chat.utility.Utility;
import org.jivesoftware.smack.XMPPConnection;

public class Listener extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -800529893679431406L;

	/**
	 * Constructor of the object.
	 */
	public Listener() {
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
			String user = conn.getUser();
			if (user != null) {
				ChatListener listener = TrafficController.getListener(user);
				if (listener != null) {
					String dd = (String)request.getSession().getAttribute("defaultDomain");
					List msgs = listener.getUnreadMessages(dd);

					if (msgs != null && msgs.size() > 0) {
						Queue msg = null;
						String from = null;
						String body = null;
						for (int i=0;i<msgs.size(); i++) {
							msg = (Queue)msgs.get(i);
							from = msg.getMsgFrom();

							String userOnly = from;
							if (userOnly.indexOf("@") > 0) {
								userOnly = from.substring(0, from.indexOf("@"));
							}
							
							body = msg.getMsgBody();
							String myMsg = tidyMsg(body);
							String output = "openChat(null, \"" + from + "\", \"" + Contacts.findNameByUser(request.getSession(), userOnly) + "\", \"" + myMsg + "\", \"in\", \""+ msg.getId() + "\");";
							out.print(output);
						}
					}
				}
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	private String tidyMsg(String msg) {
		msg = Utility.replaceAllOccurances(msg, "\"", "\\\"");
		msg = Utility.replaceAllOccurances(msg, "\n", "<clarosbr><clarosbr>");
		if (msg != null && msg.endsWith("<clarosbr><clarosbr>")) {
				msg += "<clarosbr><clarosbr>";
		}
		return msg;
		/*
		msg = Utility.replaceAllOccurances(msg, ":)", "<img src='img/emotions/regular_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":-)", "<img src='img/emotions/regular_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":-O", "<img src='img/emotions/omg_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":-o", "<img src='img/emotions/omg_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":O", "<img src='img/emotions/omg_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":o", "<img src='img/emotions/omg_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ";-)", "<img src='img/emotions/wink_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ";)", "<img src='img/emotions/wink_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":S", "<img src='img/emotions/confused_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":s", "<img src='img/emotions/confused_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":'(", "<img src='img/emotions/cry_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":D", "<img src='img/emotions/teeth_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":d", "<img src='img/emotions/teeth_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":-P", "<img src='img/emotions/tongue_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":p", "<img src='img/emotions/tongue_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":(", "<img src='img/emotions/sad_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":|", "<img src='img/emotions/what_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":-@", "<img src='img/emotions/angry_smile.gif' />");
		msg = Utility.replaceAllOccurances(msg, ":@", "<img src='img/emotions/angry_smile.gif' />");
		*/
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
