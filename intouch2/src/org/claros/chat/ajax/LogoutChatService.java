package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.profiling.services.LogoutService;

public class LogoutChatService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4898393288833060843L;

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
		
		LogoutService.logoutChat(request.getSession(false));
		out.print("ok");
	}

}
