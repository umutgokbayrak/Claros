package org.claros.intouch.notes.models;

import java.sql.Timestamp;

/**
 * @author Umut Gokbayrak
 */
public class Note {
	private Long id;
	private String username;
	private Long folderId;
	private String noteContent;
	private Integer posLeft;
	private Integer posTop;
	private Integer posHeight;
	private Integer posWidth;
	private String noteColor;
	private String noteBarColor;
	private String noteBorderColor;
	private Timestamp noteDate;

	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
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
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public Integer getPosHeight() {
		return posHeight;
	}
	public void setPosHeight(Integer posHeight) {
		this.posHeight = posHeight;
	}
	public Integer getPosLeft() {
		return posLeft;
	}
	public void setPosLeft(Integer posLeft) {
		this.posLeft = posLeft;
	}
	public Integer getPosTop() {
		return posTop;
	}
	public void setPosTop(Integer posTop) {
		this.posTop = posTop;
	}
	public Integer getPosWidth() {
		return posWidth;
	}
	public void setPosWidth(Integer posWidth) {
		this.posWidth = posWidth;
	}
	public String getNoteBarColor() {
		return noteBarColor;
	}
	public void setNoteBarColor(String noteBarColor) {
		this.noteBarColor = noteBarColor;
	}
	public String getNoteBorderColor() {
		return noteBorderColor;
	}
	public void setNoteBorderColor(String noteBorderColor) {
		this.noteBorderColor = noteBorderColor;
	}
	public String getNoteColor() {
		return noteColor;
	}
	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}
	public Timestamp getNoteDate() {
		return noteDate;
	}
	public void setNoteDate(Timestamp noteDate) {
		this.noteDate = noteDate;
	}


}
