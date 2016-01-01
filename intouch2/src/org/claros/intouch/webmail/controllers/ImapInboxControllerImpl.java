package org.claros.intouch.webmail.controllers;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.mail.Flags;
import javax.mail.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.protocols.ImapProtocolImpl;
import org.claros.commons.mail.protocols.ProtocolFactory;

/**
 * @author Umut Gokbayrak
 */
public class ImapInboxControllerImpl extends InboxControllerBase implements InboxController {
	private static Log log = LogFactory.getLog(ImapInboxControllerImpl.class);
	
	/**
	 * @param auth
	 * @param profile
	 * @param handler
	 */
	public ImapInboxControllerImpl(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		super(auth, profile, handler);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.InboxController#checkEmail()
	 */
	public ConnectionMetaHandler checkEmail() throws Exception {
		// fetch all messages from the remote pop3 server
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(null);

		if (handler == null || !handler.getStore().isConnected()) {
			handler = protocol.connect(org.claros.commons.mail.utility.Constants.CONNECTION_READ_WRITE);	
		}
		
//		ImapIdleNewMsgThread newMsgThread = new ImapIdleNewMsgThread(protocol.getFolder());
//		newMsgThread.start();

//		ImapIdleModifiedMsgThread modifiedMsgThread = new ImapIdleModifiedMsgThread(protocol.getFolder());
//		modifiedMsgThread.start();

		// for imap fetching the message headers is enough. No need to fetch the whole message.
		// pros: performance gain. cons: spam protection is done only with the message headers.
		ArrayList headers = protocol.fetchAllHeadersAsMessages();
		ArrayList toBeMoved = new ArrayList();
		if (headers != null) {
			Message msg = null;
			String folderId = null;

//			FolderControllerFactory fact = new FolderControllerFactory(auth, profile, handler);
//			FolderController cont = fact.getFolderController();
			for (int i=0;i<headers.size();i++) {
				msg = (Message)headers.get(i);
				
				Flags.Flag flags[] = msg.getFlags().getSystemFlags();
				boolean isSeen = false;
				if (flags != null) {
					Flags.Flag flag = null;
					for (int m=0; m < flags.length; m++) {
						flag = flags[m];
						if (flag.equals(Flags.Flag.SEEN)) {
							isSeen = true;
						}
					}
				}
				
				if (!isSeen) {
					try {
						// find the destionation folderId for the message
						folderId = findDestinationFolderId(msg);
						if (!folderId.equals(org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile))) {
							toBeMoved.add(msg.getMessageNumber() + "_" + folderId);
						}
						/*
						// if message should be directly deleted it shouldn't be
						
						// stored in DB.
//						if (folderId == null) {
//							toBeDeleted.add(new Integer(msg.getMessageNumber()));
						} else if (folderId.toUpperCase().equals(junkFolderName.toUpperCase())) {
							MailControllerFactory mailFact = new MailControllerFactory(auth, profile, handler, Constants.FOLDER_INBOX(profile));
							MailController mailCont = mailFact.getMailController();
							mailCont.moveEmail(new Long(msg.getMessageNumber()), folderId);
						}
						*/
					} catch (Exception e) {
						log.error("Error while processing mail.", e);
					}
				}
			} 
		}

		/*
		// remove the filtered messages from the server.
		if (toBeDeleted.size() > 0) {
			int ids[] = new int[toBeDeleted.size()];
			for (int i=0;i<toBeDeleted.size();i++) {
				Integer id = (Integer)toBeDeleted.get(i);
				ids[i] = id.intValue();
			}
			protocol.deleteMessages(ids);
		}
		*/

		if (toBeMoved.size() > 0) {
			int ids[] = new int[toBeMoved.size()];
			String folders[] = new String[toBeMoved.size()];
			String tmp = null;
			for (int i=0;i<toBeMoved.size();i++) {
				tmp = (String)toBeMoved.get(i);
				StringTokenizer token = new StringTokenizer(tmp, "_");
				ids[i] = Integer.parseInt(token.nextToken());
				folders[i] = token.nextToken();
			}
			protocol.moveEmails(ids, folders);
		}
		return handler;
	}
}
