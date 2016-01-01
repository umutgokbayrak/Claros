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
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;

public class DeleteMailFolderService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3541910754785993385L;

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
		String folder = new String(request.getParameter("fid").getBytes(charset), "utf-8");
		*/
		String folder = URLDecoder.decode(request.getParameter("fid"), "UTF-8");

		try {
			FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
			FolderController foldCont = factory.getFolderController();
			foldCont.deleteFolder(folder);

			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
