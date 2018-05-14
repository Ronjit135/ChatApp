package com.app.chatapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.chatapp.common.utils.ChatAppUtils;
import com.app.chatapp.common.utils.Constants;
import com.app.chatapp.common.utils.email.Email;
import com.app.chatapp.common.utils.email.EmailService;
import com.app.chatapp.common.utils.email.EmailTemplate;
import com.app.chatapp.data.entity.Message;
import com.app.chatapp.data.entity.User;
import com.app.chatapp.service.UserServiceIfc;

@Controller
public class MainController {

	private static HashMap<Long, User> onlineUserList = new HashMap<>();

	@Autowired
	EmailService emailService;

	@Autowired
	UserServiceIfc userService;

	@GetMapping("/")
	public String showLogin() {
		return "Login";
	}

	@PostMapping("/Login")
	public String validateLogin(String email, String password, HttpSession session) {
		User user = userService.validateUserLogin(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			session.setAttribute("showNoMsg", true);
			return "redirect:/Home";
		}
		return "redirect:/";
	}

	@GetMapping("/Home")
	public String showHome(ModelMap map, HttpSession session) {
		if (!ChatAppUtils.isAuthorized(session)) {
			return "redirect:/";
		}
		User userFrom = (User) session.getAttribute("user");
		List<User> userList = userService.getAllUsers(userFrom.getUserId());
		map.addAttribute("userList", userList);
		if (!(boolean) session.getAttribute("showNoMsg") && (User) session.getAttribute("userTo") != null) {
			User userTo = (User) session.getAttribute("userTo");
			List<Message> messageList = userService.getUserMessages(userFrom, userTo);
			map.addAttribute("msgList", messageList);
			Date messageDate = messageList.size() > 0 ? messageList.get(messageList.size() - 1).getDate() : new Date();
			session.setAttribute("lastUpdateDate", messageDate);
		}
		return "HomePage";
	}

	@GetMapping("/Chat")
	public String selectContact(String email, HttpSession session) {
		if (!ChatAppUtils.isAuthorized(session)) {
			return "redirect:/";
		}
		User user = userService.getUserByEmail(email);
		session.setAttribute("userTo", user);
		session.setAttribute("showNoMsg", false);
		return "redirect:/Home";
	}

	@PostMapping("/SendMessage")
	public @ResponseBody Boolean sendMessage(@RequestParam(name = "messageText") String messageText,
			HttpSession session) {
		if (!ChatAppUtils.isAuthorized(session)) {
			return false;
		}
		User userFrom = (User) session.getAttribute("user");
		User userTo = (User) session.getAttribute("userTo");
		Message message = new Message(messageText, userFrom, userTo, new Date());
		message = userService.addMessage(message);
		return true;
	}

	@PostMapping("/UpdateMessages")
	public @ResponseBody HashMap<String, List<Message>> updateMessages(HttpSession session) {
		Date lastUpdateDate = (Date) session.getAttribute("lastUpdateDate");
		User userFrom = (User) session.getAttribute("user");
		User userTo = (User) session.getAttribute("userTo");
		List<Message> userMessageList = new ArrayList<>();
		userMessageList.add(new Message(null, userFrom, userTo, null));
		List<Message> updatedMessageList = new ArrayList<>();
		HashMap<String, List<Message>> messageMap = new HashMap<>();
		if (lastUpdateDate == null || userFrom == null || userTo == null) {
			return messageMap;
		}
		updatedMessageList = userService.getUpdatedMessages(userFrom, userTo, lastUpdateDate);
		updatedMessageList.sort(Comparator.comparing(Message::getDate));
		Date messageDate = updatedMessageList.size() > 0
				? updatedMessageList.get(updatedMessageList.size() - 1).getDate()
				: lastUpdateDate;
		session.setAttribute("lastUpdateDate", messageDate);

		messageMap.put("user", userMessageList);
		messageMap.put("msgs", updatedMessageList);

		return messageMap;
	}

	@PostMapping("/Users")
	public @ResponseBody List<User> getOnlineUsers(HttpSession session){
		return ChatAppUtils.getAllOnlineUsers();
	}
	
	@PostMapping("/UpdateStatus")
	public @ResponseBody String updateStatus(@RequestParam(name="userName") String userName, HttpSession session){
		ChatAppUtils.addOnlineUser(userService.getUserByUserName(userName));
		return "Online";
	}

	@GetMapping("/Register")
	public String showRegisterUser() {
		return "Register";
	}

	@PostMapping("/Validate")
	public @ResponseBody HashMap<String, Boolean> validateRegisterUser(@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, @RequestParam(name = "userName") String userName) {
		Boolean emailFlag = true;
		Boolean passwordFlag = true;
		Boolean userNameFlag = true;
		Boolean registerFlag = false;
		HashMap<String, Boolean> responseMap = new HashMap<>();
		User user = userService.getUserByEmail(email);
		if (user != null) {
			emailFlag = false;
		}
		user = userService.registerUser(new User(userName, email, password));
		if (user.getUserId() != null) {
			registerFlag = true;
		}
		responseMap.put("email", emailFlag);
		responseMap.put("password", passwordFlag);
		responseMap.put("userName", userNameFlag);
		responseMap.put("register", registerFlag);
		return responseMap;
	}

	@GetMapping("/ForgotPassword")
	public String showForgotPassword() {
		return "ForgotPassword";
	}

	@PostMapping("/ForgotPassword")
	public String resetPassword(String email) {
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return "ForgotPassword";
		}
		Random random = new Random();
		Long password = random.nextLong();
		Long newPassword = password > 0 ? password : -password;
		String from = Constants.EMAIL_FROM;
		String to = email;
		String title = Constants.EMAIL_TITLE_FORGOT_PASSWORD;
		String subject = Constants.EMAIL_SUBJECT_FORGOT_PASSWORD;
		String userName = user.getUserName();
		String templateId = Constants.FORGOT_PASSWORD_TEMPLATE;
		EmailTemplate template = new EmailTemplate(templateId);
		Map<String, String> params = new HashMap<>();
		params.put(Constants.TITLE, title);
		params.put(Constants.USER, userName);
		params.put(Constants.PASSWORD, newPassword.toString());
		String message = template.getTemplate(params);
		Email mail = new Email(from, to, subject, message);
		mail.setIsHTML(true);
		emailService.send(mail);
		return "redirect:/";
	}

	@GetMapping("/Logout")
	public String performLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
