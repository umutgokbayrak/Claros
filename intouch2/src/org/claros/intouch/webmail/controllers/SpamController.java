package org.claros.intouch.webmail.controllers;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.preferences.controllers.UserPrefsController;
import org.jasen.JasenScanner;
import org.jasen.interfaces.JasenScanResult;

/**
 * @author Umut Gokbayrak
 */
public class SpamController {

	/**
	 * @param auth
	 * @param msg
	 * @return
	 */
	public static boolean isSpam(AuthProfile auth, Message msg) throws Exception {
		System.setProperty("java.awt.headless", "true");
//		String spam = UserPrefsController.getUserSetting(auth, UserPrefConstants.spamAnalysis);
		String spam = null;
		boolean spamAnalysis = true;
		if (spam != null && spam.equals("no")) {
			spamAnalysis = false;
		}

		if (spamAnalysis) {
			if (msg instanceof MimeMessage) {
				if (Constants.spamScanner == null) {
					JasenScanner.getInstance().init();
					Constants.spamScanner = JasenScanner.getInstance();
				}
				/*
				if (!msg.getFolder().isOpen()) {
					msg.getFolder().open(Folder.READ_WRITE);
				}
				*/
//				msg = msg.getFolder().getMessage(msg.getMessageNumber());
				JasenScanResult result = Constants.spamScanner.scan((MimeMessage)msg);
				double probability = result.getProbability();

				msg.setFlag(Flags.Flag.SEEN, false);
				String accepts = UserPrefsController.getUserSetting(auth, "spamUserAccepts");
				accepts = "0.2";
				Double d = null;
				if (accepts == null) {
					d = new Double(0.9);
				} else {
					d = new Double(accepts);
				}
				if (probability >= d.doubleValue()) {
					// it is spam
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
