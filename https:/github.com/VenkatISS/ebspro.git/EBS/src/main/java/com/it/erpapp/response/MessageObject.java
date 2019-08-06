package com.it.erpapp.response;

public class MessageObject {

	private int messageId;
	private String messageText;
	private String messageStatus;
	
	public MessageObject(int messageId, String messageText, String messageStatus) {
		super();
		this.messageId = messageId;
		this.messageText = messageText;
		this.messageStatus = messageStatus;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	
}
