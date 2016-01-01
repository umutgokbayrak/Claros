package org.claros.chat.controllers;

import java.util.HashMap;

import org.claros.chat.threads.ChatListener;
import org.claros.chat.threads.ChatSender;

public class TrafficController {
	private static HashMap listeners = new HashMap();
	private static HashMap senders = new HashMap();
	
	/**
	 * 
	 * @param user
	 * @param listener
	 */
	public static void addListener(String user, ChatListener listener) {
		String usrTrim = getTrimmed(user);
		
		ChatListener tmp = (ChatListener)listeners.get(usrTrim);
		if (tmp != null) {
			tmp.terminate();
		}
		listeners.put(usrTrim, listener);
	}

	/**
	 * 
	 * @param user
	 */
	public static void removeListener(String user) {
		String usrTrim = getTrimmed(user);
		listeners.remove(usrTrim);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static ChatListener getListener(String user) {
		String usrTrim = getTrimmed(user);
		return (ChatListener)listeners.get(usrTrim);
	}
	
	/**
	 * 
	 * @param user
	 * @param sender
	 */
	public static void addSender(String user, ChatSender sender) {
		String usrTrim = getTrimmed(user);

		ChatSender tmp = (ChatSender)senders.get(usrTrim);
		if (tmp != null) {
			tmp.terminate();
		}
		senders.put(usrTrim, sender);
	}

	/**
	 * 
	 * @param user
	 */
	public static void removeSender(String user) {
		String usrTrim = getTrimmed(user);
		senders.remove(usrTrim);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static ChatSender getSender(String user) {
		String usrTrim = getTrimmed(user);
		return (ChatSender)senders.get(usrTrim);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private static String getTrimmed(String user) {
		String usrTrim = user;
		if (usrTrim.indexOf("/") > 0) {
			usrTrim = usrTrim.substring(0, usrTrim.indexOf("/"));
		}
		return usrTrim;
	}

}
