package org.claros.intouch.webdisk.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class DownloadService extends BaseService {

	private static final long serialVersionUID = -3552379179879832940L;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();

		try {
			response.setHeader("Expires", "-1");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-control", "no-cache");

			String path1 = request.getParameter("path");
			
			String requestCharset = PropertyFile.getConfiguration("/config/config.xml").getString("request-charset");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path1 = new String(path1.getBytes(requestCharset), "utf-8");
			}
			String username = getAuthProfile(request).getUsername();

			File f = WebdiskController.getUserFile(username, path1); 

			if (requestCharset != null && !requestCharset.trim().equals("")) {
				response.setHeader("Content-disposition","attachment; filename=\"" + new String(f.getName().getBytes("utf-8"), requestCharset) + "\"");
			} else {
				response.setHeader("Content-disposition","attachment; filename=\"" + f.getName() + "\"");
			}
			response.setContentType("application/octet-stream");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));
			int byte_;
			while ((byte_ = is.read()) != -1) {
				baos.write (byte_);
				out.flush();
			}
			is.close();
			baos.close();
			out.write(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
