package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.Address;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.protocols.Smtp;
import org.claros.commons.mail.utility.Utility;
import org.claros.intouch.common.services.BaseService;

public class SendReadReceiptMailService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451365138227115574L;
	private static Log log = LogFactory.getLog(SendReadReceiptMailService.class);

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
		response.setHeader("Content-Type", "text/plain; charset=utf-8");

		PrintWriter out = response.getWriter();

		try {
			String from = URLDecoder.decode(request.getParameter("from"), "UTF-8");
			String to = URLDecoder.decode(request.getParameter("to"), "UTF-8");
			String subject = URLDecoder.decode(request.getParameter("subject"), "UTF-8");
			String date = URLDecoder.decode(request.getParameter("date"), "UTF-8");
			
			Email email = new Email();
			EmailHeader header = new EmailHeader();
			
			Address adrs[] = Utility.stringToAddressArray(from);
			header.setFrom(adrs);
			
			Address tos[] = Utility.stringToAddressArray(to);
			header.setTo(tos);
			
			header.setSubject(getText(request, "read") + ": " + subject);
			header.setDate(new Date());
			
			email.setBaseHeader(header);

			ArrayList parts = new ArrayList();
			EmailPart bodyPart = new EmailPart();
			bodyPart.setContentType("text/html; charset=UTF-8");
			
			String body = getText(request, "read.receipt.email.message");
			body = org.claros.commons.utility.Utility.replaceAllOccurances(body, "#FROM#", org.claros.intouch.common.utility.Utility.htmlCheck(
						org.claros.commons.utility.Utility.updateTRChars(from)));
			body = org.claros.commons.utility.Utility.replaceAllOccurances(body, "#DATE#", date);
			bodyPart.setContent(body);
			parts.add(0, bodyPart);
			email.setParts(parts);
			
			Smtp smtp = new Smtp(getConnectionProfile(request), getAuthProfile(request));

			String proxyIp = request.getHeader("x-forwarded-for");
			if (proxyIp == null || proxyIp.equals("")) {
				proxyIp = request.getRemoteAddr();
			}
			
			HashMap sendRes = smtp.send(email, false, proxyIp + "(" + getAuthProfile(request).getUsername() + ")");
			Address[] sent = (Address[])sendRes.get("sent");
			if (sent == null || sent.length == 0) {
				out.print("fail");
			} else {
				out.print("ok");
			}
		} catch (Exception e) {
			log.error("Error occured while sending read receipt mail : " + e);
			out.print("fail");
		}
	}
}

