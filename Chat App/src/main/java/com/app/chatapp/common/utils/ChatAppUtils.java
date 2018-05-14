package com.app.chatapp.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.chatapp.data.entity.Message;
import com.app.chatapp.data.entity.User;

@EnableScheduling
@Component
public class ChatAppUtils {

	private static HashMap<Long, User> onlineUsersMap = new HashMap<>();
	private static List<User> onlineUserList = new ArrayList<>();

	@Scheduled(fixedRate = 10000)
	public void removeAllOnlineUsers() {
		onlineUsersMap = new HashMap<>();
	}

	public static List<User> getAllOnlineUsers() {
		return  new ArrayList<>(onlineUsersMap.values());
	}

	public static void addOnlineUser(User user) {
		if(!onlineUsersMap.containsKey(user.getUserId())){
			onlineUsersMap.put(user.getUserId(), user);
		}
	}

	public static boolean isAuthorized(HttpSession session) {
		if (session.getAttribute("user") != null)
			return true;
		return false;
	}

	public static List<Message> mergeMessages(List<Message> messageListFrom, List<Message> messageListTo) {
		List<Message> messageList = new ArrayList<>();
		messageList.addAll(messageListFrom);
		messageList.addAll(messageListTo);
		messageList.sort(Comparator.comparing(Message::getDate));
		return messageList;
	}

	public static String concatenate(List<String> listOfItems, String separator) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> stit = listOfItems.iterator();
		while (stit.hasNext()) {
			sb.append(stit.next());
			if (stit.hasNext()) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isObjectEmpty(Object object) {
		if (object == null) {
			return true;
		} else if (object instanceof String) {
			if (((String) object).trim().length() == 0) {
				return true;
			}
		} else if (object instanceof Collection) {
			return isCollectionEmpty((Collection<?>) object);
		}
		return false;
	}
}
