package com.pinguela;

public class MailException extends ServiceException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3215222742091207294L;
	
	{
		setErrorCode(ErrorCodes.MAIL_SERVICE_FAILURE);
	}

	public MailException() {		
	}

	public MailException(String message) {
		super(message);
	}

	public MailException(Throwable cause) {
		super(cause);
	}

	public MailException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MailException(int errorCode) {
		super(errorCode);
	}

	public MailException(int errorCode, String message) {
		super(errorCode, message);
	}

	public MailException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public MailException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
