package org.claros.intouch.preferences.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.preferences.controllers.UserPrefsController;

public class LoadPreferencesService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 69436044316786827L;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");

		PrintWriter out = response.getWriter();
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");

		AuthProfile auth = getAuthProfile(request);
		
		try {
			HashMap prefs = UserPrefsController.getUserPreferences(auth);
			if (prefs != null) {
				String fullName = (String)prefs.get("fullName");
				if (fullName == null) fullName = "";
				String emailAddress = (String)prefs.get("emailAddress");
				if (emailAddress == null) emailAddress = "";
				String replyTo = (String)prefs.get("replyTo");
				if (replyTo == null) replyTo = "";
				String mailSound = (String)prefs.get("mailSound");
				if (mailSound == null) mailSound = "yes";
				String spamAnalysis = (String)prefs.get("spamAnalysis");
				if (spamAnalysis == null) spamAnalysis = "yes";
				String saveSent = (String)prefs.get("saveSent");
				if (saveSent == null) saveSent = "yes";
				String signature = (String)prefs.get("signature");
				if (signature == null) signature = "";
				String signaturePos = (String)prefs.get("signaturePos");
				if (signaturePos == null) signaturePos = "top";
				String sendReadReceipt = (String)prefs.get("sendReadReceipt");
				if (sendReadReceipt == null) sendReadReceipt = "prompt";
				String safeContacts = (String)prefs.get("safeContacts");
				if (safeContacts == null) safeContacts = "yes";
				String saveSentContacts = (String)prefs.get("saveSentContacts");
				if (saveSentContacts == null) saveSentContacts = "yes";
				String displayType = (String)prefs.get("displayType");
				if (displayType == null) displayType = "nameFirst";
				String chatAwayMins = (String)prefs.get("chatAwayMins");
				if (chatAwayMins == null) chatAwayMins = "15";
				String chatSound = (String)prefs.get("chatSound");
				if (chatSound == null) chatSound = "yes";
				String newsUrl = (String)prefs.get("newsUrl");
				if (newsUrl == null) newsUrl = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.rss-feed");

				out.write("<fullName> " + fullName + "</fullName>");
				out.write("<emailAddress> " + emailAddress + "</emailAddress>");
				out.write("<replyTo> " + replyTo + "</replyTo>");
				out.write("<mailSound> " + mailSound + "</mailSound>");
				out.write("<spamAnalysis> " + spamAnalysis + "</spamAnalysis>");
				out.write("<saveSent> " + saveSent + "</saveSent>");
				out.write("<signature> " + Utility.htmlSpecialChars(signature) + "</signature>");
				out.write("<signaturePos> " + signaturePos + "</signaturePos>");
				out.write("<signaturePos> " + signaturePos + "</signaturePos>");
				out.write("<sendReadReceipt> " + sendReadReceipt + "</sendReadReceipt>");
				out.write("<saveSentContacts> " + saveSentContacts + "</saveSentContacts>");
				out.write("<displayType> " + displayType + "</displayType>");
				out.write("<chatAwayMins> " + chatAwayMins + "</chatAwayMins>");
				out.write("<chatSound> " + chatSound + "</chatSound>");
				out.write("<newsUrl> " + newsUrl + "</newsUrl>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.write("</data>");
	}
}
