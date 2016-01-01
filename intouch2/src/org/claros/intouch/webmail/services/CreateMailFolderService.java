package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.models.FolderDbObject;

public class CreateMailFolderService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391489259828194439L;

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

		AuthProfile auth = getAuthProfile(request);
		ConnectionMetaHandler handler = (ConnectionMetaHandler)request.getSession().getAttribute("handler");
		ConnectionProfile profile = (ConnectionProfile)request.getSession().getAttribute("profile");

		/*
		String charset = Constants.charset;
		String folderName = new String(request.getParameter("folder").getBytes(charset), "utf-8");
		*/
		String folderName = URLDecoder.decode(request.getParameter("folder"), "UTF-8");
		
		try {
			// character corrections. This is important for turkish users. 
			folderName = Utility.replaceAllOccurances(folderName.trim(), ".", "_");
			folderName = Utility.replaceAllOccurances(folderName, "\u0131", "i");
			folderName = Utility.replaceAllOccurances(folderName, "\u0130", "I");
			folderName = Utility.replaceAllOccurances(folderName, "\u015E", "S");
			folderName = Utility.replaceAllOccurances(folderName, "\u015F", "s");
			folderName = Utility.replaceAllOccurances(folderName, "\u00E7", "c");
			folderName = Utility.replaceAllOccurances(folderName, "\u00C7", "C");
			folderName = Utility.replaceAllOccurances(folderName, "\u00FC", "u");
			folderName = Utility.replaceAllOccurances(folderName, "\u00DC", "U");
			folderName = Utility.replaceAllOccurances(folderName, "\u00F6", "o");
			folderName = Utility.replaceAllOccurances(folderName, "\u00D6", "O");
			folderName = Utility.replaceAllOccurances(folderName, "\u011F", "g");
			folderName = Utility.replaceAllOccurances(folderName, "\u011E", "G");
			folderName = Utility.replaceAllOccurances(folderName, "\"", "_");

			FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
			FolderController foldCont = factory.getFolderController();
			
			FolderDbObject folder = new FolderDbObject();
			folder.setFolderType(Constants.FOLDER_TYPE_CUSTOM);
			folder.setFolderName(folderName);
			folder.setUsername(auth.getUsername());
			folder.setParentId(new Long(0));
			
			foldCont.createFolder(folder);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	
	}
}
