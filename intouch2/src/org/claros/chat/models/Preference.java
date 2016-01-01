package org.claros.chat.models;

public class Preference {
	private Long id;
	private String username;
	private String prefKey;
	private String prefValue;
	
	public Preference() {
		super();
	}
	
	public Preference(String user, String key, String val) {
		this.username = user;
		this.prefKey = key;
		this.prefValue = val;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrefKey() {
		return prefKey;
	}
	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}
	public String getPrefValue() {
		return prefValue;
	}
	public void setPrefValue(String prefValue) {
		this.prefValue = prefValue;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
