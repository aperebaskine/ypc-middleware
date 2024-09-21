package com.pinguela.yourpc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailUtils {

	private static Logger logger = LogManager.getLogger(EmailUtils.class);

	public static final String TEST_EMAIL = "pereb_test@outlook.com";

	public static final String EMAIL_SUBJECT = "Registración en YourPC confirmada!";
	public static final String REGISTRATION_MESSAGE;

	static {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("email.html");
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
			throw new ExceptionInInitializerError();
		}
		REGISTRATION_MESSAGE = stringBuilder.toString();
	}

}
