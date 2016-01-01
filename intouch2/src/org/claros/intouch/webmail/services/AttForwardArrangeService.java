package org.claros.intouch.webmail.services;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.utility.MD5;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;

public class AttForwardArrangeService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6975510930334000147L;
	private static String tmpDir = Constants.tmpDir;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		
		request.getSession().setAttribute("attachments", null);
		Email email = (Email)request.getSession().getAttribute("email");
		String res = "";
		if (email != null) {
			List parts = email.getParts();
			List newParts = new ArrayList();
			if (parts != null) {
				EmailPart part = null;
				DataSource ds = null;
				byte data[] = null;
				for (int i=0;i<parts.size();i++) {
					part = (EmailPart)parts.get(i);
					
					if (part.getShortname().indexOf("Body") >= 0) {
						continue;
					}

					String tmpName = MD5.getHashString(request.getSession().getId() + part.getFilename().toLowerCase());
				    File f = new File(tmpDir + "/" + tmpName);

					ds = part.getDataSource();
					if (ds == null) {
						if (part.getContent() instanceof ByteArrayOutputStream) {
							ByteArrayOutputStream bos = (ByteArrayOutputStream)part.getContent();
							data = bos.toByteArray();
							bos.close();
						} else if (part.getContent() instanceof ByteArrayInputStream) {
							ByteArrayInputStream bis = (ByteArrayInputStream)part.getContent();
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							int j = -1;
							while ((j = bis.read()) != -1) {
								bos.write(j);
							}
							data = bos.toByteArray();
							bos.close();
							bis.close();
						} else if (part.getContent() instanceof String) {
							data = ((String)part.getContent()).getBytes();
						}
					}
				    if (data != null) {
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
						bos.write(data);
						bos.close();
					    
						part.setDisposition(tmpDir + "/" + tmpName);

						// get the file name
						String fn = part.getFilename();
						int pos = fn.lastIndexOf('\\');
						if (pos >= 0) {
							fn = fn.substring(pos + 1);
						}
						pos = fn.lastIndexOf('/');
						if (pos >= 0) {
							fn = fn.substring(pos + 1);
						}
						fn = fn.toLowerCase();
						
						res += "<li size=\"" + part.getSize() + "\"><img src=\"images/attachment.gif\"/><span>" + part.getFilename() + " (" + part.getSizeReadable() + ")</span> <a href=\"javascript:removeAttach('" + fn + "')\" attid=\"" + i + "\" style='color:#5A799E;'>" + getText(request, "remove") + "</a></li>";
				    }
				    
				    newParts.add(part);
				}
			}
			request.getSession().setAttribute("attachments", newParts);
		}
		out.print(res);
	}
}
