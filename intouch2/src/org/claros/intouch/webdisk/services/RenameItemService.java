package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class RenameItemService extends BaseService {
	private static final long serialVersionUID = 7863734723651766816L;

	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			String requestCharset = PropertyFile.getConfiguration("/config/config.xml").getString("request-charset");

			String newName = request.getParameter("name");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				newName = new String(newName.getBytes(requestCharset), "utf-8");
			}

			String path = WebdiskController.correctPath(request.getParameter("path"));
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path = new String(path.getBytes(requestCharset), "utf-8");
			}

			String username = getAuthProfile(request).getUsername();

			File f = WebdiskController.getUserFile(username, path); 

			if (newName.indexOf("..") >= 0) {
				out.print("fail");
			}
//			newName = Utility.convertTRCharsToENChars(newName);
			
			String home = WebdiskController.correctPath(WebdiskController.getUserHome(username).getAbsolutePath());
			
			File fNew = new File(home + "/" + path.substring(0, path.lastIndexOf("/")) + "/" + newName);

			f.renameTo(fNew);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
