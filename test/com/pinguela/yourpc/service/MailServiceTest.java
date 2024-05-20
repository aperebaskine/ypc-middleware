package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.MailException;
import com.pinguela.yourpc.service.impl.MailServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class MailServiceTest {
	
	private static final String EMAIL = "pereb_test@outlook.com";
	
	private MailService mailService;
	
	private String testString;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		mailService = new MailServiceImpl();
	}

	@BeforeEach
	void setUp() throws Exception {
		testString = "TEST" +System.currentTimeMillis();
	}

	@Test
	void testSendValidEmail() {
		try {
			mailService.send(testString, testString, EMAIL);
		} catch (MailException e) {
			fail(e.getMessage(), e);
		}
	}
	
	@Test
	void testSendValidEmailToMultipleUsers() {
		try {
			mailService.send(testString, testString, EMAIL, EMAIL);
		} catch (MailException e) {
			fail(e.getMessage(), e);
		}
	}
	
	@Test
	void testSendWithoutRecipient() {
		assertThrows(MailException.class, () -> mailService.send(testString, testString, (String[]) null));
	}
	
	@Test
	void testSendToInvalidEmail() {
		assertThrows(MailException.class, () -> mailService.send(testString, testString, "notanemail"));
	}
	
	@Test
	void testSendWithoutSubject() {
		assertThrows(MailException.class, () -> mailService.send(null, testString, EMAIL));
	}
	
	@Test
	void testSendWithoutMessage() {
		assertThrows(MailException.class, () -> mailService.send(testString, null, EMAIL));
	}
	
	@Test
	void testSendEmptyMessage() {
		assertThrows(MailException.class, () -> mailService.send(testString, "", EMAIL));
	}

}
