package com.pinguela;

public class ServiceException extends YPCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8903373772618396662L;

	public ServiceException() {		
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(int errorCode) {
		super(errorCode);
	}

	public ServiceException(int errorCode, String message) {
		super(errorCode, message);
	}

	public ServiceException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ServiceException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
