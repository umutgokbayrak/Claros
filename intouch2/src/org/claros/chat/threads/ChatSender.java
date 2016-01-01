package org.claros.chat.threads;

import java.util.HashMap;
import java.util.List;

import org.claros.chat.controllers.QueueController;
import org.claros.chat.models.Queue;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

/**
 * @author Umut Gokbayrak
 */
public class ChatSender extends Thread {
	private String user;
	private XMPPConnection conn;
	private HashMap chatMap;
	private boolean running;
	private String defaultDomain;
	
	/**
	 * Do not use this constructor
	 */
	@SuppressWarnings("unused")
	private ChatSender() {
		super();
	}

	public ChatSender(String user, XMPPConnection conn, String defaultDomain) {
		if (chatMap == null) {
			chatMap = new HashMap();
		}
		this.user = user;
		this.conn = conn;
		this.running = true;
		this.defaultDomain = defaultDomain;
	}

	/**
	 * method to call to stop this thread
	 *
	 */
	public void terminate() {
		running = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (running) {
			try {
				List msgs = QueueController.fetchUserMessages(user, QueueController.QUEUE_OUT, defaultDomain);
				if (msgs != null) {
					Queue tmp = null;
					for (int i=0; i<msgs.size(); i++) {
						tmp = (Queue)msgs.get(i);
						
						Chat chat = (Chat)chatMap.get(tmp.getMsgTo());
						if (chat == null) {
							chat = conn.getChatManager().createChat(tmp.getMsgTo(), new MessageListener() {
								public void processMessage(Chat ch, Message msg) {
									System.out.println("msg: " + msg);
								}
							});
							chatMap.put(tmp.getMsgTo(), chat);
						}
						chat.sendMessage(tmp.getMsgBody());
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			// sleep for a while and then go on;
			try { 
				Thread.sleep((long)(Math.random() * 1500L));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * creates a message and adds it to the send queue
	 * @param from
	 * @param to
	 * @param body
	 */	
	public void sendMessage(String to, String body) {
		QueueController.push(QueueController.prepareName(user, defaultDomain), to, body, QueueController.QUEUE_OUT);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
