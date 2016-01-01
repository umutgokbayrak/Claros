package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.mail.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.webdisk.controllers.WebdiskController;

public class UploadFileService extends BaseService {
	private static final long serialVersionUID = 1635352720800214050L;
	private static int MAX_SIZE;

	private static String tmpDir = Constants.tmpDir;

	static {
		int maxSize = 5;
		try {
			maxSize = Integer.parseInt(PropertyFile.getConfiguration("/config/config.xml").getString("webdisk.upload-limit-size"));
		} catch (Exception e) {}
		MAX_SIZE = 1024 * 1024 * maxSize;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
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
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		
		request.setCharacterEncoding("UTF-8");

		ArrayList parts = (ArrayList)request.getSession().getAttribute("attachments");
		if (parts == null) {
			parts = new ArrayList();
		}

		String fileName = null;
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setRepository(new File(tmpDir));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(MAX_SIZE);

			// Parse the request
			List items = upload.parseRequest(request);
			
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();

			    if (!item.isFormField()) {
					//String fieldName = item.getFieldName();
					fileName = item.getName();
					// ie6 bug. it sends whole file path as file name. use firefox!
					if (fileName.indexOf("\\") >=0) {
						fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
					}

					File uploadDir = WebdiskController.getUploadDir(getAuthProfile(request).getUsername());
					File uploadedFile = new File(uploadDir.getAbsolutePath() + "/" + fileName);
				    item.write(uploadedFile);
				    uploadedFile = null;

					if (fileName.indexOf("\\") > 0) {
						fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
					}
					item.delete();
			    }
			}
			response.sendRedirect("../upload_file_ok.jsp?result=0");
		} catch (SizeLimitExceededException e) {
			// attachment exceeded the attachment upload size limit
			response.sendRedirect("../upload_file_ok.jsp?result=1&maxAttSize=" + Utility.sizeToHumanReadable(MAX_SIZE));
		} catch (Exception e) {
			response.sendRedirect("../upload_file_ok.jsp?result=3");
		}
	}
}
