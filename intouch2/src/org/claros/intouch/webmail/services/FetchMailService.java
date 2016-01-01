package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.IconController;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.MailControllerFactory;
import org.claros.intouch.common.utility.Utility;

public class FetchMailService extends BaseService {
	private static Locale loc = new Locale("en", "US");
	private static final long serialVersionUID = 2997611737645527623L;
	
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


		String msgId = request.getParameter("msgId");
		String folder = request.getParameter("folder");

		AuthProfile auth = getAuthProfile(request);
		ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
		ConnectionProfile profile = (ConnectionProfile)request.getSession().getAttribute("profile");

		MailControllerFactory factory = new MailControllerFactory(auth, profile, handler, folder);
		MailController mailCont = factory.getMailController();
		
		
		try {
			Email email = mailCont.getEmailById(new Long(msgId));
			request.getSession().setAttribute("email", email);

			String format = "html";
			int i = -1;
			if (format == null || format.equals("html")) {
				i = findHtmlBody(email.getParts());
			}
			if (i == -1) {
				i = findTextBody(email.getParts());
			}

			String from = Utility.htmlCheck(org.claros.commons.utility.Utility.updateTRChars(email.getBaseHeader().getFromShown()));
			String to = Utility.htmlCheck(org.claros.commons.utility.Utility.updateTRChars(email.getBaseHeader().getToShown()));
			String cc = Utility.htmlCheck(org.claros.commons.utility.Utility.updateTRChars(email.getBaseHeader().getCcShown()));
			String date = Utility.htmlCheck(email.getBaseHeader().getDateShown());
			String subject = Utility.htmlCheck(org.claros.commons.utility.Utility.updateTRChars(email.getBaseHeader().getSubject()));
			Boolean sendReceiptNotification = email.getBaseHeader().getRequestReceiptNotification();
			String sendReceiptNotificationEmail = email.getBaseHeader().getReceiptNotificationEmail();
			String notificationEmail = "";
		
			if(sendReceiptNotification != null && sendReceiptNotification.booleanValue() && sendReceiptNotificationEmail !=null && email.getBaseHeader().getUnread().booleanValue()){
				notificationEmail = org.claros.commons.utility.Utility.convertTRCharsToHtmlSafe(sendReceiptNotificationEmail);
			}
			
			if (profile.getProtocol().equals(Constants.POP3)) {
				mailCont.markAsRead(new Long(msgId));
			}
			
			if (from == null || from.equals("")) {
				from = getText(request, "unknown.sender");
			}
			if (subject == null || subject.equals("")) {
				subject = getText(request, "no.subject");
			}
			
			out.print("<div id='mailViewTitle' notificationEmail='" + notificationEmail + "' class='title' msgid='" + msgId + "' folder='" + folder + "'>" + 
			"<div class='buttons'>" + 
			"<table border='0' cellspacing='0' cellpadding='0'><tr><td nowrap='nowrap'>" + 
			"	<div><a style='text-indent:0px;width:auto;overflow:visible;cursor:pointer;color:#5A799E;' href='javascript:;' onclick='toggleHeader()' id='headerChoose'>" + getText(request, "show.more.headers") + "</a>&nbsp;&nbsp;<img src='images/welcome-border.gif' height='10'/>&nbsp;&nbsp;</div>" +
			"</td><td nowrap='nowrap'>" + 
			"		<a href='javascript:;' class='toggle on' title='" + getText(request, "toggle.list.on") + "'>" + getText(request, "toggle.list.on") + "</a>" + 
			"		<a href='javascript:;' class='toggle off' title='" + getText(request, "toggle.list.off") + "'>" + getText(request, "toggle.list.off") + "</a><br/>" + 
			"</td></tr></table>" + 
			"</div>");
			out.print("<p id='subjectHeader'><span class='subtitle'>" + getText(request, "subject") + ":</span><span>" + org.claros.commons.utility.Utility.convertTRCharsToHtmlSafe(subject) + "</span></p>" + 
			"<p id='fromHeader'><span class='subtitle'>" + getText(request, "from") + ":</span><span>" + org.claros.commons.utility.Utility.convertTRCharsToHtmlSafe(from) + "</span></p>");

			if (date != null && !date.equals("")) {
				out.print("<p style='display:none' id='dateHeader'><span class='subtitle'>" + getText(request, "date") + ":</span><span>" + date + "</span></p>");
			}
			if (to != null && !to.equals("")) {
				out.print("<p style='display:none' id='toHeader'><span class='subtitle'>" + getText(request, "to") + ":</span><span>" + to + "</span></p>");
			}
			if (cc != null && !cc.equals("")) {
				out.print("<p style='display:none' id='ccHeader'><span class='subtitle'>" + getText(request, "cc") + ":</span><span>" + cc + "</span></p>");
			}

			// parts begin
			List parts = email.getParts();
			if (parts != null) {
				if (parts.size() > 1 || i == -1) {
					out.println("<p style='float:left'>");
					EmailPart tmp = null;
					String mime = null;
					for (int j=0;j<parts.size();j++) {
						tmp = (EmailPart)parts.get(j);

						mime = tmp.getContentType();
						mime = mime.toLowerCase(loc).trim();
						if (mime.indexOf(";") > 0) {
							mime = mime.substring(0, mime.indexOf(";"));
						}
						if (mime.indexOf(" ") > 0) {
							mime = mime.substring(0, mime.indexOf(" "));
						}
						String fileName = org.claros.commons.utility.Utility.updateTRChars(tmp.getFilename());
						out.println("<img src='images/attachment-small.gif'/>&nbsp;<a href=\"javascript:;\" onclick=\"Dom.get('msgTextIframe').src = 'webmail/dumpPart.service?dl=false&partid=" + j + "';menuLayers.hide();\" onmouseout=\"menuLayers.hide()\" onmouseover=\"menuLayers.show('attachmenu" + j + "', event)\" style='color:#5A799E;font-weight:bold;'>" + fileName + "</a>&nbsp;&nbsp;");
						out.print("<div id='attachmenu" + j + "' class='menu'>");
						out.print("<img src='images/mime/" + IconController.findIconByMime(tmp.getContentType()) + "' style='float:left;padding-bottom:20px;padding-right:8px;'/>");
						out.print("<strong>" + getText(request, "file.name") + ":</strong>" + fileName + "<br/>");
						out.print("<strong>" + getText(request, "mime.type") + ":</strong>" + mime + "<br/>");
						out.print("<strong>" + getText(request, "size") + ":</strong>" + tmp.getSizeReadable() + "<br/><br/>");
						out.print("<table border='0' cellspacing='1' cellpadding='5'>"  + 
								  "<tr><td>" + 
								  "<table height='23' border='0' cellspacing='0' cellpadding='0' width='50'>" + 
								  "	<tr>" + 
								  "	  <td width='1%'><img src='images/button-left-bg.gif' width='9' height='23'/></td>" + 
								  "	  <td nowrap='nowrap' style='background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer' width='98%' height='23' onclick=\"menuLayers.hide();Dom.get('msgTextIframe').src = 'webmail/dumpPart.service?dl=true&partid=" + j + "';\"'>" + getText(request, "save") + "</td>" + 
								  "	  <td width='1%'><img src='images/button-right-bg.gif' width='9' height='23'/></td>" + 
								  "	</tr>" + 
								  "</table>" + 
								  "</td>" + 
								  "<td>" + 
								  "<table height='23' border='0' cellspacing='0' cellpadding='0' width='50'>" + 
								  "	<tr>" + 
								  "	  <td width='1%'><img src='images/button-left-bg.gif' width='9' height='23'/></td>" + 
								  "	  <td nowrap='nowrap' style='background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer' width='98%' height='23' onclick=\"menuLayers.hide();Dom.get('msgTextIframe').src = 'webmail/dumpPart.service?dl=false&partid=" + j + "';\">" + getText(request, "open") + "</td>" + 
								  "	  <td width='1%'><img src='images/button-right-bg.gif' width='9' height='23'/></td>" + 
								  "	</tr>" + 
								  "</table>" + 
								  "</td>" + 
								  "</tr>" + 
								  "</table>");
						out.print("</div>");
					}
					out.println("</p>");
				}
			}
			// parts end
			
			out.print("</div>" + 
					  "<div id='msgText'>" + 
					  "<iframe id='msgTextIframe' align='center' frameborder='0' height='250' width='100%' style='font-size: 11px;font: arial, sans-serif;' scrolling='auto' src='webmail/dumpPart.service?partid=" + i + "' width='100%' border='0'/>" + 
					  "</div>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
