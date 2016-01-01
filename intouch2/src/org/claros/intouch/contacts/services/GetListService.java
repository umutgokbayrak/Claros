package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.contacts.controllers.GroupsController;
import org.claros.intouch.contacts.models.Contact;
import org.claros.intouch.contacts.models.ContactGroup;
import org.claros.intouch.contacts.utility.Utility;
import org.claros.intouch.preferences.controllers.UserPrefsController;

public class GetListService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9052097028337237490L;
	private static Log log = LogFactory.getLog(GetListService.class);

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
		// String charset = Constants.charset;

		String prefix = request.getParameter("prefix");
		if (prefix == null || prefix.equals("")) {
			prefix = "ALL";
		} else {
			/*
			prefix = new String(prefix.getBytes(charset), "utf-8");
			prefix = prefix.toUpperCase(loc);
			*/
			prefix = prefix.toUpperCase();
		}
		AuthProfile auth = getAuthProfile(request);
		
		try {
			List contacts = ContactsController.getContactsByNamePrefix(auth, prefix);
			List groups = GroupsController.getGroupsByUser(auth);

			String displayType = UserPrefsController.getUserSetting(auth, "displayType");
			if (displayType == null) {
				displayType = Constants.DISPLAY_TYPE_NAME_FIRST;
			}

			boolean nameFirst = true;
			if (!displayType.equals(Constants.DISPLAY_TYPE_NAME_FIRST)) {
				nameFirst = false;
			}
			
			if (contacts != null) {
				Contact tmp = null;
				String img = null;
				String fullName = null;
				String email = null;
				for (int i=0; i<contacts.size(); i++) {
					try {
						tmp = (Contact)contacts.get(i);
						if (tmp.getSex() == null || tmp.getSex().equals("")) {
							img = "unknown";
						} else if (tmp.getSex().equals("M")) {
							img = "male";
						} else if (tmp.getSex().equals("F")) {
							img = "female";
						}
						fullName = Utility.getFullName(tmp, nameFirst);
						if(0 == fullName.length()) fullName = "&nbsp;";
						email = tmp.getEmailPrimary();
						if(0 == email.length()) email = "&nbsp;";
						
						out.print("<tr onclick='showContactDetails(" + tmp.getId() + ")' id='contact" + tmp.getId() + "'>" + 
								"<td><img src='images/contact-" + img + "-mini.png'></td>" + 
								"<td>" + fullName + "</td>" + 
								"<td>" + email + "</td>" + 
							"</tr>");
					} catch (Exception e) {
						log.error("error while parsing contact", e);
					}
				}
			}
			
			if (groups != null) {
				ContactGroup tmp = null;
				for (int i=0;i<groups.size();i++) {
					tmp = (ContactGroup)groups.get(i);

					out.print("<tr onclick='showGroupDetails(" + tmp.getId() + ")'>" + 
							"<td><img src='images/contact-group-mini.png'></td>" + 
							"<td>" + tmp.getShortName() + "</td>" + 
							"<td>&nbsp;</td>" + 
						"</tr>");
				}
			}
		} catch (Exception e) {
			log.error("error while fetching contacts/groups", e);
		}
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
