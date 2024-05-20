package com.pinguela.yourpc.service.impl;

import javax.mail.Authenticator;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.MailException;
import com.pinguela.yourpc.config.ConfigManager;
import com.pinguela.yourpc.service.MailService;

public class MailServiceImpl implements MailService {

	private static final String EMAIL_USER_PNAME = "service.mail.user";
	private static final String EMAIL_PASSWORD_PNAME = "service.mail.password";
	private static final String SMTP_SERVER_PNAME = "service.mail.server.name";
	private static final String SMTP_PORT_PNAME = "service.mail.server.port";
	private static final String TLS_ENABLED_PNAME = "service.mail.server.tls";

	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	private Authenticator authenticator = null;

	public MailServiceImpl() {
		authenticator = new DefaultAuthenticator(
				ConfigManager.getValue(EMAIL_USER_PNAME), 
				ConfigManager.getValue(EMAIL_PASSWORD_PNAME));
	}

	@Override
	public void send(String subject, String message, String... to) 
		throws MailException {
		
		if (subject == null || subject.length() == 0) {
			throw new MailException("Empty subject.");
		}

		Email email = null;
		
		try {
			email = new SimpleEmail();
			email.setHostName(ConfigManager.getValue(SMTP_SERVER_PNAME));
			email.setSmtpPort(Integer.valueOf(ConfigManager.getValue(SMTP_PORT_PNAME)));
			email.setAuthenticator(authenticator);
			email.setStartTLSEnabled(Boolean.valueOf(ConfigManager.getValue(TLS_ENABLED_PNAME)));
			email.setFrom(ConfigManager.getValue(EMAIL_USER_PNAME));
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(to);
			email.send();
		} catch (EmailException e) {
			logger.error(e);
			throw new MailException("Sending to " +to, e);
		}
	}

}
