package com.app.chatapp.common.utils.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	JavaMailSender mailSender;

	public void send(Email eParams) {
		if (eParams.getIsHTML()) {
			try {
				sendHtmlMail(eParams);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else {
			sendPlainTextMail(eParams);
		}
	}

	private void sendHtmlMail(Email eParams) throws MessagingException {
		boolean isHtml = true;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getTo());
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(eParams.getFrom());
		helper.setSubject(eParams.getSubject());
		helper.setText(eParams.getMessage(), isHtml);
		mailSender.send(message);
	}

	private void sendPlainTextMail(Email eParams) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(eParams.getTo());
		mailMessage.setReplyTo(eParams.getFrom());
		mailMessage.setFrom(eParams.getFrom());
		mailMessage.setSubject(eParams.getSubject());
		mailMessage.setText(eParams.getMessage());
		mailSender.send(mailMessage);
	}
}
