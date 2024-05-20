package com.pinguela.yourpc.service;

import com.pinguela.MailException;

public interface MailService {
	
	public void send(String subject, String message, String... to)
		throws MailException;

}
