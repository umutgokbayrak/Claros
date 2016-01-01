package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.Address;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.Email;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;

public class GetHeaderInfoService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 808344999094917540L;

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
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		Email email = (Email)request.getSession().getAttribute("email");
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.write("<from>" + Utility.htmlSpecialChars(email.getBaseHeader().getFromShown()) + "</from>");
		out.write("<subject>" + Utility.htmlSpecialChars(email.getBaseHeader().getSubject()) + "</subject>");
		out.write("<date>" + email.getBaseHeader().getDateShown() + "</date>");
		
		Address replyTo[] = email.getBaseHeader().getReplyTo();
		if (replyTo != null && replyTo.length > 0) {
			try {
				out.write("<replyTo>" + Utility.htmlSpecialChars(org.claros.commons.mail.utility.Utility.addressArrToString(replyTo)) + "</replyTo>");
			} catch (Exception e) {
				out.write("<replyTo> </replyTo>");
			}
		} else {
			out.write("<replyTo> </replyTo>");
		}
		
		String to = email.getBaseHeader().getFromShown();
		if (email.getBaseHeader().getCc() != null && !email.getBaseHeader().getCc().equals("")) {
			to += ", " + email.getBaseHeader().getCcShown();
		}
		if (email.getBaseHeader().getToShown() != null && !email.getBaseHeader().getToShown().equals("")) {
			to += ", " + email.getBaseHeader().getToShown();
		}
		out.write("<recipients>" + Utility.htmlSpecialChars(to) + "</recipients>");
		
		out.write("</data>");
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
