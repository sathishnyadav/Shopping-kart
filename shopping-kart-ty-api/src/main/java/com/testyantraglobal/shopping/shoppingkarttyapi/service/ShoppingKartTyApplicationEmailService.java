package com.testyantraglobal.shopping.shoppingkarttyapi.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.EmailConfiguration;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.EmailNotSentException;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.IdNotFoundException;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class ShoppingKartTyApplicationEmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ResetPasswordLinkService linkService;

	@Autowired
	private Configuration configuration;

	public ResponseEntity<ResponseStructure<String>> sendEmailWithAttachement(EmailConfiguration config) {

		ResponseStructure<String> responseStructure = new ResponseStructure<String>();

		if (config.getUser().get("email") != null) {
			String html = null;

			try {
				MimeMessage message = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);

				helper.setTo(config.getUser().get("email"));

				helper.setSubject(config.getSubject());

				Map<String, Object> Name = new HashMap<String, Object>();
				Name.put("firstName", config.getUser().get("name"));
				Template template = configuration.getTemplate(config.getUser().get("name"));

				html = FreeMarkerTemplateUtils.processTemplateIntoString(template, Name);
				helper.setText(html, true);

				FileSystemResource resource = new FileSystemResource(new File(""));
				helper.addAttachment(resource.getFilename(), resource);
				javaMailSender.send(message);
			} catch (MessagingException | IOException | TemplateException e) {
				if (e.getClass().equals(IOException.class)) {
					e.printStackTrace();
				}
			}

			responseStructure.setData("Email Sent Sucessfully");
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setStatusCode(HttpStatus.OK.value());
		} else {
			throw new IdNotFoundException();
		}

		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<String>> sendWelcomeEmail(EmailConfiguration config,
			HttpServletRequest request, User user) {

		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (config.getUser() != null) {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true);

				helper.setTo(config.getUser().get("email"));

				helper.setSubject(config.getSubject());

				Map<String, Object> body = new HashMap<String, Object>();
				body.put("verification", linkService.getVerifyAccountlink(request, user));
				body.put("firstName", config.getUser().get("name"));
				Template template = configuration.getTemplate(config.getTemplate());
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, body);
				helper.setText(html, true);
				javaMailSender.send(message);

				responseStructure.setData("sucess");
				responseStructure.setMessage(ApplicationConstants.SUCCESS);
				responseStructure.setStatusCode(HttpStatus.OK.value());
			} catch (MessagingException | TemplateException | IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IdNotFoundException();
		}
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<String>> sendResetPasswordEmail(EmailConfiguration config, User user,
			HttpServletRequest request) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (config.getUser() != null) {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true);

				helper.setTo(config.getUser().get("email"));

				helper.setSubject(config.getSubject());

				Map<String, Object> body = new HashMap<String, Object>();
				body.put("verification", linkService.getResetPasswordlink(request, user));
				body.put("firstName", config.getUser().get("name"));
				Template template = configuration.getTemplate(config.getTemplate());
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, body);
				helper.setText(html, true);
				javaMailSender.send(message);

				responseStructure.setData("sucess");
				responseStructure.setMessage(ApplicationConstants.SUCCESS);
				responseStructure.setStatusCode(HttpStatus.OK.value());
			} catch (MessagingException | TemplateException | IOException e) {
				throw new EmailNotSentException("Mail Not Sent to User");
			}
		} else {
			throw new IdNotFoundException();
		}
		return null;
	}

}
