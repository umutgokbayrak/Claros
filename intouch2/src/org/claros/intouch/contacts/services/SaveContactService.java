package org.claros.intouch.contacts.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.contacts.models.Contact;

public class SaveContactService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5047398836515996933L;
	private static Log log = LogFactory.getLog(GetContactDetailsService.class);
	private static String charset = Constants.charset;

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
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
			boolean doConversion = false;
			String id = getValue(request, "contId", doConversion);
			
			String firstName = getValue(request, "contFirstName", doConversion);
			String middleName = getValue(request, "contMiddleName", doConversion);
			String lastName = getValue(request, "contLastName", doConversion);
			String title = getValue(request, "contTitle", doConversion);
			String sex = getValue(request, "contSex", doConversion);
			String emailPrimary = getValue(request, "contEmailPrimary", doConversion);
			String emailAlternative = getValue(request, "contEmailAlternative", doConversion);
			String gsmNoPrimary = getValue(request, "contGsmNoPrimary", doConversion);
			String gsmNoAlternative = getValue(request, "contGsmNoAlternative", doConversion);
			String webPage = getValue(request, "contWebPage", doConversion);
			String nickName = getValue(request, "contNickName", doConversion);
			String spouseName = getValue(request, "contSpouseName", doConversion);
			String personalNote = getValue(request, "contPersonalNote", doConversion);
			String homeAddress = getValue(request, "contHomeAddress", doConversion);
			String homeCity = getValue(request, "contHomeCity", doConversion);
			String homeProvince = getValue(request, "contHomeProvince", doConversion);
			String homeZip = getValue(request, "contHomeZip", doConversion);
			String homeCountry = getValue(request, "contHomeCountry", doConversion);
			String homePhone = getValue(request, "contHomePhone", doConversion);
			String homeFaks = getValue(request, "contHomeFaks", doConversion);
			String workCompany = getValue(request, "contWorkCompany", doConversion);
			String workJobTitle = getValue(request, "contWorkJobTitle", doConversion);
			String workOffice = getValue(request, "contWorkOffice", doConversion);
			String workDepartment = getValue(request, "contWorkDepartment", doConversion);
			String workProfession = getValue(request, "contWorkProfession", doConversion);
			String workManagerName = getValue(request, "contWorkManagerName", doConversion);
			String workAssistantName = getValue(request, "contWorkAssistantName", doConversion);
			String workAddress = getValue(request, "contWorkAddress", doConversion);
			String workCity = getValue(request, "contWorkCity", doConversion);
			String workProvince = getValue(request, "contWorkProvince", doConversion);
			String workZip = getValue(request, "contWorkZip", doConversion);
			String workCountry = getValue(request, "contWorkCountry", doConversion);
			String workPhone = getValue(request, "contWorkPhone", doConversion);
			String workFaks = getValue(request, "contWorkFaks", doConversion);
			String birthDay = getValue(request, "contBirthDay", doConversion);
			String birthMonth = getValue(request, "contBirthMonth", doConversion);
			String anniversaryDay = getValue(request, "contAnniversaryDay", doConversion);
			String anniversaryMonth = getValue(request, "contAnniversaryMonth", doConversion);
			
			if (firstName == null || firstName.trim().equals("")) {
				firstName = emailPrimary.substring(0, emailPrimary.indexOf("@"));
			}
			
			Contact contact = new Contact();
			
			if (id != null && id.trim().length() > 0) {
				// it is an update
				try {
					contact.setId(new Long(Long.parseLong(id)));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
			contact.setBirthDay(birthDay);
			contact.setBirthMonth(birthMonth);
			contact.setAnniversaryDay(anniversaryDay);
			contact.setAnniversaryMonth(anniversaryMonth);
			contact.setEmailAlternate(emailAlternative);
			contact.setEmailPrimary(emailPrimary);
			contact.setFirstName(firstName);
			contact.setGsmNoAlternate(gsmNoAlternative);
			contact.setGsmNoPrimary(gsmNoPrimary);
			contact.setHomeAddress(homeAddress);
			contact.setHomeCity(homeCity);
			contact.setHomeCountry(homeCountry);
			contact.setHomeFaks(homeFaks);
			contact.setHomePhone(homePhone);
			contact.setHomeProvince(homeProvince);
			contact.setHomeZip(homeZip);
			contact.setLastName(lastName);
			contact.setMiddleName(middleName);
			contact.setNickName(nickName);
			contact.setPersonalNote(personalNote);
			contact.setSex(sex);
			contact.setSpouseName(spouseName);
			contact.setTitle(title);
			contact.setUsername(getAuthProfile(request).getUsername());
			contact.setWebPage(webPage);
			contact.setWorkAddress(workAddress);
			contact.setWorkAssistantName(workAssistantName);
			contact.setWorkCity(workCity);
			contact.setWorkCompany(workCompany);
			contact.setWorkCountry(workCountry);
			contact.setWorkDepartment(workDepartment);
			contact.setWorkFaks(workFaks);
			contact.setWorkJobTitle(workJobTitle);
			contact.setWorkManagerName(workManagerName);
			contact.setWorkOffice(workOffice);
			contact.setWorkPhone(workPhone);
			contact.setWorkProfession(workProfession);
			contact.setWorkProvince(workProvince);
			contact.setWorkZip(workZip);
			
			try {
				ContactsController.saveContact(getAuthProfile(request), contact);
				out.print("ok");
			} catch (Exception e) {
				log.error("error while saving contact", e);
				out.print("fail");
			}
		} catch (Exception e) {
			log.error("error while saving contact. (getting params)", e);
			out.print("fail");
		}
	}

	/**
	 * 
	 * @param request
	 * @param param
	 * @param doConversion
	 * @return
	 * @throws Exception
	 */
	private static String getValue(HttpServletRequest request, String param, boolean doConversion) throws Exception {
		if (doConversion) {
			return new String(request.getParameter(param).getBytes(charset), "utf-8");		
		} else {
			return request.getParameter(param);
		}
	}
}
