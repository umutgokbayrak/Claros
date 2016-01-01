package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.contacts.controllers.ContactsController;

public class SaveCheckContactService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5962637897139123002L;
	private static Log log = LogFactory.getLog(SaveCheckContactService.class);

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

		String emailPrimary = request.getParameter("contEmailPrimary");
		String emailAlternative = request.getParameter("contEmailAlternative");

		AuthProfile auth = getAuthProfile(request);
		
		boolean exists = false;
			
		try {
			exists = ContactsController.searchContactExistsByEmail(auth.getUsername(), emailPrimary);
			if (!exists && emailAlternative != null && !emailAlternative.trim().equals("")) {
				exists = ContactsController.searchContactExistsByEmail(auth.getUsername(), emailAlternative);
			}
		} catch (Exception e) {
			log.warn("error while checking if user exists", e);
		}
		
		if (exists) {
			out.print("yes");
		} else {
			out.print("none");
		}
	}
}
