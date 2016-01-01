package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.contacts.models.Contact;
import org.claros.intouch.contacts.utility.Utility;
import org.claros.intouch.preferences.controllers.UserPrefsController;


public class AutoCompleteService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3418640902654581164L;

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

		String name = new String(request.getParameter("query").getBytes(Constants.charset), "utf-8");

		try {
			
			String displayType = UserPrefsController.getUserSetting(getAuthProfile(request), "displayType");
			if (displayType == null) {
				displayType = Constants.DISPLAY_TYPE_NAME_FIRST;
			}
			boolean nameFirst = true;
			if (!displayType.equals(Constants.DISPLAY_TYPE_NAME_FIRST)) {
				nameFirst = false;
			}
			
			List contacts = ContactsController.searchContactsByNameEmailNick(getAuthProfile(request).getUsername(), name, true);
			if (contacts != null) {
				String fn = null;
				Contact tmp = null;
				for (int i=0;i<contacts.size();i++) {
					tmp = (Contact)contacts.get(i);
					fn = Utility.getFullName(tmp, nameFirst);
					fn = org.claros.commons.utility.Utility.replaceAllOccurances(fn, ",", "");
					fn = org.claros.commons.utility.Utility.replaceAllOccurances(fn, ";", "");
					if (fn == null || fn.length() == 0) {
						out.println(tmp.getEmailPrimary());
					} else {
						if (tmp.getSex() == null) {
							tmp.setSex("N");
						}
						out.println(fn + " <" + tmp.getEmailPrimary() + ">\t" + tmp.getSex());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
