package org.claros.intouch.webmail.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.mail.Folder;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.protocols.ImapProtocolImpl;
import org.claros.commons.mail.protocols.Protocol;
import org.claros.commons.mail.protocols.ProtocolFactory;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.webmail.models.FolderDbObject;
import org.claros.intouch.webmail.models.FolderDbObjectWrapper;

/**
 * @author Umut Gokbayrak
 */
public class ImapFolderControllerImpl implements FolderController {
	private AuthProfile auth;
	private ConnectionProfile profile;
	private ConnectionMetaHandler handler;
	private static Log log = LogFactory.getLog(ImapFolderControllerImpl.class);

	/**
	 * @param auth
	 * @param profile
	 * @param handler
	 */
	public ImapFolderControllerImpl(AuthProfile auth, ConnectionProfile profile, ConnectionMetaHandler handler) {
		this.auth = auth;
		this.profile = profile;
		this.handler = handler;
	}

	/**
	 * used to disable it.
	 */
	@SuppressWarnings("unused")
	private ImapFolderControllerImpl() {
		super();
	}

	/**
	 * 
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public Folder getFolderObj(String folder, boolean useCache) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		Folder f = protocol.getImapFolder(useCache);
		return f;
	}
	
	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#getFolders()
	 */
	public List getFolders() throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(null);
		Folder folders[] = protocol.listFolders();
		
		ArrayList res = new ArrayList();
		boolean inboxAdded = false;
		if (folders != null) {
			Folder tmp = null;
			String n, fn = null;
			for (int i=0; i<folders.length; i++) {
				try {
					tmp = folders[i];
					if (tmp != null) {
						n = tmp.getName();
						fn = tmp.getFullName();
//						if (fn != null && fn.startsWith("INBOX.")) {
						//	n = fn;
//						}
						Integer type = determineFolderType(fn);
						if (type.equals(Constants.FOLDER_TYPE_INBOX)) {
							inboxAdded = true;
						}
						FolderDbObject item = new FolderDbObject(null, null, auth.getUsername(), n, type);
						FolderDbObjectWrapper wr = new FolderDbObjectWrapper(item);
						wr.setUnreadItemCount(new Integer(tmp.getUnreadMessageCount()));
						wr.setTotalItemCount(new Integer(tmp.getMessageCount()));
						res.add(wr);
					}
				} catch (MessagingException e) {
					// do not worry about this. folder might be deleted but still subscribed.
					log.debug(e);
				}
			}
		}
		
		// inbox is not added in this server implementation(weird!!!) . Please add it.
		if (!inboxAdded) {
			FolderDbObject item = new FolderDbObject(null, null, auth.getUsername(), org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile).toUpperCase(), Constants.FOLDER_TYPE_INBOX);
			FolderDbObjectWrapper wr = new FolderDbObjectWrapper(item);
			Folder inbox = protocol.getFolder();
			if (inbox.exists()) {
				wr.setUnreadItemCount(new Integer(inbox.getUnreadMessageCount()));
				wr.setTotalItemCount(new Integer(inbox.getMessageCount()));
				res.add(wr);
			}
		}
		
		Collections.sort(res, new Comparator() {
			public int compare(Object f1, Object f2) {
				FolderDbObjectWrapper fw1 = (FolderDbObjectWrapper)f1;
				FolderDbObjectWrapper fw2 = (FolderDbObjectWrapper)f2;
				
				Integer t1 = fw1.getFolderType();
				Integer t2 = fw2.getFolderType();
				
				if (t1.equals(Constants.FOLDER_TYPE_CUSTOM) && t2.equals(Constants.FOLDER_TYPE_CUSTOM)) {
					return fw1.getFolderName().compareTo(fw2.getFolderName());
				} else {
					return t1.compareTo(t2);
				}
			}
		});

		return res;
	}

	/**
	 * 
	 * @param folderName
	 * @return
	 */
	private Integer determineFolderType(String folderName) {
		if (folderName == null) {
			return null;
		}
		String ns = profile.getFolderNameSpace();
		if (!folderName.equals("INBOX") && !folderName.startsWith(ns)) {
			folderName = ns + folderName;
		}
		if (folderName.toUpperCase().equals(org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile).toUpperCase())) {
			return Constants.FOLDER_TYPE_INBOX;	
		} else if (folderName.toUpperCase().equals(org.claros.commons.mail.utility.Constants.FOLDER_SENT(profile).toUpperCase())) {
			return Constants.FOLDER_TYPE_SENT;
		} else if (folderName.toUpperCase().equals(org.claros.commons.mail.utility.Constants.FOLDER_JUNK(profile).toUpperCase())) {
			return Constants.FOLDER_TYPE_JUNK;
		} else if (folderName.toUpperCase().equals(org.claros.commons.mail.utility.Constants.FOLDER_TRASH(profile).toUpperCase())) {
			return Constants.FOLDER_TYPE_TRASH;
		} else if (folderName.toUpperCase().equals(org.claros.commons.mail.utility.Constants.FOLDER_DRAFTS(profile).toUpperCase())) {
			return Constants.FOLDER_TYPE_DRAFTS;
		} else {
			return Constants.FOLDER_TYPE_CUSTOM;
		}
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#getFolder(java.lang.String)
	 */
	public FolderDbObject getFolder(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		Folder f = protocol.getFolder();

		FolderDbObject item = null;
		if (f.exists()) {
			item = new FolderDbObject(null, null, auth.getUsername(), f.getName(), determineFolderType(f.getName()));
		}
//		ImapProtocolImpl.closeFolder(f);
		return item;
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#getMailsByFolder(java.lang.String)
	 */
	public List getMailsByFolder(String folder) throws Exception {
		throw new SystemException("Method not supported at this protocol");
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#createFolder(org.claros.groupware.webmail.models.FolderDbItem)
	 */
	public void createFolder(FolderDbObject item) throws Exception {
		String name = item.getFolderName();
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(name);
		protocol.createFolder();
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#countUnreadMessages(java.lang.String)
	 */
	public Integer countUnreadMessages(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		int count = protocol.getUnreadMessageCount();
		return new Integer(count);
	}
	
	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#countTotalMessages(java.lang.String)
	 */
	public Integer countTotalMessages(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		int count = protocol.getTotalMessageCount();
		return new Integer(count);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#emptyFolder(java.lang.String)
	 */
	public void emptyFolder(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.emptyFolder();
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#deleteFolder(java.lang.String)
	 */
	public void deleteFolder(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		protocol.deleteFolder();
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#renameFolder(java.lang.String, java.lang.String)
	 */
	public void renameFolder(String oldName, String newName) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(oldName);
		protocol.renameFolder(newName);
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#getHeadersByFolder(java.lang.String)
	 */
	public ArrayList getHeadersByFolder(String folder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		Protocol protocol = factory.getProtocol(folder);
		ArrayList headers = protocol.fetchAllHeaders();
		
		return headers;
	}

	/* (non-Javadoc)
	 * @see org.claros.groupware.webmail.controllers.FolderController#createDefaultFolders()
	 */
	public void createDefaultFolders() throws Exception {		
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);

		ImapProtocolImpl imapJunk = (ImapProtocolImpl)factory.getImap(org.claros.commons.mail.utility.Constants.FOLDER_JUNK(profile));
		imapJunk.createFolder();
		
		ImapProtocolImpl imapSent = (ImapProtocolImpl)factory.getImap(org.claros.commons.mail.utility.Constants.FOLDER_SENT(profile));
		imapSent.createFolder();

		ImapProtocolImpl imapTrash = (ImapProtocolImpl)factory.getImap(org.claros.commons.mail.utility.Constants.FOLDER_TRASH(profile));
		imapTrash.createFolder();

		ImapProtocolImpl imapDrafts = (ImapProtocolImpl)factory.getImap(org.claros.commons.mail.utility.Constants.FOLDER_DRAFTS(profile));
		imapDrafts.createFolder();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public FolderDbObject getJunkFolder() throws Exception {
		return new FolderDbObject(null, null, null, org.claros.commons.mail.utility.Constants.FOLDER_JUNK(profile), null);
	}

	/**
	 * @param auth
	 * @return
	 */
	public FolderDbObject getInboxFolder() throws Exception {
		return new FolderDbObject(null, null, null, org.claros.commons.mail.utility.Constants.FOLDER_INBOX(profile), null);
	}

	/**
	 * @param auth
	 * @return
	 */
	public FolderDbObject getSentItems() throws Exception {
		return new FolderDbObject(null, null, null, org.claros.commons.mail.utility.Constants.FOLDER_SENT(profile), null);
	}

	/**
	 * @param auth
	 * @return
	 */
	public FolderDbObject getTrashFolder() throws Exception {
		return new FolderDbObject(null, null, null, org.claros.commons.mail.utility.Constants.FOLDER_TRASH(profile), null);
	}

	/**
	 * @param auth
	 * @return
	 */
	public FolderDbObject getDraftsFolder() throws Exception {
		return new FolderDbObject(null, null, null, org.claros.commons.mail.utility.Constants.FOLDER_DRAFTS(profile), null);
	}

	public List getHeadersByFolder(String folder, int[] msgs) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		Protocol protocol = factory.getProtocol(folder);
		ArrayList headers = protocol.fetchHeaders(msgs);
		return headers;
	}
}
