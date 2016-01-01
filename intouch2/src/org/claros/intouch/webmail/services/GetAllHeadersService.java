package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.mail.models.Email;
import org.claros.intouch.common.services.BaseService;

public class GetAllHeadersService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531591567232821418L;

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

		Email email = (Email)request.getSession().getAttribute("email");
		
		if (email != null) {
			List headers = email.getHeaders();
			if (headers != null) {
				// name + (char)6 + value
				String tmp = null;
				String name = null;
				String value = null;
				for (int i=0;i<headers.size();i++) {
					try {
						tmp = (String)headers.get(i);
						StringTokenizer tk = new StringTokenizer(tmp, "" + (char)6);
						name = tk.nextToken();
						value = tk.nextToken();
						
						out.print(name + ": " + ((value == null) ? "" : value) + "<br/>");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
