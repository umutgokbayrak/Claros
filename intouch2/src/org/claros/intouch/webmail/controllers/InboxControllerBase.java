package org.claros.intouch.webmail.controllers;

import javax.mail.Message;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.models.FolderDbObject;

/**
 * @author Umut Gokbayrak
 */
public class InboxControllerBase {
	protected ConnectionProfile profile;
	protected AuthProfile auth;
	protected ConnectionMetaHandler handler = null;
	
	/**
	 * 
	 * @param profile
	 * @param auth
	 * @param handler
	 */
	public InboxControllerBase(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		this.profile = profile;
		this.auth = auth;
		this.handler = handler;
	}

	/**
	 * @param auth
	 * @param msg
	 * @return
	 */
	public String findDestinationFolderId(Message msg) throws Exception {
		String strSpamCheck = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.spam-check-enabled");
		boolean spamEnabled = false;
		try {
			spamEnabled = Boolean.valueOf(strSpamCheck).booleanValue();
		} catch (Exception e) {}
		
		boolean isSpam = false;
		if (spamEnabled) {
			isSpam = SpamController.isSpam(auth, msg);
		}
		FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
		FolderController fc = factory.getFolderController();
		String folderId = null;
		FolderDbObject folder = null;

		if (isSpam) {
			folder = fc.getJunkFolder();
			if (profile.getProtocol().equals(org.claros.commons.mail.utility.Constants.POP3)) {
				folderId = folder.getId().toString();
			} else {
				folderId = folder.getFolderName();
			}
		} else {
			folder = fc.getInboxFolder();
			if (profile.getProtocol().equals(org.claros.commons.mail.utility.Constants.POP3)) {
				folderId = folder.getId().toString();
			} else {
				folderId = folder.getFolderName();
			}
		}
		return folderId;
		
		/*
		// search if any user specified filter matches the content
		List filters = FilterController.getFilters(auth);
		if (filters != null) {
			Filter filter = null;
			for (int i=0; i<filters.size(); i++) {
				filter = (Filter)filters.get(i);
				String fPort = filter.getPortion();
				String mPort = getPortion(msg, fPort).toUpperCase();
				String fCond = filter.getCondition();
				String fKeyWord = filter.getKeyword().toUpperCase();
				if (fCond.equals(Constants.CONDITION_CONTAINS)) {
					if (mPort.indexOf(fKeyWord) >= 0) {
						if (filter.getAction().equals(Constants.ACTION_DELETE)) {
							return null;
						} else {
							return filter.getDestination();
						}
					}
				} else if (fCond.equals(Constants.CONDITION_EQUALS)) {
					if (mPort.equals(fKeyWord)) {
						if (filter.getAction().equals(Constants.ACTION_DELETE)) {
							return null;
						} else {
							return filter.getDestination();
						}
					}
				} if (fCond.equals(Constants.CONDITION_NOT_CONTAINS)) {
					if (mPort.indexOf(fKeyWord) < 0) {
						if (filter.getAction().equals(Constants.ACTION_DELETE)) {
							return null;
						} else {
							return filter.getDestination();
						}
					}
				}
			}
		}

		// if at the preferences page, user clicked not to perform a spam 
		// check on the contacts in address book.
		String safe = UserPrefsController.getUserSetting(auth, UserPrefConstants.safeContacts);
		if (safe == null) {
			safe = "no";
		}
		boolean isSafe = (safe.equals("yes") ? true : false);
		boolean contactIsSafe = false;
		if (isSafe) {
			// check to see if the user exists in the address book
			Address[] adrs = msg.getFrom();
			if (adrs != null && adrs.length == 1) {
				Address adr = adrs[0];
				if (adr instanceof InternetAddress) {
					InternetAddress iAdr = (InternetAddress)adr;
					Contact tmp = ContactsController.searchContactByEmail(auth.getUsername(), iAdr.getAddress());
					if (tmp != null) {
						contactIsSafe = true;
					}
				}
			}
		}

		FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
		FolderController fc = factory.getFolderController();

		String folderId = null;
		FolderDbItem folder = null;
		if (!contactIsSafe) {
			// none of the user specified filters matched. Do spam filtering.
			// if enabled do spam analysis and probability is higher than
			// user accepts, move it to junk mail folder.
			boolean isSpam = false;
			try {
				isSpam = SpamController.isSpam(auth, msg);
			} catch (Exception e) {
				// if spam controller can no parse the message it is 
				// probable that it is an illegal format. Treat as spam.
				isSpam = true;
			}
			if (isSpam) {
				folder = fc.getJunkFolder();
				if (profile.getProtocol().equals(org.claros.commons.mail.utility.Constants.POP3)) {
					folderId = folder.getId().toString();
				} else {
					folderId = folder.getFolderName();
				}
			} else {
				folder = fc.getInboxFolder();
				if (profile.getProtocol().equals(org.claros.commons.mail.utility.Constants.POP3)) {
					folderId = folder.getId().toString();
				} else {
					folderId = folder.getFolderName();
				}
			}
		} else {
			// if the contact is safe deliver mail to INBOX
			folder = fc.getInboxFolder();
			if (profile.getProtocol().equals(org.claros.commons.mail.utility.Constants.POP3)) {
				folderId = folder.getId().toString();
			} else {
				folderId = org.claros.commons.mail.utility.Constants.FOLDER_INBOX;
			}
		}
		return folderId;
		*/
	}

	/**
	 * @param msg
	 * @param fPort
	 * @return
	 */
	/*
	private String getPortion(Message msg, String fPort) throws MessagingException {
		if (fPort.equals(Constants.PORTION_SUBJECT)) {
			return msg.getSubject();
		} else if (fPort.equals(Constants.PORTION_FROM)) {
			return Utility.addressArrToString(msg.getFrom());
		} else if (fPort.equals(Constants.PORTION_TO)) {
			return Utility.addressArrToString(msg.getRecipients(Message.RecipientType.TO));
		} else if (fPort.equals(Constants.PORTION_CC)) {
			return Utility.addressArrToString(msg.getRecipients(Message.RecipientType.CC));
		} else if (fPort.equals(Constants.PORTION_MESSAGE_BODY)) {
			Email email = MessageParser.parseMessage(msg);
			return email.getBodyText();
		}
		return "";
	}
	*/
}
