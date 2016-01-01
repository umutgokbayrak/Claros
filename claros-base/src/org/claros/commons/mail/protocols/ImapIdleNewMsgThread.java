package org.claros.commons.mail.protocols;

import java.io.IOException;

import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

import com.sun.mail.imap.IMAPFolder;
/**
 * @author umut
 *
 */
public class ImapIdleNewMsgThread extends Thread {
	private IMAPFolder fold;
	
	public ImapIdleNewMsgThread(Folder folder) {
		if (folder instanceof IMAPFolder) {
			fold = (IMAPFolder)folder;
		}
	}
	
	public void run() {
		try {
			monitorNewMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses the IMAP IDLE Extension and monitors for new mail.
	 * @throws Exception
	 */
	public void monitorNewMail() throws Exception {
        // Add messageCountListener to listen for new messages
        fold.addMessageCountListener(new MessageCountAdapter() {
            public void messagesAdded(MessageCountEvent ev) {
                Message[] msgs = ev.getMessages();
                System.out.println("Got " + msgs.length + " new messages in folder" + fold.getFullName());

                // Just dump out the new messages
                for (int i = 0; i < msgs.length; i++) {
                    try {
                        System.out.println("-----");
                        System.out.println("Message " + msgs[i].getMessageNumber() + ":");
                        msgs[i].writeTo(System.out);
                    } catch (IOException ioex) {
                        ioex.printStackTrace();
                    } catch (MessagingException mex) {
                        mex.printStackTrace();
                    }
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
