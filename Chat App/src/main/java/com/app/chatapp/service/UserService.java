package com.app.chatapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.chatapp.dao.UserDAOIfc;
import com.app.chatapp.data.entity.Message;
import com.app.chatapp.data.entity.User;

@Service
public class UserService implements UserServiceIfc{

	@Autowired
	UserDAOIfc userDao;
	
	@Override
	public User registerUser(User user) {
		return userDao.registerUser(user);
	}
	
	@Override
	public User validateUserLogin(String email, String password) {
		return userDao.validateUserLogin(email, password);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}
	
	@Override
	public List<User> getAllUsers(Long userId) {
		return userDao.getAllUsers(userId);
	}

	@Override
	public Message addMessage(Message message) {
		return userDao.addMessage(message);
	}

	@Override
	public List<Message> getUserMessages(User userFrom, User userTo) {
		return userDao.getUserMessages(userFrom, userTo);
	}

	@Override
	public List<Message> getUpdatedMessages(User userFrom, User userTo, Date lastUpdateDate) {
		return userDao.getUpdatedMessages(userFrom, userTo, lastUpdateDate);
	}
}
