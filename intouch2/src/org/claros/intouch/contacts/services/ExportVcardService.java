package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.contacts.models.Contact;
import org.claros.intouch.contacts.utility.Utility;

public class ExportVcardService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4818273833984599431L;

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
		response.setHeader("Content-Type", "text/x-vcard; charset=" + Constants.charset);

		String sId = request.getParameter("id");
		PrintWriter out = response.getWriter();
	
		try {
			Contact contact = ContactsController.getContactById(getAuthProfile(request), new Long(sId));
			
			String fullName = Utility.getFullName(contact, true);
			String fullNameHeader = fullName.replace(' ', '_');
			response.setHeader("Content-disposition","attachment; filename=" + fullNameHeader + ".vcf");
			
			out.println("BEGIN:VCARD");
			out.println("VERSION:3.0");
			out.println("FN:" + fullName);

			String middleName = contact.getMiddleName();
			if (middleName != null && !middleName.trim().equals("")) {
				middleName = " " + middleName;
			} else {
				middleName = "";
			}
			
			out.println("N:" + contact.getFirstName() + middleName + ";" + contact.getLastName());
			out.println("ORG:" + getInfo(contact.getWorkCompany()));
			out.println("ADR;TYPE=WORK,POSTAL,PARCEL:;;" + getInfo(contact.getWorkAddress()) + ";" + getInfo(contact.getWorkCity()) + ";" + getInfo(contact.getWorkProvince()) + ";" + getInfo(contact.getWorkZip()) + ";" + getInfo(contact.getWorkCountry()));
			out.println("ADR;TYPE=HOME,POSTAL,PARCEL:;;" + getInfo(contact.getHomeAddress()) + ";" + getInfo(contact.getHomeCity()) + ";" + getInfo(contact.getHomeProvince()) + ";" + getInfo(contact.getHomeZip()) + ";" + getInfo(contact.getHomeCountry()));
			out.println("TEL;TYPE=VOICE,MSG,WORK:" + getInfo(contact.getWorkPhone()));
			out.println("TEL;TYPE=VOICE,MSG,HOME:" + getInfo(contact.getHomePhone()));
			out.println("TEL;TYPE=FAX,WORK:" + getInfo(contact.getWorkFaks()));
			out.println("TEL;TYPE=FAX,HOME:" + getInfo(contact.getHomeFaks()));
			out.println("EMAIL;TYPE=INTERNET,PREF:" + getInfo(contact.getEmailPrimary()));
			out.println("EMAIL;TYPE=INTERNET:" + getInfo(contact.getEmailAlternate()));
			out.println("URL:" + contact.getWebPage());
			out.println("END:VCARD");
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		/*
		BEGIN:VCARD
		VERSION:3.0
		FN:Frank Dawson
		N:Frank;Dawson
		ORG:Lotus Development Corporation
		ADR;TYPE=WORK,POSTAL,PARCEL:;;6544 Battleford Drive
		 ;Raleigh;NC;27613-3502;U.S.A.
		TEL;TYPE=VOICE,MSG,WORK:+1-919-676-9515
		TEL;TYPE=FAX,WORK:+1-919-676-9564
		EMAIL;TYPE=INTERNET,PREF:Frank_Dawson@Lotus.com
		EMAIL;TYPE=INTERNET:fdawson@earthlink.net
		URL:http://home.earthlink.net/~fdawson
		END:VCARD
		 */
	}

	private static String getInfo(String str) {
		if (str == null) return " ";
		if (str.equals("")) return " ";
		return str;
	}

}
