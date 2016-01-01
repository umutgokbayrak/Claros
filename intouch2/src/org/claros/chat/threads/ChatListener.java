package org.claros.chat.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.chat.controllers.QueueController;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author Umut Gokbayrak
 */
public class ChatListener extends Thread {
	private static Log log = LogFactory.getLog(ChatListener.class);
	private static List subsRequest;
	private String user;
	private XMPPConnection conn;
	private MyListener listener;
	private MyFilter filter;

	/**
	 * Do not use this constructor
	 *
	 */
	@SuppressWarnings("unused")
	private ChatListener() {
		super();
	}

	/**
	 * Constructor method.
	 * @param user
	 * @param conn
	 */
	public ChatListener(String user, XMPPConnection conn) {
		if (subsRequest == null) {
			subsRequest = Collections.synchronizedList(new ArrayList());
		}
		this.user = user;
		this.conn = conn;
		listener = new MyListener();
		filter = new MyFilter();
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		conn.addPacketListener(listener, filter);
	}

	/**
	 * method to call to stop this thread
	 *
	 */
	public void terminate() {
		try {
			conn.removePacketListener(listener);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public List getUnreadMessages(String defaultDomain) {
		List msgs = null;
		try {
			msgs = QueueController.fetchUserMessages(user, QueueController.QUEUE_IN, defaultDomain);
		} catch (Exception e) {
			log.error("unable to get unread messages", e);
		}
		return msgs;
	}
	
	public synchronized List getNewSubscriptions() {
		// TODO: mesajlardakinin aynısını queue yapısı bunda yapmalıyız.
		
		ArrayList outList = new ArrayList();
		ArrayList toDelete = new ArrayList();
		
		// look if new messages are waiting
		Presence tmp = null;
		String tmpTo = null;
		int tmpPos = -1;
		for (int i=0;i<subsRequest.size();i++) {
			tmp = (Presence)subsRequest.get(i);
			tmpTo = tmp.getTo();
			tmpPos = tmpTo.indexOf("/");
			if (tmpPos > -1) {
				tmpTo = tmpTo.substring(0, tmpPos);
			}
			if (tmpTo.equals(user)) {
				toDelete.add(new Integer(i));
				outList.add(tmp);
			}
		}

		// remove the fetched messages
		for (int i=toDelete.size()-1;i>=0;i--) {
			try {
				subsRequest.remove(((Integer)toDelete.get(i)).intValue());
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return outList;
	} 	

	/**
	 * Listener class implementation
	 * 
	 * @author Umut Gokbayrak
	 */
	class MyListener implements PacketListener {

		/* (non-Javadoc)
		 * @see org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack.packet.Packet)
		 */
		public void processPacket(Packet arg0) {
			if (arg0 instanceof Message) {
				Message msg = (Message)arg0;
				
				if (msg.getType().equals(Message.Type.chat)) {
					if (msg.getFrom().indexOf("/") > -1) {
						msg.setFrom(msg.getFrom().substring(0, msg.getFrom().indexOf("/")));
					}

					if (msg.getTo().indexOf("/") > -1) {
						msg.setTo(msg.getTo().substring(0, msg.getTo().indexOf("/")));
					}

					// pushing the message to db.
					if (msg.getBody() != null) {
						QueueController.push(msg.getFrom(), msg.getTo(), msg.getBody(), QueueController.QUEUE_IN);
						log.debug("new message written to queue, from:" + msg.getFrom() + " to: " + msg.getTo() + " body: " + msg.getBody());
					}
				}
			} else if (arg0 instanceof Presence) {
				Presence prs = (Presence)arg0;
				if (prs.getType().equals(Presence.Type.subscribe)) {
					subsRequest.add(arg0);
				}
			}
		}
	}

	/**
	 * Listener packet filter implementation.
	 * 
	 * @author Umut Gokbayrak
	 */
	class MyFilter implements PacketFilter {

		/* (non-Javadoc)
		 * @see org.jivesoftware.smack.filter.PacketFilter#accept(org.jivesoftware.smack.packet.Packet)
		 */
		public boolean accept(Packet arg0) {
			// TODO: ignored users should be handled here.
			return true;
		}
	}

	/**
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param string
	 */
	public void setUser(String string) {
		user = string;
	}

}
