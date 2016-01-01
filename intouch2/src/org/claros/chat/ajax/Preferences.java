package org.claros.chat.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.chat.controllers.PreferencesController;
import org.claros.chat.models.Preference;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.packet.VCard;

public class Preferences extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7576677600733897150L;

	/**
	 * Constructor of the object.
	 */
	public Preferences() {
		super();
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
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		String user = (String)request.getSession().getAttribute("user");
		String act = (String)request.getParameter("act");
		if (user != null) {
			if (act.equals("load")) {
				try {
					List prefs = PreferencesController.getPreferencesByUser(user);
					if (prefs != null) {
						out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
						out.write("<data>");
						
						XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
						VCard vCard = new VCard();
						vCard.load(conn);
						String nickname = vCard.getNickName();
						String firstName = vCard.getFirstName();
						String middleName = vCard.getMiddleName();
						String lastName = vCard.getLastName();
						String fullName = vCard.getField("FN");
						
						if (fullName == null) {
							if (firstName != null && lastName != null) {
								if (middleName != null) {
									fullName = firstName + " " + middleName + " " + lastName;
								} else {
									fullName = firstName + " " + middleName + " " + lastName;
								}
							} else if (nickname != null) {
								fullName = nickname;
							} else {
								fullName = user;
							}
						}
						out.write("<fullname>" + fullName + "</fullname>");
						
						Preference p = null;
						for (int i=0;i<prefs.size();i++) {
							p = (Preference)prefs.get(i);
							out.write("<preference>");
							out.write("<key>" + p.getPrefKey() + "</key>");
							out.write("<value>" + p.getPrefValue() + "</value>");
							out.write("</preference>");
						}
						out.write("</data>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (act.equals("save")) {
				try {
					String fullName = new String(request.getParameter("fullName").getBytes("iso-8859-9"), "utf-8");
					XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
					VCard vcard = new VCard();
					vcard.load(conn);
					vcard.setField("FN", fullName);
					vcard.save(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String awayTimeout = request.getParameter("awayTimeout");
					PreferencesController.savePreference(new Preference(user, "awayTimeout", awayTimeout));
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String animations = request.getParameter("animations");
					PreferencesController.savePreference(new Preference(user, "animations", animations));
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String soundAlert = request.getParameter("soundAlert");
					PreferencesController.savePreference(new Preference(user, "soundAlert", soundAlert));
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.print("<result>ok</result>");
			}

		}
		out.flush();
		out.close();
	}
}
