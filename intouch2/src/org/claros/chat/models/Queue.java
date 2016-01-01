package org.claros.chat.models;

import java.sql.Timestamp;

public class Queue {
	private Long id;
	private String msgFrom;
	private String msgTo;
	private String msgBody;
	private Timestamp msgTime;
	private String msgDirection;
	private Integer delivered;

	public Integer getDelivered() {
		return delivered;
	}
	public void setDelivered(Integer delivered) {
		this.delivered = delivered;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public String getMsgFrom() {
		return msgFrom;
	}
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}
	public Timestamp getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Timestamp msgTime) {
		this.msgTime = msgTime;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
	public String getMsgDirection() {
		return msgDirection;
	}
	public void setMsgDirection(String msgDirection) {
		this.msgDirection = msgDirection;
	}

	
}
