package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.configuration.PropertyFile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class DeleteItemService extends BaseService {
	private static final long serialVersionUID = -7181255224935275339L;

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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			String requestCharset = PropertyFile.getConfiguration("/config/config.xml").getString("request-charset");

			String path = request.getParameter("path");
			if (requestCharset != null && !requestCharset.trim().equals("")) {
				path = new String(path.getBytes(requestCharset), "utf-8");
			}
			String username = getAuthProfile(request).getUsername();

			File f = WebdiskController.getUserFile(username, path); 
			
			File uploadDir = WebdiskController.getUploadDir(username);
			if (uploadDir.getAbsolutePath().equals(f.getAbsolutePath())) {
				out.print("ok");
			} else {
				if (deleteItem(f)) {
					out.print("ok");
				} else {
					out.print("fail");
				}
			}
		} catch (Exception e) {
			out.print("fail");
		}
	}

	/**
     * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
	 * 
	 * @param dir
	 * @return
	 */
    public static boolean deleteItem(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteItem(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
