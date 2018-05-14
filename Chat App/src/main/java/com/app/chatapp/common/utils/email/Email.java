package com.app.chatapp.common.utils.email;

public class Email {
	private String from;
	private String to;
	private String subject;
	private String message;
	private Boolean isHTML;

	public Email() {
		super();
	}

	public Email(String from, String to, String subject, String message) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsHTML() {
		return isHTML;
	}

	public void setIsHTML(Boolean isHTML) {
		this.isHTML = isHTML;
	}
}
