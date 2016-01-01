package org.claros.intouch.webmail.controllers;

import javax.mail.Message;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.parser.MessageParser;
import org.claros.commons.mail.protocols.ImapProtocolImpl;
import org.claros.commons.mail.protocols.Protocol;
import org.claros.commons.mail.protocols.ProtocolFactory;
import org.claros.intouch.webmail.models.MsgDbObject;

/**
 * @author Umut Gokbayrak
 */
public class ImapMailControllerImpl implements MailController {
//	private static Log log = LogFactory.getLog(DbMailController.class);
	private AuthProfile auth;
	private ConnectionProfile profile;
	private ConnectionMetaHandler handler;
	private String folder;
	
	@SuppressWarnings("unused")
	private ImapMailControllerImpl() {
		super();
	}
	
	public ImapMailControllerImpl(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler, String folder) {
		this.auth = auth;
		this.profile = profile;
		this.handler = handler;
		this.folder = folder; 
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#getEmailById(java.lang.Long)
	 */
	public Email getEmailById(Long emailId) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getProtocol(folder);
		Message msg = protocol.getMessage(emailId.intValue());
		/*
		if (!msg.getFolder().isOpen()) {
			msg.getFolder().open(Constants.CONNECTION_READ_WRITE);
		}
		*/
		Email email = MessageParser.parseMessage(msg);
//		msg.getFolder().close(true);
		email.setMsgId(emailId);
		return email;
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#deleteEmail(java.lang.Long)
	 */
	public void deleteEmail(Long emailId) throws Exception {		
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		Protocol protocol = factory.getProtocol(folder);
		protocol.deleteMessages(new int[] {emailId.intValue()});
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#moveEmail(java.lang.Long, java.lang.String)
	 */
	public void moveEmail(Long msgId, String destFolder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.moveEmail(msgId, destFolder);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#appendEmail(org.claros.groupware.webmail.models.EmailDbItem)
	 */
	public void appendEmail(MsgDbObject item) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.appendEmail(item.getEmail());
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#markAsRead(java.lang.Long)
	 */
	public void markAsRead(Long msgId) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.markAsRead(msgId);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.MailController#deleteEmails(int[])
	 */
	public void deleteEmails(int[] msgs) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.deleteMessages(msgs);
	}

	/**
	 * 
	 */
	public void moveEmails(int[] msgs, String destFolder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.moveEmails(msgs, destFolder);
	}

	/**
	 * 
	 */
	public void moveEmails(int[] msgs, String destFolders[]) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.moveEmails(msgs, destFolders);
	}

	/**
	 * 
	 */
	public void markAsDeleted(int[] ids) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.flagAsDeleted(ids);
	}
}
