package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.utility.Constants;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.models.FolderDbObject;
import org.claros.intouch.webmail.models.FolderDbObjectWrapper;

public class GetFoldersService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7348820385681559438L;

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
		ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
		ConnectionProfile profile = getConnectionProfile(request);
		
		String sFolder = (String)getVariable(request, "folder");
		if (sFolder == null || sFolder.equals("")) {
			sFolder = org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile);
		}

		try {
			FolderControllerFactory foldFact = new FolderControllerFactory(getAuthProfile(request), profile, handler);
			FolderController folderCont = foldFact.getFolderController();
			if (profile.getProtocol().equals(Constants.POP3)) {
				if (sFolder == null || sFolder.equals("INBOX")) {
					FolderDbObject foldObj = folderCont.getInboxFolder();
					sFolder = foldObj.getId().toString();
				}
			}
			
			List folders = folderCont.getFolders();
			out.print("<ul>");
			if (folders != null) {
				FolderDbObjectWrapper tmp = null;
				String folderName = null;
				String folderNameEnc = null;
				String folderNameLang = null;
				for (int i=0;i<folders.size();i++) {
					tmp = (FolderDbObjectWrapper)folders.get(i);
					folderName = folderNameLang = tmp.getFolderName();
					folderNameEnc = java.net.URLEncoder.encode(folderName, "utf-8");
					if(tmp.getFolderType().intValue() < 6) {
						folderNameLang = getText(request, folderName.replace(' ', '.'));
					}
					if (folderNameLang == null || folderNameLang.length() == 0) {
						folderNameLang = folderName;
					}
					
					folderNameLang = org.claros.intouch.common.utility.Utility.htmlCheck(folderNameLang);
					if (profile.getProtocol().equals(Constants.IMAP)) {
						out.print("<li " + ((folderName.equals(sFolder)) ? "class=\"active\"" : "") + " folderType=\"" + tmp.getFolderType() + "\" folderId=\"" + folderNameEnc  + "\" folderName=\"" + folderNameLang + "\" id=\"mailFolder" + folderNameEnc + "\" onclick=\"selectMailFolder('" + folderNameEnc + "')\"><div>"); 
					} else {
						out.print("<li " + ((tmp.getId().toString().equals(sFolder)) ? "class=\"active\"" : "") + " folderType=\"" + tmp.getFolderType() + "\" folderId=\"" + tmp.getId()  + "\" folderName=\"" + folderNameLang + "\" id=\"mailFolder" + tmp.getId() + "\" onclick=\"selectMailFolder('" + tmp.getId() + "')\"><div>");
					}
					if (tmp.getUnreadItemCount().intValue() > 0) {
						out.print("<em><i class='left'>&nbsp;</i><b id=\"folderUnreadCount\">" + tmp.getUnreadItemCount() + "</b><i class=\"right\">&nbsp;</i></em>");
					}
					
					String tmpF = folderNameLang;
					if (folderNameLang.length() > 15) {
						tmpF = folderNameLang.substring(0, 13) + "...";
					}
					out.print("<span><img alt=\"\" src=\"images/blue-folder.gif\"/></span>" + tmpF);
					out.print("</div></li>");
				}
			}
			out.print("</ul>");
		} catch (Exception e) {
			out.print("");
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

}
