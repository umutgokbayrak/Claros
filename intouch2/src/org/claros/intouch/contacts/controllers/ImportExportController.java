package org.claros.intouch.contacts.controllers;

import java.util.ArrayList;
import java.util.List;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.contacts.models.Contact;

/**
 * @author Umut Gokbayrak
 */
public class ImportExportController {
	
	public static int importContacts(AuthProfile auth, ArrayList columns, ArrayList rows) {
		Contact tmp = null;
		String col = null;
		ArrayList row = null;
		String val = null;
		int success = 0;
		for (int i=0; i<rows.size(); i++) {
			try {
				tmp = new Contact();
				row = (ArrayList)rows.get(i);
				for (int j=0; j<columns.size(); j++) {
					col = (String)columns.get(j);
					try {
						val = (String)row.get(j);
					} catch (Exception m) {
						val = " ";
					}
					
					if (col.equals("FIRST_NAME")) {
						tmp.setFirstName(val);
					} else if (col.equals("MIDDLE_NAME")) {
						tmp.setMiddleName(val);
					} else if (col.equals("LAST_NAME")) {
						tmp.setLastName(val);
					} else if (col.equals("TITLE")) {
						tmp.setTitle(val);
					} else if (col.equals("SEX")) {
						tmp.setSex(val);
					} else if (col.equals("GSM_NO_PRIMARY")) {
						tmp.setGsmNoPrimary(val);
					} else if (col.equals("GSM_NO_ALTERNATE")) {
						tmp.setGsmNoAlternate(val);
					} else if (col.equals("EMAIL_PRIMARY")) {
						tmp.setEmailPrimary(val);
					} else if (col.equals("EMAIL_ALTERNATE")) {
						tmp.setEmailAlternate(val);
					} else if (col.equals("WEB_PAGE")) {
						tmp.setWebPage(val);
					} else if (col.equals("PERSONAL_NOTE")) {
						tmp.setPersonalNote(val);
					} else if (col.equals("SPOUSE_NAME")) {
						tmp.setSpouseName(val);
					} else if (col.equals("NICKNAME")) {
						tmp.setNickName(val);
					} else if (col.equals("BIRTHDAY")) {
						tmp.setBirthDay(val);
					} else if (col.equals("BIRTHMONTH")) {
						tmp.setBirthMonth(val);
					} else if (col.equals("ANNIVERSARYDAY")) {
						tmp.setAnniversaryDay(val);
					} else if (col.equals("ANNIVERSARYMONTH")) {
						tmp.setAnniversaryMonth(val);
					} else if (col.equals("HOME_ADDRESS")) {
						tmp.setHomeAddress(val);
					} else if (col.equals("HOME_CITY")) {
						tmp.setHomeCity(val);
					} else if (col.equals("HOME_PROVINCE")) {
						tmp.setHomeProvince(val);
					} else if (col.equals("HOME_ZIP")) {
						tmp.setHomeZip(val);
					} else if (col.equals("HOME_COUNTRY")) {
						tmp.setHomeCountry(val);
					} else if (col.equals("HOME_PHONE")) {
						tmp.setHomePhone(val);
					} else if (col.equals("HOME_FAKS")) {
						tmp.setHomeFaks(val);
					} else if (col.equals("WORK_COMPANY")) {
						tmp.setWorkCompany(val);
					} else if (col.equals("WORK_JOB_TITLE")) {
						tmp.setWorkJobTitle(val);
					} else if (col.equals("WORK_DEPARTMENT")) {
						tmp.setWorkDepartment(val);
					} else if (col.equals("WORK_OFFICE")) {
						tmp.setWorkOffice(val);
					} else if (col.equals("WORK_PROFESSION")) {
						tmp.setWorkProfession(val);
					} else if (col.equals("WORK_MANAGER_NAME")) {
						tmp.setWorkManagerName(val);
					} else if (col.equals("WORK_ASSISTANT_NAME")) {
						tmp.setWorkAssistantName(val);
					} else if (col.equals("WORK_ADDRESS")) {
						tmp.setWorkAddress(val);
					} else if (col.equals("WORK_CITY")) {
						tmp.setWorkCity(val);
					} else if (col.equals("WORK_PROVINCE")) {
						tmp.setWorkProvince(val);
					} else if (col.equals("WORK_ZIP")) {
						tmp.setWorkZip(val);
					} else if (col.equals("WORK_COUNTRY")) {
						tmp.setWorkCountry(val);
					} else if (col.equals("WORK_PHONE")) {
						tmp.setWorkPhone(val);
					} else if (col.equals("WORK_FAKS")) {
						tmp.setWorkFaks(val);
					}
				}
				tmp.setUsername(auth.getUsername());
				ContactsController.saveContact(auth, tmp);
				success++;
			} catch (Exception e) {
				// do nothing sier
			}
		}
		return success;
	}

	public static ArrayList exportContacts(AuthProfile auth) {
		ArrayList out = new ArrayList();

		out.add("FIRST NAME;MIDDLE NAME;LAST NAME;TITLE;SEX;GSM PRIMARY;GSM ALTERNATE;" +				"EMAIL PRIMARY;EMAIL ALTERNATE;WEB PAGE;PERSONAL NOTE;SPOUSE NAME;NICKNAME;" +				"BIRTHDAY;BIRTHMONTH;ANNIVERSARYDAY;ANNIVERSARYMONTH;HOME ADDRESS;HOME CITY;HOME PROVINCE;HOME ZIP;HOME COUNTRY;" +				"HOME PHONE;HOME FAX;COMPANY;JOB TITLE;DEPARTMENT;OFFICE;PROFESSION;MANAGER NAME;" +				"ASSISTANT NAME;WORK ADDRESS;WORK CITY;WORK PROVINCE;WORK ZIP;WORK COUNTRY;WORK PHONE;WORK FAX");
		try {
			List contacts = ContactsController.getContactsByNamePrefix(auth, "ALL");
			if (contacts != null) {
				Contact tmp = null;
				for (int i=0; i<contacts.size(); i++) {
					String line = "";
					tmp = (Contact)contacts.get(i);
					line += tmp.getFirstName() + ";";
					line += tmp.getMiddleName() + ";";
					line += tmp.getLastName() + ";";
					line += tmp.getTitle() + ";";
					line += tmp.getSex() + ";";
					line += tmp.getGsmNoPrimary() + ";";
					line += tmp.getGsmNoAlternate() + ";";
					line += tmp.getEmailPrimary() + ";";
					line += tmp.getEmailAlternate() + ";";
					line += tmp.getWebPage() + ";";
					line += Utility.replaceAllOccurances(tmp.getPersonalNote(), "\n", " ")  + ";";
					line += tmp.getSpouseName() + ";";
					line += tmp.getNickName() + ";";
					line += getInfo(tmp.getBirthDay()) + ";";
					line += getInfo(tmp.getBirthMonth()) + ";";
					line += getInfo(tmp.getAnniversaryDay()) + ";";
					line += getInfo(tmp.getAnniversaryMonth()) + ";";
					line += Utility.replaceAllOccurances(tmp.getHomeAddress(), "\n", " ")  + ";";
					line += tmp.getHomeCity() + ";";
					line += tmp.getHomeProvince() + ";";
					line += tmp.getHomeZip() + ";";
					line += tmp.getHomeCountry() + ";";
					line += tmp.getHomePhone() + ";";
					line += tmp.getHomeFaks() + ";";
					line += tmp.getWorkCompany() + ";";
					line += tmp.getWorkJobTitle() + ";";
					line += tmp.getWorkDepartment() + ";";
					line += tmp.getWorkOffice() + ";";
					line += tmp.getWorkProfession() + ";";
					line += tmp.getWorkManagerName() + ";";
					line += tmp.getWorkAssistantName() + ";";
					line += Utility.replaceAllOccurances(tmp.getWorkAddress(), "\n", " ")  + ";";
					line += tmp.getWorkCity() + ";";
					line += tmp.getWorkProvince() + ";";
					line += tmp.getWorkZip() + ";";
					line += tmp.getWorkCountry() + ";";
					line += tmp.getWorkPhone() + ";";
					line += tmp.getWorkFaks() + ";";
					out.add(line);
				}
			}
		} catch (Exception e) {
			out.add("An Error Occured.");
		}
		return out;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String getInfo(String str) {
		if (str == null) return " ";
		if (str.equals("")) return " ";
		return str;
	}
}

