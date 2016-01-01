package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.claros.commons.configuration.PropertyFile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class CreateDirService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436273005995690425L;

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

			String parent = request.getParameter("parent");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				parent = new String(parent.getBytes(requestCharset), "utf-8");
			}
			String dir = request.getParameter("dir");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				dir = new String(dir.getBytes(requestCharset), "utf-8");
			}
			
			String username = getAuthProfile(request).getUsername();

			String home = WebdiskController.correctPath(WebdiskController.getUserHome(username).getAbsolutePath());

			if (parent == null || parent.equals("undefined")) {
				parent = "";
			}
			
			if (dir.startsWith("/")) {
				dir = dir.substring(1);
			}
			dir = WebdiskController.correctPath(dir);
			String myDir = home + parent + "/" + dir;
			myDir = WebdiskController.correctPath(myDir);
			
			File f = new File(myDir);
			FileUtils.forceMkdir(f);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}

		
	}

}
