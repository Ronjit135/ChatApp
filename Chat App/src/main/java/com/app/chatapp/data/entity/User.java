package com.app.chatapp.data.entity;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "user")
public class User {
	private Long userId;
	private String userName;
	private String email;
	private String password;
	private HashMap<Long, List<Message>> messageMap = new HashMap<>();

	public User() {
		super();
	}

	public User(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User(Long userId, String userName, String email, String password, HashMap<Long, List<Message>> messageMap) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		if (messageMap != null)
			this.messageMap = messageMap;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public HashMap<Long, List<Message>> getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(HashMap<Long, List<Message>> messageMap) {
		this.messageMap = messageMap;
	}
}
