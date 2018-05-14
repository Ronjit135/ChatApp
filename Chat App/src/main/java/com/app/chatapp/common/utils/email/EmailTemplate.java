package com.app.chatapp.common.utils.email;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import com.app.chatapp.common.utils.ChatAppUtils;
import com.app.chatapp.common.utils.Constants;

public class EmailTemplate {

	private String templateId;
	private String template;
	private Map<String, String> params;

	public EmailTemplate(String templateId) {
		super();
		this.templateId = templateId;
		try {
			this.template = loadTemplate(templateId);
		} catch (Exception e) {
			e.printStackTrace();
			this.template = Constants.BLANK;
		}
	}

	private String loadTemplate(String templateId) throws Exception {
		String content = Constants.BLANK;
		String filePath = "classpath:com/app/chatapp/common/resources/Email-Templates/" + templateId;
		try {
			File file = ResourceUtils.getFile(filePath);
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (Exception e) {
			throw new Exception("Could not read template : " + filePath);
		}
		return content;
	}

	public String getTemplate(Map<String, String> params) {
		String template = this.getTemplate();

		if (!ChatAppUtils.isObjectEmpty(template)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
			}
		}
		return template;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
