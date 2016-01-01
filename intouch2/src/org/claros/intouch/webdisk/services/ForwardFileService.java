package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.exception.NoPermissionException;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;
import org.claros.intouch.webmail.controllers.IconController;

public class ForwardFileService extends BaseService {
	private static final long serialVersionUID = 7698586538538606204L;

	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		
		try {
			String requestCharset = PropertyFile.getConfiguration("/config/config.xml").getString("request-charset");

			String path = request.getParameter("path");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path = new String(path.getBytes(requestCharset), "utf-8");
			}
			
			EmailPart part = new EmailPart();
			
			if (path.indexOf("../") >= 0) {
				throw new NoPermissionException();
			}
			
			String home = WebdiskController.getUserHome(getAuthProfile(request).getUsername()).getAbsolutePath();
			path = home + path;

			if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
			String fn = path.substring(path.lastIndexOf("/") + 1);
			File fnF = new File(path);
			part.setFilename(fn);
			part.setSize(fnF.length());
			part.setSizeReadable(Utility.sizeToHumanReadable(part.getSize()));
			part.setContentType(IconController.findMimeByName(fn));
			part.setDisposition(path);

			String res = "<li size=\"" + part.getSize() + "\"><img src=\"images/attachment.gif\"/><span>" + part.getFilename() + " (" + part.getSizeReadable() + ")</span> <a href=\"javascript:removeAttach('" + fn + "')\" attid=\"" + 0 + "\" style='color:#5A799E;'>" + getText(request, "remove") + "</a></li>";

			
			List newParts = new ArrayList();
			newParts.add(part);
			request.getSession().setAttribute("attachments", newParts);
			out.print(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
