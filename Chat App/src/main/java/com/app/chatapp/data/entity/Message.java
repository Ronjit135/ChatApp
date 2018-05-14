package com.app.chatapp.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.chatapp.common.utils.JSONDateSerialiser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@Entity
@Table(name = "messages")
public class Message {
	private Long messageId;
	private String messageText;
	private User userFrom;
	private User userTo;
	private Date date;

	public Message() {
		super();
	}

	public Message(String messageText, User userFrom, User userTo, Date date) {
		super();
		this.messageText = messageText;
		this.userFrom = userFrom;
		this.userTo = userTo;
		this.date = date;
	}

	public Message(Long messageId, String messageText, User userFrom, User userTo, Date date) {
		super();
		this.messageId = messageId;
		this.messageText = messageText;
		this.userFrom = userFrom;
		this.userTo = userTo;
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Column(name = "message_text")
	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_from")
	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_to")
	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	@JsonSerialize(using=JSONDateSerialiser.class)
	@Column(name = "message_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
