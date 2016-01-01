package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.Email;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.contacts.controllers.ContactsController;

public class SaveSenderService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 384282706587364201L;

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

		Email email = (Email)request.getSession().getAttribute("email");
		
		try {
			Address adrs[] = email.getBaseHeader().getFrom();
			
			if (adrs != null && adrs[0] != null && adrs[0] instanceof InternetAddress) {
				InternetAddress adr = (InternetAddress)adrs[0];
				AuthProfile auth = getAuthProfile(request);
				ContactsController.saveSenderFromAddr(auth, adr);

				out.print("ok");
			}
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
