package org.claros.commons.mail.protocols;

import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.exception.ConnectionException;
import org.claros.commons.mail.exception.MailboxActionException;
import org.claros.commons.mail.exception.ProtocolNotAvailableException;
import org.claros.commons.mail.exception.ServerDownException;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.utility.Constants;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.Formatter;

/**
 * @author Umut Gokbayrak
 */
public class Pop3ProtocolImpl implements Protocol {
	private static Log log = LogFactory.getLog(Pop3ProtocolImpl.class);
	private ConnectionProfile profile;
	private AuthProfile auth;
	private ConnectionMetaHandler handler;
	private static Map<String, Folder> pop3Folders = Collections.synchronizedMap(new HashMap<String, Folder>());

	/**
	 * 
	 * @param profile
	 * @param auth
	 * @param handler
	 */
	Pop3ProtocolImpl(ConnectionProfile profile, AuthProfile auth, ConnectionMetaHandler handler) {
		this.profile = profile;
		this.auth = auth;
		this.handler = handler;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.mail.protocols.FetchProtocol#connect(int)
	 */
	public ConnectionMetaHandler connect(int connectType) throws SystemException, ConnectionException, ServerDownException {
		try {
			try {
				disconnect();
				try { Thread.sleep(2000); } catch (Exception k) {}
			} catch (Exception k) {}

			if (handler == null || !handler.getStore().isConnected()) {
				Properties props = new Properties();

				if (log.isDebugEnabled()) {
					props.setProperty("mail.debug", "true");
					System.setProperty("javax.net.debug", "all");
				}
				
				if (profile.getFetchSSL() != null && profile.getFetchSSL().toLowerCase().equals("true")) {
					Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider());
					
					Security.setProperty("ssl.SocketFactory.provider", "org.claros.commons.mail.protocols.DummySSLSocketFactory");
					props.setProperty("mail.store.protocol", "pop3");
					props.setProperty("mail.pop3.host", profile.getFetchServer());
					props.setProperty("mail.pop3.port", profile.getFetchPort());
				      
					props.setProperty("mail.pop3.socketFactory.class", "org.claros.commons.mail.protocols.DummySSLSocketFactory");
					props.setProperty("mail.pop3.socketFactory.fallback", "false");
					props.setProperty("mail.pop3.port", profile.getFetchPort());
					props.setProperty("mail.pop3.socketFactory.port", profile.getFetchPort());
				}
				
				Session session = Session.getInstance(props);
				log.debug("session instance initiated");
				handler = new ConnectionMetaHandler();
				handler.setStore(session.getStore(profile.getProtocol()));
				log.debug("session store set");
				handler.getStore().connect(profile.getFetchServer(), profile.getIFetchPort(), auth.getUsername(), auth.getPassword());
				log.debug("Store has been connected... Successful");
				handler.setMbox(handler.getStore().getDefaultFolder());
				handler.setMbox(handler.getMbox().getFolder(Constants.FOLDER_INBOX(profile)));
				log.debug("Got mailbox");
				handler.getMbox().open(connectType);
				log.debug("Mailbox open");
					
				// storing the folder in map
				pop3Folders.put(auth.getUsername(), handler.getMbox());

				handler.setTotalMessagesCount(handler.getMbox().getMessageCount());
				log.debug("Message Count:" + handler.getTotalMessagesCount());
			}
		} catch (AuthenticationFailedException e) {
			log.debug("Pop3 Mailbox was busy with another session and there is a read write lock. A few minutes later when the lock is released everything will be fine.", e);
		} catch (NoSuchProviderException e) {
			log.fatal(profile.getProtocol() + " provider could not be found.");
			throw new SystemException(e);
		} catch (MessagingException e) {
			log.error("Connection could not be established.");
			throw new ConnectionException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.mail.protocols.FetchProtocol#deleteMessages(int[])
	 */
	public ConnectionMetaHandler deleteMessages(int[] messageIds) throws MailboxActionException, SystemException, ConnectionException {
		Folder fold = null;
		try {
			fold = getFolder();
			if (messageIds != null && messageIds.length > 0) {
				for (int i=0;i<messageIds.length;i++) {
					Message msg = fold.getMessage(messageIds[i]);
					msg.setFlag(Flags.Flag.DELETED, true);
				}
			}
			// fold.expunge();
			disconnect();
			connect(Constants.CONNECTION_READ_WRITE);
			return handler;
		} catch (Exception e) {
			pop3Folders.put(auth.getUsername(), null);
			log.error("Could not delete message ids: " + messageIds, e);
			throw new MailboxActionException(e);
		}
	}

	/**
	 * Fetches all e-mail headers from the server, with appropriate
	 * fields already set.
	 * @param handler
	 * @return ArrayList of MessageHeaders
	 * @throws ConnectionException
	 */
	public ArrayList<EmailHeader> fetchAllHeaders() throws SystemException, ConnectionException {
		ArrayList<EmailHeader> headers = null;
		Folder fold = null;
		try {
			fold = getFolder();
			closeFolder(fold);
			fold = getFolder();
			
			headers = new ArrayList<EmailHeader>();
			EmailHeader header = null;
            
			Message[] msgs = fold.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.FLAGS);
			fp.add(FetchProfile.Item.CONTENT_INFO);
			fp.add("Size");
			fp.add("Date");
			fold.fetch(msgs, fp);

			Message msg = null;
			for (int i = 0; i < msgs.length; i++) {
				try {
					header = new EmailHeader();
					msg = msgs[i];

					header.setMultipart((msg.isMimeType("multipart/*")) ? true : false);
					header.setMessageId(i + 1);
					header.setFrom(msg.getFrom());
					header.setTo(msg.getRecipients(Message.RecipientType.TO));
					header.setCc(msg.getRecipients(Message.RecipientType.CC));
					header.setBcc(msg.getRecipients(Message.RecipientType.BCC));
					header.setDate(msg.getSentDate());
					header.setReplyTo(msg.getReplyTo());
					header.setSize(msg.getSize());
					header.setSubject(msg.getSubject());
                    
					// now set the human readables.
					header.setDateShown(Formatter.formatDate(header.getDate(), "dd.MM.yyyy HH:mm"));
					header.setFromShown(Utility.addressArrToString(header.getFrom()));
					header.setToShown(Utility.addressArrToString(header.getTo()));
					header.setCcShown(Utility.addressArrToString(header.getCc()));
					header.setSizeShown(Utility.sizeToHumanReadable(header.getSize()));
                    
					// it is time to add it to the arraylist
					headers.add(header);
				} catch (MessagingException e1) {
					log.debug("Could not parse headers of e-mail. Message might be defuncted or illegal formatted.", e1);
				}
			}
		} catch (Exception e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
			throw new ConnectionException(e);
		}
		return headers;
	}

	/**
	 * Fetches and returns message headers as message objects.
	 * @return
	 * @throws SystemException
	 * @throws ConnectionException
	 */
	public ArrayList<Message> fetchAllHeadersAsMessages() throws SystemException, ConnectionException {
		ArrayList<Message> headers = null;
		Folder fold = null;
		try {
			fold = getFolder();
			closeFolder(fold);
			fold = getFolder();
			headers = new ArrayList<Message>();
            
			Message[] msgs = fold.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.FLAGS);
			fp.add(FetchProfile.Item.CONTENT_INFO);
			fp.add("Size");
			fp.add("Date");
			fold.fetch(msgs, fp);

			Message msg = null;
			for (int i = 0; i < msgs.length; i++) {
				msg = msgs[i];
				headers.add(msg);
			}
		} catch (Exception e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
			throw new ConnectionException(e);
		}
		return headers;
	}

	public Message getMessage(int messageId) throws MailboxActionException, SystemException, ConnectionException, Exception {
		Message msg = null;
		Folder fold = null;
		try {
			try {
				fold = getFolder();
				msg = fold.getMessage(messageId);
			} catch (Exception e) {
				log.error("Could not fetch message body from remote server.", e);
				throw new MailboxActionException(e);
			}
		} catch (Exception e) {
			throw e;
		}
		return msg;
	}

	/**
	 * Disconnects the previously opened data connection if
	 * the connection is still alive.
	 * @param handler
	 */
	public void disconnect() {
		try {
			Folder fold = (Folder)pop3Folders.get(auth.getUsername());
			if (fold != null) {
				fold.close(true);
			}
			try {
				if (handler.getMbox() != null) {
					handler.getMbox().close(true);
				}
			} catch (Exception e) {}
			
			try {
				if (handler.getStore() != null) {
					handler.getStore().close();
				}
			} catch (Exception e) {}
		} catch (Exception e) {
		}
		pop3Folders.put(auth.getUsername(), null);
	}

	public void emptyFolder() throws Exception {
		Folder f = getFolder();

		try {
			Message msgs[] = f.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			f.fetch(msgs, fp);
			
			int ids[] = new int[msgs.length];
			for (int i=0; i<msgs.length; i++) {
				ids[i] = msgs[i].getMessageNumber();
			}
			if (ids.length > 0) {
				deleteMessages(ids);
			}
		} catch (Exception e) {
			log.warn("Could not delete all messages");
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized Folder getFolder() throws Exception {
		String folder = Constants.FOLDER_INBOX(profile);

		Folder fold = (Folder)pop3Folders.get(auth.getUsername());
		if (fold != null && fold.isOpen()) {
			return fold;
		} else {
			if (folder != null && handler != null) {
				Store store = handler.getStore();
				if (store == null || !store.isConnected()) {
					log.debug("Connection is closed. Restoring it...");
					handler = connect(Constants.CONNECTION_READ_WRITE);
					log.debug("Connection re-established");
				}
				fold = handler.getStore().getFolder(folder);
				if (!fold.isOpen()) {
					log.debug("Folder :" + folder + " is closed. Opening again.");
					fold.open(Constants.CONNECTION_READ_WRITE);
					log.debug("Folder is open again.");
					
					pop3Folders.put(auth.getUsername(), fold);
				}
			}
		}
		return fold;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	/*
	public void connectIfNeeded() throws Exception {
		Folder myFold = null;
		String folder = Constants.FOLDER_INBOX(profile);

		if (folder != null && handler != null) {
			Store store = handler.getStore();
			if (store == null || !store.isConnected()) {
				log.debug("Connection is closed. Restoring it...");
				handler = connect(Constants.CONNECTION_READ_WRITE);
				log.debug("Connection re-established");
			}
			myFold = handler.getStore().getFolder(folder);
			if (!myFold.isOpen() && myFold.exists()) {
				try {
					myFold.close(true);
				} catch (Exception z) {}

				try {
					log.debug("Folder :" + folder + " is closed. Opening again.");
					myFold.open(Constants.CONNECTION_READ_WRITE);
					log.debug("Folder is open again.");
				} catch (Exception m) {
					m.printStackTrace();
				}
			}
		}
	}
	*/
	
	/**
	 * 
	 * @param f
	 */
	public void closeFolder(Folder f) {
		if (f != null) {
			try {
				if (f.isOpen()) {
					f.close(true);
					log.info("Folder: " + f.getName() + " was open and now closed.");
				} else {
					log.info("Folder: " + f.getName() + " was already closed.");
				}
			} catch (MessagingException e) {
				log.info("Error while closing folder: " + f.getName(), e);
			}
		}
		pop3Folders.put(auth.getUsername(), null);
	}

	/**
	 * @return
	 */
	public int getUnreadMessageCount() throws Exception {
		Folder f = getFolder();

		if (f.exists()) {
			return f.getUnreadMessageCount();
		}
		return 0;
	}

	/**
	 * @return
	 */
	public int getTotalMessageCount() throws Exception {
		Folder f = getFolder();
		if (f.exists()) {
			return f.getMessageCount();
		}
		return 0;
	}
	
	public void flagAsDeleted(int[] ids) throws Exception {
		deleteMessages(ids);
	}

	public ArrayList<Integer> getHeadersSortedList(String sortCriteriaRaw, String sortDirectionRaw) throws ProtocolNotAvailableException {
		profile.setSupportSort(false);
		throw new ProtocolNotAvailableException();
	}

	/**
	 * 
	 */
	public ConnectionProfile getProfile() {
		return profile;
	}

	/**
	 * 
	 */
	public ArrayList<EmailHeader> fetchHeaders(int[] msgs) throws SystemException, ConnectionException {
		throw new SystemException("this method is not available with the pop3 protocol");
	}

}
