package org.claros.chat.models;

public class Avatar {
	private String user;
	private byte[] data;
	private String hash;
	
	public Avatar(String user, byte[] data, String hash) {
		this.user = user;
		this.data = data;
		this.hash = hash;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
