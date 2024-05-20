package com.pinguela;

public class InvalidLoginCredentialsException extends ServiceException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6453910300211951784L;
	
	{
		setErrorCode(ErrorCodes.INVALID_LOGIN_CREDENTIALS);
	}
	
	public InvalidLoginCredentialsException() {
		super();
	}

	public InvalidLoginCredentialsException(Throwable cause) {
		super(cause);
	}

	public InvalidLoginCredentialsException(String message) {
		super(message);
	}

	public InvalidLoginCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}
}
