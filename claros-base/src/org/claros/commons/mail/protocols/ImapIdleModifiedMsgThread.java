package org.claros.commons.mail.protocols;

import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;

import com.sun.mail.imap.IMAPFolder;

public class ImapIdleModifiedMsgThread extends Thread {
	private IMAPFolder fold;
	
	public ImapIdleModifiedMsgThread(Folder folder) {
		if (folder instanceof IMAPFolder) {
			fold = (IMAPFolder)folder;
		}
	}

	public void run() {
		try {
			monitorModifiedMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses the IMAP IDLE Extension and monitors for modified mail.
	 * @throws Exception
	 */
	public void monitorModifiedMail() throws Exception {
        // Add messageCountListener to listen for new messages
        fold.addMessageChangedListener(new MessageChangedListener() {
        	public void messageChanged(MessageChangedEvent ev) {
                try {
					Message msg = ev.getMessage();
					System.out.println("In folder " + fold.getFullName() + " A message has been changed. Info:..... Subject: " + msg.getSubject() + " From: " + msg.getFrom().toString());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
        	}
        });

        // Check mail once in "freq" MILLIseconds
        try {
			int freq = 5000;
			boolean supportsIdle = false;
			try {
			    if (fold instanceof IMAPFolder) {
			        IMAPFolder f = (IMAPFolder)fold;
			        f.idle(); 
			        supportsIdle = true;
			    }
			} catch (FolderClosedException fex) {
			    throw fex;
			} catch (MessagingException mex) {
			    supportsIdle = false;
			}
			for (;;) {
			    if (supportsIdle && fold instanceof IMAPFolder) {
			        IMAPFolder f = (IMAPFolder)fold;
			        f.idle();
			        System.out.println("IDLE done");
			    } else {
			        Thread.sleep(freq); // sleep for freq milliseconds
			        // This is to force the IMAP server to send us
			        // EXISTS notifications.
			        fold.getMessageCount();
			    }
			}
		} catch (FolderClosedException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
