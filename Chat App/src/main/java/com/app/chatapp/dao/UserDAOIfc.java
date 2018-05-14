package com.app.chatapp.dao;

import java.util.Date;
import java.util.List;

import com.app.chatapp.data.entity.Message;
import com.app.chatapp.data.entity.User;

public interface UserDAOIfc {
	public User registerUser(User user);
	public User validateUserLogin(String email, String password);
	public User getUserByEmail(String email);
	public User getUserByUserName(String userName);
	public List<User> getAllUsers(Long userId);
	public Message addMessage(Message message);
	public List<Message> getUserMessages(User userFrom, User userTo);
	public List<Message> getUpdatedMessages(User userFrom, User userTo, Date lastUpdateDate);
}