package org.claros.intouch.webmail.services;

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
import org.claros.commons.mail.exception.MailSizeExceededException;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.MD5;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;

public class UploadAttachmentService extends BaseService {
	private static final long serialVersionUID = 1406344913366076583L;
	private static int MAX_MEM_SIZE = 1024 * 1024;
	private static int MAX_ATT_SIZE = 1024 * 1024 * Constants.maxAttSize;
	private static int MAX_MAIL_SIZE = 1024 * 1024 * Constants.maxMailSize;

	private static String tmpDir = Constants.tmpDir;

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

		String id = null;

		String fileName = null;
		String strSize = null;
		long totalSize = 0;
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setSizeThreshold(MAX_MEM_SIZE);
			factory.setRepository(new File(tmpDir));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(MAX_ATT_SIZE);

			// Parse the request
			List items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator iter = items.iterator();
			
			EmailPart part = null;
			// we need the id any way so we parse it first
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();

			    if (item.isFormField()) {
			    	String name = item.getFieldName();
			    	if (name != null && name.equals("iframeid")) {
			    		id = item.getString();
			    	}
			    }
			}
			
			// we can parse the attachments now
			iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();

			    if (!item.isFormField()) {
					//String fieldName = item.getFieldName();
					fileName = item.getName();
					String contentType = item.getContentType();
					// boolean isInMemory = item.isInMemory();
					long size = item.getSize();

					String tmpName = MD5.getHashString(request.getSession().getId() + fileName);

				    File uploadedFile = new File(tmpDir + "/" + tmpName);
				    item.write(uploadedFile);
				    uploadedFile = null;

					if (fileName.indexOf("\\") > 0) {
						fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
					}

				    part = new EmailPart();
					/*
					byte data[] = item.get();
					

					MimeBodyPart bodyPart = new MimeBodyPart();
					DataSource ds = new ByteArrayDataSource(data, contentType, fileName);
					bodyPart.setDataHandler(new DataHandler(ds));
					bodyPart.setDisposition("attachment; filename=\"" + fileName + "\"");
					bodyPart.setFileName(fileName);

					part.setDataSource(ds);

					part.setContent(bodyPart.getContent());
					*/
					part.setContentType(contentType);
					part.setDisposition(tmpDir + "/" + tmpName);

					// part.setDisposition(bodyPart.getDisposition());
					part.setFilename(fileName);
					part.setSize(size);
					part.setSizeReadable(Utility.sizeToHumanReadable(size));

					strSize = part.getSizeReadable();
					
					parts.add(part);
					item.delete();
					request.getSession().setAttribute("attachments", parts);
					
			    }
			}
			totalSize = calculateSize(parts);
			
			// check if the total mail size exceeds the limit
			if (totalSize > MAX_MAIL_SIZE) {
				parts.remove(parts.size() - 1);
				throw new MailSizeExceededException();
			}

			response.sendRedirect("../upload_ok.jsp?result=0&size=" + strSize + "&fileName=" + java.net.URLEncoder.encode(java.net.URLEncoder.encode(fileName,"UTF-8"),"UTF-8") + "&id=" + id + "&totalSize=" + Utility.sizeToHumanReadable(totalSize));
		} catch (SizeLimitExceededException e) {
			// attachment exceeded the attachment upload size limit
			response.sendRedirect("../upload_ok.jsp?result=1&fileName=" + java.net.URLEncoder.encode(fileName,"UTF-8") + "&size=" + strSize + "&maxAttSize=" + Utility.sizeToHumanReadable(MAX_ATT_SIZE));
		} catch (MailSizeExceededException e) {
			// mail exceeded the total mail size limit
			totalSize = calculateSize(parts);
			response.sendRedirect("../upload_ok.jsp?result=2&fileName=" + java.net.URLEncoder.encode(fileName,"UTF-8") + "&totalSize=" + Utility.sizeToHumanReadable(totalSize) + "&maxMailSize=" + Utility.sizeToHumanReadable(MAX_MAIL_SIZE));
		} catch (Exception e) {
			response.sendRedirect("../upload_ok.jsp?result=3");
		}
	}

	private long calculateSize(ArrayList parts) {
		EmailPart tmp = null;
		long allSize = 0;
		for (int i=0;i<parts.size();i++) {
			tmp = (EmailPart)parts.get(i);
			allSize = allSize + tmp.getSize();
		}
		return allSize;
	}

}
