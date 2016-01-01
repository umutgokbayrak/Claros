package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.contacts.controllers.ImportExportController;

public class ExportAllContactsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234121498656197582L;

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
		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader ("Pragma", "public");
		response.setHeader ("Cache-Control", "must-revalidate");
		response.setDateHeader ("Expires",0); 

		response.setHeader("Content-disposition","attachment; filename=contacts.csv");

		PrintWriter out = response.getWriter();

		AuthProfile auth = (AuthProfile)request.getSession().getAttribute("auth");
		ArrayList result = ImportExportController.exportContacts(auth);
		if (result != null) {
			for (int i=0;i<result.size();i++) {
				out.write((String)result.get(i) + "\n");
			}
		}
	}
}
