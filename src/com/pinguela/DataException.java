package com.pinguela;

public class DataException extends YPCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5031478115324921791L;

	public DataException() {		
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DataException(int errorCode) {	
		super(errorCode);
	}

	public DataException(int errorCode, String message) {
		super(errorCode, message);
	}

	public DataException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public DataException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
}
