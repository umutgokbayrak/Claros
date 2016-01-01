package org.claros.intouch.webmail.models;

/**
 * @author Umut Gokbayrak
 */
public class MsgDbObject {
	private Long id;
	private String username;
	private Long folderId;
	private String uniqueId;
	private Boolean unread;
	private Long msgSize;
	private byte[] email;

	/**
	 * 
	 */
	public MsgDbObject() {
		super();
	}

	/**
	 * @return
	 */
	public byte[] getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public Long getFolderId() {
		return folderId;
	}

	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return
	 */
	public Boolean getUnread() {
		return unread;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param bs
	 */
	public void setEmail(byte[] bs) {
		email = bs;
	}

	/**
	 * @param long1
	 */
	public void setFolderId(Long long1) {
		folderId = long1;
	}

	/**
	 * @param long1
	 */
	public void setId(Long long1) {
		id = long1;
	}

	/**
	 * @param boolean1
	 */
	public void setUnread(Boolean boolean1) {
		unread = boolean1;
	}

	/**
	 * @param string
	 */
	public void setUsername(String string) {
		username = string;
	}

	/**
	 * @return
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param string
	 */
	public void setUniqueId(String string) {
		uniqueId = string;
	}

	/**
	 * @return
	 */
	public Long getMsgSize() {
		return msgSize;
	}

	/**
	 * @param long1
	 */
	public void setMsgSize(Long long1) {
		msgSize = long1;
	}

}
