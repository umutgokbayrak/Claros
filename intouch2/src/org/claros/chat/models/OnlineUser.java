package org.claros.chat.models;

public class OnlineUser {
	private String sessionId;
	private String ip;
	private String username;
	
	public OnlineUser(String sessionId, String ip, String username) {
		this.sessionId = sessionId;
		this.ip = ip;
		this.username = username;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
