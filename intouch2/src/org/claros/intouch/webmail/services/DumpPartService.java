package org.claros.intouch.webmail.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.parser.HTMLMessageParser;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.MailControllerFactory;

import org.htmlcleaner.HtmlCleaner;

public class DumpPartService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3929161932612674112L;

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

		int partId = 0;
		boolean download = false;
		try {
			partId = Integer.parseInt((String)request.getParameter("partid"));
		} catch (Exception e) {}

		try {
			String dl = request.getParameter("dl");
			if (dl != null && dl.equals("true")) {
				download = true;
			}
		} catch (Exception e) {}

		boolean modifyOutput = true;
		try {
			String strModify = request.getParameter("modify");
			if (strModify != null && strModify.equals("false")) {
				modifyOutput = false;
			}
		} catch (Exception e) {}
		
		
		Email email = (Email)request.getSession().getAttribute("email");
		
		try {
			// if mail is not in session try to fetch from parameters
			if (email == null) {
				String msgId = request.getParameter("msgId");
				String folder = URLDecoder.decode(request.getParameter("folder"), "UTF-8");

				AuthProfile auth = getAuthProfile(request);
				ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
				ConnectionProfile profile = (ConnectionProfile)request.getSession().getAttribute("profile");

				MailControllerFactory factory = new MailControllerFactory(auth, profile, handler, folder);
				MailController mailCont = factory.getMailController();
				email = mailCont.getEmailById(new Long(msgId));
			}

			if (partId == -1) {
				String format = "html";
				if (format == null || format.equals("html")) {
					partId = findHtmlBody(email.getParts());
				}
				if (partId == -1) {
					partId = findTextBody(email.getParts());
				}
			}
			
			if (partId != -1) {
				EmailPart part = (EmailPart)email.getParts().get(partId);
				response.setContentType(part.getContentType());
				response.setHeader ("Pragma", "public");
				response.setHeader ("Cache-Control", "must-revalidate");
				response.setDateHeader ("Expires",0); 
				
				String fn = part.getFilename();
				if (fn != null) {
					if (fn.equals("Text Body")) {
						fn = Utility.replaceAllOccurances(email.getBaseHeader().getSubject(), " ", "_") + ".txt";
					} else if (fn.equals("Html Body")) {
						fn = Utility.replaceAllOccurances(email.getBaseHeader().getSubject(), " ", "_") + ".html";
					}
				}
				
				if (download) {
					response.setHeader("Content-disposition","attachment; filename=\"" + fn + "\"");
				} else {
					response.setHeader("Content-disposition","inline; filename=\"" + fn + "\"");
				}
				
				if (part.getContentType().toLowerCase().startsWith("text/plain") || part.isPlainText()) {
					PrintWriter out = response.getWriter();
                	String content = "";
                	Object obj = part.getContent();
                	if(null!=obj) content = obj.toString();
					if (!download) {
						response.setHeader("Content-Type", "text/html");
						HtmlCleaner cleaner = new HtmlCleaner(content);
						cleaner.setOmitXmlDeclaration(true);
						cleaner.setOmitXmlnsAttributes(true);
						cleaner.setUseCdataForScriptAndStyle(false);
						if (modifyOutput) {
							cleaner.clean(true,true);
						} else {
							cleaner.clean(false,true);
						}
						content = cleaner.getXmlAsString();
					} else {
						response.setContentType(part.getContentType());
					}
					out.print(content);
				} else if (part.getContentType().toLowerCase().startsWith("text/html") || part.isHTMLText()) {
					PrintWriter out = response.getWriter();
                	String content = "";
                	Object obj = part.getContent();
                	if(null!=obj) content = obj.toString();
					if (!download) {
						response.setHeader("Content-Type", "text/html");
						HtmlCleaner cleaner = new HtmlCleaner(content);
						cleaner.setOmitXmlDeclaration(true);
						cleaner.setOmitXmlnsAttributes(true);
						cleaner.setUseCdataForScriptAndStyle(false);
						cleaner.clean(false,true);
						content = cleaner.getCompactXmlAsString();
						if (modifyOutput) {
							content = HTMLMessageParser.prepareInlineHTMLContent(email, content);
						}
					} else {
						response.setContentType(part.getContentType());
					}
					out.write(content);
				} else {
					String tmpContType = (part.getContentType() == null) ? "application/octet-stream" : part.getContentType();
					int pos = tmpContType.indexOf(";");
					if (pos >= 0) {
						tmpContType = tmpContType.substring(0, pos);
					}
					response.setContentType(tmpContType);
					
					Object obj = part.getContent();
					if (obj instanceof ByteArrayOutputStream) {
						ServletOutputStream sos = response.getOutputStream();
						ByteArrayOutputStream baos = (ByteArrayOutputStream)obj;
						byte[] b = baos.toByteArray();
						sos.write(b);
						sos.close();
					} else if (obj instanceof String) {
						PrintWriter out = response.getWriter();
						String content = part.getContent().toString();
						out.write(content);
					} else if (obj instanceof ByteArrayInputStream) {
						ServletOutputStream sos = response.getOutputStream();
						ByteArrayInputStream bais = (ByteArrayInputStream)obj;
						int i = -1;
						int len = 0;
						while ((i = bais.read()) != -1) {
							sos.write(i);
							len++;
						}
						sos.close();
						bais.close();
					}
				}
			} else {
				PrintWriter out = response.getWriter();
				out.print("<!-- No body part -->.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * @param list
	 * @return
	 */
	private int findHtmlBody(ArrayList parts) {
		for (int i=0;i<parts.size();i++) {
			EmailPart body = (EmailPart)parts.get(i);
			String cType = body.getContentType();
			if (cType.toLowerCase().startsWith("text/html")) {
				return i;
			}
		}
		return -1;
	}

	private int findTextBody(ArrayList parts) {
		for (int i=0;i<parts.size();i++) {
			EmailPart body = (EmailPart)parts.get(i);
			String cType = body.getContentType();
			if (cType.toLowerCase().startsWith("text/plain")) {
				return i;
			}
		}
		return -1;
	}

}
