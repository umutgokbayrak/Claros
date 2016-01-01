package org.claros.intouch.preferences.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.PropertyFile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.preferences.controllers.UserPrefsController;

public class SavePreferencesService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758680271955404513L;

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
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		// String charset = Constants.charset;

		// String fullName = new String(request.getParameter("fullName").getBytes(charset), "utf-8");
		String fullName = request.getParameter("fullName");
		String emailAddress = request.getParameter("emailAddress");
		String replyTo = request.getParameter("replyTo");
		String mailSound = request.getParameter("mailSound");
		String spamAnalysis = request.getParameter("spamAnalysis");
		String saveSent = request.getParameter("saveSent");
		// String signature = new String(request.getParameter("signature").getBytes(charset), "utf-8");
		String signature = request.getParameter("signature");
		String signaturePos = request.getParameter("signaturePos");
		String sendReadReceipt = request.getParameter("sendReadReceipt");
		String safeContacts = request.getParameter("safeContacts");
		String saveSentContacts = request.getParameter("saveSentContacts");
		String displayType = request.getParameter("displayType");
		String chatAwayMins = request.getParameter("chatAwayMins");
		String chatSound = request.getParameter("chatSound");
		String newsUrl = request.getParameter("newsUrl");
		
		if (mailSound == null || mailSound.trim().equals("")) {
			mailSound = "yes";
		}
		if (spamAnalysis == null || spamAnalysis.trim().equals("")) {
			spamAnalysis = "-1";
		}
		if (saveSent == null || saveSent.trim().equals("")) {
			saveSent = "yes";
		}
		if (signaturePos == null || signaturePos.trim().equals("")) {
			signaturePos = "top";
		}
		if (sendReadReceipt == null || sendReadReceipt.trim().equals("")) {
			sendReadReceipt = "prompt";
		}
		if (safeContacts == null || safeContacts.trim().equals("")) {
			safeContacts = "yes";
		}
		if (saveSentContacts == null || saveSentContacts.trim().equals("")) {
			saveSentContacts = "yes";
		}
		if (displayType == null || displayType.trim().equals("")) {
			displayType = "nameFirst";
		}
		if (chatAwayMins == null || chatAwayMins.trim().equals("")) {
			chatAwayMins = "15";
		} else {
			try {
				// check if it is a valid integer number
				chatAwayMins = Integer.parseInt(chatAwayMins) + "";
			} catch (Exception e) {
				chatAwayMins = "15";
			}
		}
		if (chatSound == null || chatSound.trim().equals("")) {
			chatSound = "yes";
		}
		if (newsUrl == null || newsUrl.trim().equals("")) {
			try {
				newsUrl = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.rss-feed");
			} catch (ConfigurationException e) {}
		}
		
		AuthProfile auth = getAuthProfile(request);
		try {
			UserPrefsController.saveItem(auth, "fullName", fullName);
			UserPrefsController.saveItem(auth, "emailAddress", emailAddress);
			UserPrefsController.saveItem(auth, "replyTo", replyTo);
			UserPrefsController.saveItem(auth, "mailSound", mailSound);
			UserPrefsController.saveItem(auth, "spamAnalysis", spamAnalysis);
			UserPrefsController.saveItem(auth, "saveSent", saveSent);
			UserPrefsController.saveItem(auth, "signature", signature);
			UserPrefsController.saveItem(auth, "signaturePos", signaturePos);
			UserPrefsController.saveItem(auth, "sendReadReceipt", sendReadReceipt);
			UserPrefsController.saveItem(auth, "safeContacts", safeContacts);
			UserPrefsController.saveItem(auth, "saveSentContacts", saveSentContacts);
			UserPrefsController.saveItem(auth, "displayType", displayType);
			UserPrefsController.saveItem(auth, "chatAwayMins", chatAwayMins);
			UserPrefsController.saveItem(auth, "chatSound", chatSound);
			UserPrefsController.saveItem(auth, "newsUrl", newsUrl);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}
}
