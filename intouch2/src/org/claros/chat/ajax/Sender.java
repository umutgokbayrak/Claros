package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.controllers.TrafficController;
import org.claros.chat.threads.ChatSender;
import org.jivesoftware.smack.XMPPConnection;

public class Sender extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6152899787081101689L;

	/**
	 * Constructor of the object.
	 */
	public Sender() {
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
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String user = request.getParameter("user");
		/*
		String msg = URLDecoder.decode(request.getParameter("msg"), Constants.charset);
		msg = new String(msg.getBytes(Constants.charset), "utf-8");
		*/
		String msg = request.getParameter("msg");
		
		XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
		if (conn != null) {
			String xmppUser = conn.getUser();
			if (xmppUser != null) {
				ChatSender sender = TrafficController.getSender(xmppUser);
				sender.sendMessage(user, msg);
			}
		}
		out.print("ok");
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
