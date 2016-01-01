package org.claros.intouch.webmail.controllers;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;

/**
 * @author Umut Gokbayrak
 */
public class DbInboxControllerImpl extends InboxControllerBase implements InboxController {
//	private static Log log = LogFactory.getLog(DbInboxControllerImpl.class);
//	private static Locale loc = new Locale("en", "US");
	/**
	 * @param profile
	 * @param auth
	 * @param handler
	 */
	public DbInboxControllerImpl(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		super(auth, profile, handler);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.InboxController#checkEmail()
	 */
	public ConnectionMetaHandler checkEmail() throws Exception {
		//ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		//Protocol protocol = factory.getProtocol(null);
		try {
			// fetch all messages from the remote pop3 server
			// protocol.disconnect();
			// handler = protocol.connect(org.claros.commons.mail.utility.Constants.CONNECTION_READ_WRITE);
			/*
			ArrayList headers = protocol.fetchAllHeaders();
			ArrayList toBeDeleted = new ArrayList();
			if (headers != null) {
				EmailHeader header = null;
				for (int i=0;i<headers.size();i++) {
					header = (EmailHeader)headers.get(i);
					int msgId = header.getMessageId();
					try {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						ObjectOutputStream os = new ObjectOutputStream(bos);
						os.writeObject(header);
						byte bHeader[] = bos.toByteArray();
						String md5Header = MD5.getHashString(bHeader);

						MailControllerFactory mailFact = new MailControllerFactory(auth, profile, handler, null);
						MailController mailCont = mailFact.getMailController();
						DbMailControllerImpl dbMailCont = (DbMailControllerImpl)mailCont;
						if (!dbMailCont.mailAlreadyFetched(md5Header)) {
							Message msg = protocol.getMessage(msgId);
							if (!msg.getFolder().isOpen()) {
								msg.getFolder().open(Folder.READ_ONLY);
							}
					
							// find the destionation folderId for the message
							String folderId = findDestinationFolderId(msg);
					
							// if message should be directly deleted it shouldn't be 
							// stored in DB.
							if (folderId != null) {
								// create a byte array from the message content. 
								bos = new ByteArrayOutputStream();
								msg.writeTo(bos);
								byte bMsg[] = bos.toByteArray();
					
								// serialize the message byte array
								os = new ObjectOutputStream(bos);
								os.writeObject(bMsg);

								// create an email db item
								MsgDbOject item = new MsgDbOject();
								item.setEmail(bos.toByteArray());
								item.setUniqueId(md5Header);
								item.setFolderId(new Long(folderId));
								item.setUnread(new Boolean(true));
								item.setUsername(auth.getUsername());
								item.setMsgSize(new Long(bMsg.length));

								// save the email db item.
								mailCont.appendEmail(item);
							}
							toBeDeleted.add(new Integer(msgId));
						}
					} catch (Exception e) {
						toBeDeleted.add(new Integer(msgId));
						log.error("Error while processing mail.", e);
					}
				}
			}
		
			// fetched messages are deleted if the user requested so.
//			String deleteFetched = UserPrefsController.getUserSetting(auth, UserPrefConstants.deleteFetched);
			String deleteFetched = "no";
			if (deleteFetched != null && deleteFetched.equals("yes")) {
				if (toBeDeleted.size() > 0) {
					int ids[] = new int[toBeDeleted.size()];
					for (int i=0;i<toBeDeleted.size();i++) {
						Integer id = (Integer)toBeDeleted.get(i);
						ids[i] = id.intValue();
					}
					protocol.deleteMessages(ids);
				}
			}
			*/
		} finally {
//			protocol.disconnect();
		}
		return handler;
	}

}
