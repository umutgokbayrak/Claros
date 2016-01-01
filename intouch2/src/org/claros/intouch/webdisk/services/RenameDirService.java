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

public class RenameDirService extends BaseService {
	private static final long serialVersionUID = 7116921849130450239L;

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

			String from = request.getParameter("from");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				from = new String(from.getBytes(requestCharset), "utf-8");
			}
			
			String dir = request.getParameter("dir");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				dir = new String(dir.getBytes(requestCharset), "utf-8");
			}

			String username = getAuthProfile(request).getUsername();

			String home = WebdiskController.correctPath(WebdiskController.getUserHome(username).getAbsolutePath());

			if (from == null || from.equals("undefined")) {
				out.print("ok");
			} else {
				if (dir.startsWith("/")) {
					dir = dir.substring(1);
				}
				dir = WebdiskController.correctPath(dir);
				from = WebdiskController.correctPath(from);
				
				String parentDir = from.substring(0, from.lastIndexOf("/"));
				
				String myDir = home + parentDir + "/" + dir;
				myDir = WebdiskController.correctPath(myDir);
				File to = new File(myDir);
				
				File fromDir = new File(home + from);
				
				FileUtils.copyDirectory(fromDir, to);
				FileUtils.deleteDirectory(fromDir);
				out.print("ok");
			}
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
