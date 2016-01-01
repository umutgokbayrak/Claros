package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.contacts.controllers.ContactsController;

public class DeleteContactService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2623444584059352821L;

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
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		String sId = request.getParameter("id");
		
		try {
			ContactsController.deleteContact(getAuthProfile(request), new Long(sId));
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
