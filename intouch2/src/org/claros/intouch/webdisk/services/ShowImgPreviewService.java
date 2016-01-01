package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.configuration.Paths;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.exception.NoPermissionException;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.ImageController;
import org.claros.intouch.webdisk.controllers.WebdiskController;
import org.claros.intouch.webmail.controllers.IconController;

public class ShowImgPreviewService extends BaseService {

	private static final long serialVersionUID = -6154464522984604466L;

	/**
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

			String requestCharset = PropertyFile.getConfiguration("/config/config.xml").getString("request-charset");

			String size = request.getParameter("size");
			String path = request.getParameter("path");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path = new String(path.getBytes(requestCharset), "utf-8");
			}

			String username = getAuthProfile(request).getUsername();

			File f = WebdiskController.getUserFile(username, path);
			String mime = IconController.findMimeByName(f.getName());
			if (mime == null || !mime.startsWith("image/")) {
				throw new NoPermissionException();
			}
			
			byte b[] = null;
			if (f.length() > 102400) {
				response.setContentType(mime);
				b = ImageController.getImgBytes(Paths.getResFolder() + "/download-big.gif");
			} else {
				if (size != null && size.equals("normal")) {
					response.setContentType(mime);
					b = ImageController.getImgBytes(f.getAbsolutePath());
				} else {
					response.setContentType("image/jpeg");
					int width = Integer.parseInt(request.getParameter("width"));
					int height = Integer.parseInt(request.getParameter("height"));
					b = ImageController.getCustomImgBytes(f.getAbsolutePath(), width, height);
				}
			}
			out.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
