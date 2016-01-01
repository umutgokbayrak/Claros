package org.claros.intouch.calendar.models;

import java.sql.Timestamp;

public class CalendarObject implements Cloneable {
	private Long id;
	private String username;
	private Timestamp recordDate;
	private Timestamp endDate;
	private Integer repeatType;
	private String description;
	private Integer reminderDays;
	private String color;
	private String location;
	private Integer reminderMethod;
	private String remindedBefore;
	private Timestamp lastDismissedAt;
	
	public CalendarObject() {
		super();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getReminderDays() {
		return reminderDays;
	}

	public void setReminderDays(Integer reminderDays) {
		this.reminderDays = reminderDays;
	}

	public Integer getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(Integer repeatType) {
		this.repeatType = repeatType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) { // this shouldn't happen, since we are Cloneable
			return null;
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getReminderMethod() {
		return reminderMethod;
	}

	public void setReminderMethod(Integer reminderMethod) {
		this.reminderMethod = reminderMethod;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getRemindedBefore() {
		return remindedBefore;
	}

	public void setRemindedBefore(String remindedBefore) {
		this.remindedBefore = remindedBefore;
	}

	public Timestamp getLastDismissedAt() {
		return lastDismissedAt;
	}

	public void setLastDismissedAt(Timestamp lastDismissedAt) {
		this.lastDismissedAt = lastDismissedAt;
	}
}
