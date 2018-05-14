package com.app.chatapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.chatapp.common.utils.ChatAppUtils;
import com.app.chatapp.data.entity.Message;
import com.app.chatapp.data.entity.User;

@Repository
public class UserDAO implements UserDAOIfc {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public User registerUser(User user) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Long userId = (Long) session.save(user);
			user.setUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public User validateUserLogin(String email, String password) {
		User user = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			user = (User) session.createNativeQuery("select * from User where email=? and password=?", User.class)
					.setParameter(1, email).setParameter(2, password).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			user = (User) session.createNativeQuery("select * from user where email=?", User.class)
					.setParameter(1, email).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return user;
	}

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			user = (User) session.createNativeQuery("select * from user where user_name=?", User.class)
					.setParameter(1, userName).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return user;
	}

	@Override
	public List<User> getAllUsers(Long userId) {
		List<User> users = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			users = session.createNativeQuery("select * from user where user_id != ?", User.class)
					.setParameter(1, userId).getResultList();
		} finally {
			if (session != null)
				session.close();
		}
		return users;
	}

	@Override
	public Message addMessage(Message message) {
		Long messageId = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			messageId = (Long) session.save(message);
			message.setMessageId(messageId);
		} finally {
			if (session != null)
				session.close();
		}

		return message;
	}

	@Override
	public List<Message> getUserMessages(User userFrom, User userTo) {
		List<Message> userMessages = new ArrayList<>();
		List<Message> messagesFrom = new ArrayList<>();
		List<Message> messagesTo = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			messagesFrom = session
					.createNativeQuery("select * from messages where user_from=? and user_to=?", Message.class)
					.setParameter(1, userFrom.getUserId()).setParameter(2, userTo.getUserId()).getResultList();
			messagesTo = session
					.createNativeQuery("select * from messages where user_from=? and user_to=?", Message.class)
					.setParameter(1, userTo.getUserId()).setParameter(2, userFrom.getUserId()).getResultList();
			userMessages = ChatAppUtils.mergeMessages(messagesFrom, messagesTo);
		} finally {
			if (session != null)
				session.close();
		}
		return userMessages;
	}

	@Override
	public List<Message> getUpdatedMessages(User userFrom, User userTo, Date lastUpdatedDate) {
		List<Message> updatedMessages = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			updatedMessages = session.createNativeQuery(
					"select * from messages where user_from=? and user_to=? and message_date > ? or user_from=? and user_to=? and message_date > ?",
					Message.class).setParameter(1, userFrom.getUserId()).setParameter(2, userTo.getUserId())
					.setParameter(3, lastUpdatedDate).setParameter(4, userTo.getUserId())
					.setParameter(5, userFrom.getUserId()).setParameter(6, lastUpdatedDate).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return updatedMessages;
	}
}
