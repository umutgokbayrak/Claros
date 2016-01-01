package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.controllers.QueueController;

public class MarkAsReadService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1655612292561309429L;

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

		try {
			String user = (String)request.getSession().getAttribute("user");
			String sId = (String)request.getParameter("id");

			String dd = (String)request.getSession().getAttribute("defaultDomain");
			QueueController.setDelivered(user, new Long(sId), dd);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
