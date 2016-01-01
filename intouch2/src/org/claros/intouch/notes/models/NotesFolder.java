package org.claros.intouch.notes.models;

/**
 * @author Umut Gokbayrak
 */
public class NotesFolder {
	private Long id;
	private String username;
	private String folderName;

	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
