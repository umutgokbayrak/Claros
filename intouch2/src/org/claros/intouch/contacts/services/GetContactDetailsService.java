package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Utility;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.contacts.models.Contact;

public class GetContactDetailsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5200489224050326230L;
	private static Log log = LogFactory.getLog(GetContactDetailsService.class);

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

		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");

		PrintWriter out = response.getWriter();
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		
		String id = request.getParameter("id");
		try {
			Contact cont = ContactsController.getContactById(getAuthProfile(request), new Long(id));

			out.print("<emailAlternate>" + getInfo(cont.getEmailAlternate()) + "</emailAlternate>");
			out.print("<emailPrimary>" + getInfo(cont.getEmailPrimary()) + "</emailPrimary>");
			out.print("<firstName>" + getInfo(cont.getFirstName()) + "</firstName>");
			out.print("<gsmNoAlternate>" + getInfo(cont.getGsmNoAlternate()) + "</gsmNoAlternate>");
			out.print("<gsmNoPrimary>" + getInfo(cont.getGsmNoPrimary()) + "</gsmNoPrimary>");
			out.print("<homeAddress>" + getInfo(cont.getHomeAddress()) + "</homeAddress>");
			out.print("<homeCity>" + getInfo(cont.getHomeCity()) + "</homeCity>");
			out.print("<homeCountry>" + getInfo(cont.getHomeCountry()) + "</homeCountry>");
			out.print("<homeFaks>" + getInfo(cont.getHomeFaks()) + "</homeFaks>");
			out.print("<homePhone>" + getInfo(cont.getHomePhone()) + "</homePhone>");
			out.print("<homeProvince>" + getInfo(cont.getHomeProvince()) + "</homeProvince>");
			out.print("<homeZip>" + getInfo(cont.getHomeZip()) + "</homeZip>");
			out.print("<lastName>" + getInfo(cont.getLastName()) + "</lastName>");
			out.print("<middleName>" + getInfo(cont.getMiddleName()) + "</middleName>");
			out.print("<nickName>" + getInfo(cont.getNickName()) + "</nickName>");
			out.print("<personalNote>" + getInfo(cont.getPersonalNote()) + "</personalNote>");
			out.print("<sex>" + getInfo(cont.getSex()) + "</sex>");
			out.print("<spouseName>" + getInfo(cont.getSpouseName()) + "</spouseName>");
			out.print("<title>" + getInfo(cont.getTitle()) + "</title>");
			out.print("<username>" + getInfo(cont.getUsername()) + "</username>");
			out.print("<webPage>" + getInfo(cont.getWebPage()) + "</webPage>");
			out.print("<workAddress>" + getInfo(cont.getWorkAddress()) + "</workAddress>");
			out.print("<workAssistantName>" + getInfo(cont.getWorkAssistantName()) + "</workAssistantName>");
			out.print("<workCity>" + getInfo(cont.getWorkCity()) + "</workCity>");
			out.print("<workCompany>" + getInfo(cont.getWorkCompany()) + "</workCompany>");
			out.print("<workCountry>" + getInfo(cont.getWorkCountry()) + "</workCountry>");
			out.print("<workDepartment>" + getInfo(cont.getWorkDepartment()) + "</workDepartment>");
			out.print("<workFaks>" + getInfo(cont.getWorkFaks()) + "</workFaks>");
			out.print("<workJobTitle>" + getInfo(cont.getWorkJobTitle()) + "</workJobTitle>");
			out.print("<workManagerName>" + getInfo(cont.getWorkManagerName()) + "</workManagerName>");
			out.print("<workOffice>" + getInfo(cont.getWorkOffice()) + "</workOffice>");
			out.print("<workPhone>" + getInfo(cont.getWorkPhone()) + "</workPhone>");
			out.print("<workProfession>" + getInfo(cont.getWorkProfession()) + "</workProfession>");
			out.print("<workProvince>" + getInfo(cont.getWorkProvince()) + "</workProvince>");
			out.print("<workZip>" + getInfo(cont.getWorkZip()) + "</workZip>");
			out.print("<anniversaryDay>" + getInfo(cont.getAnniversaryDay()) + "</anniversaryDay>");
			out.print("<anniversaryMonth>" + getInfo(cont.getAnniversaryMonth()) + "</anniversaryMonth>");
			out.print("<birthDay>" + getInfo(cont.getBirthDay()) + "</birthDay>");
			out.print("<birthMonth>" + getInfo(cont.getBirthMonth()) + "</birthMonth>");
			out.print("<id>" + cont.getId() + "</id>");
		} catch (NumberFormatException e) {
			log.error("not a valid id number", e);
		} catch (Exception e) {
			log.error("error getting info for contact", e);
		}
		out.write("</data>");
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String getInfo(String str) {
		if (str == null) return " ";
		if (str.equals("")) return " ";
		return Utility.htmlCheck(str);
	}
}
