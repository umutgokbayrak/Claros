package org.claros.intouch.webmail.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.EmailPart;
import org.claros.intouch.common.services.BaseService;

public class DeleteAllAttachmentsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1610552503511607594L;

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
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();

		List parts = (List)request.getSession().getAttribute("attachments");
		if (parts != null) {
			deleteAll(parts);
		}
		request.getSession().setAttribute("attachments", null);
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

	/**
	 * 
	 * @param parts
	 */
	public static void deleteAll(List parts) {
		EmailPart tmp = null;
		for (int i=0;i<parts.size();i++) {
			tmp = (EmailPart)parts.get(i);
			File f = new File(tmp.getDisposition());
			f.delete();
		}
	}
}
