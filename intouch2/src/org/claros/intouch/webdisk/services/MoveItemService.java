package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class MoveItemService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -552540293565310867L;

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

			String moveTo = WebdiskController.correctPath(request.getParameter("moveto"));

			if (requestCharset != null && !requestCharset.trim().equals("")) {
				moveTo = new String(moveTo.getBytes(requestCharset), "utf-8");
			}
			
			String path = WebdiskController.correctPath(request.getParameter("path"));
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path = new String(path.getBytes(requestCharset), "utf-8");
			}
			
			String username = getAuthProfile(request).getUsername();

			File from = WebdiskController.getUserFile(username, path); 

			String home = WebdiskController.correctPath(WebdiskController.getUserHome(username).getAbsolutePath());
			File dest = new File(WebdiskController.correctPath(home + moveTo));
			
			if (from.getAbsolutePath().equals(dest.getAbsolutePath())) {
				// do nothing
			} else if (from.getAbsolutePath().equals(WebdiskController.getUploadDir(username).getAbsolutePath())) {
				// do nothing
			} else {
				if (from.isFile()) {
					// moving file to another directory
					FileUtils.copyFileToDirectory(from, dest);
					from.delete();
				} else if (from.isDirectory()) {
					FileUtils.copyDirectoryToDirectory(from, dest);
					FileUtils.deleteDirectory(from);
				}
			}
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
