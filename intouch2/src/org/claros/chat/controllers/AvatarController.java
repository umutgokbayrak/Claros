package org.claros.chat.controllers;

import java.util.HashMap;

import org.claros.chat.models.Avatar;

public class AvatarController {
	private static HashMap avatars = new HashMap();


	/**
	 * 
	 * @param user
	 * @param b
	 * @param hash
	 */
	public static void addAvatar(String user, byte b[], String hash) {
		if (hash != null && b != null) {
			Avatar avatar = (Avatar)avatars.get(user);
			if (avatar == null) {
				avatars.put(user, new Avatar(user, b, hash));
			} else {
				if (!hash.equals(avatar.getHash())) {
					avatars.put(user, new Avatar(user, b, hash));
				}
			}
		}
	}


	public static Avatar getAvatar(String user) {
		return (Avatar)avatars.get(user);
	}

}
