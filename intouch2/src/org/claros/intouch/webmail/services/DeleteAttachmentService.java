package org.claros.intouch.webmail.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.EmailPart;
import org.claros.intouch.common.services.BaseService;

public class DeleteAttachmentService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436101802848885384L;

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

		List parts = (List)request.getSession().getAttribute("attachments");
		List newLst = new ArrayList();
		if (parts != null) {
			EmailPart tmp = null;
			for (int i=0;i<parts.size();i++) {
				tmp = (EmailPart)parts.get(i);
				if (tmp.getFilename().equals(request.getParameter("f"))) {
					File f = new File(tmp.getDisposition());
					f.delete();
				} else {
					newLst.add(tmp);
				}
			}
		}
		request.getSession().setAttribute("attachments", newLst);
		out.print("ok");
	
	}

}
