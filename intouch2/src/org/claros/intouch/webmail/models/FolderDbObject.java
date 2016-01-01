package org.claros.intouch.webmail.models;

/**
 * @author Umut Gokbayrak
 */
public class FolderDbObject {
	protected Long id;
	protected String username;
	protected Long parentId;
	protected String folderName;
	protected Integer folderType;

	public FolderDbObject() {
		super();
	}

	public FolderDbObject(Long id, Long parentId, String user, String folderName, Integer type) {
		this.id = id;
		this.parentId = parentId;
		this.username = user;
		this.folderName = folderName;
		this.folderType = type;
	}

	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Integer getFolderType() {
		return folderType;
	}
	public void setFolderType(Integer folderType) {
		this.folderType = folderType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
