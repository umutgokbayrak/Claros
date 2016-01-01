package org.claros.intouch.tasks.models;

import java.sql.Timestamp;

public class Task {
	private Long id;
	private String username;
	private String checked;
	private Timestamp recordDate;
	private String description;
	private Integer priority;
	private String color;

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
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Timestamp getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
