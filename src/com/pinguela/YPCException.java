package com.pinguela;

/**
 * Excepción raiz de la empresa.
 */
public class YPCException extends Exception {

	private int errorCode = ErrorCodes.UNKNOWN_ERROR;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6659394497055968605L;

	public YPCException() {
		super();
	}

	public YPCException(String message) {
		super(message);
	}

	public YPCException(Throwable cause) {
		super(cause);
	}
	
	public YPCException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public YPCException(int errorCode) {
		super();
		setErrorCode(errorCode);
	}

	public YPCException(int errorCode, String message) {
		super(message);
		setErrorCode(errorCode);
	}

	public YPCException(int errorCode, Throwable cause) {
		super(cause);
		setErrorCode(errorCode);
	}
	
	public YPCException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		setErrorCode(errorCode);
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String toString() {
		return String.format(super.toString() +" (Error Code: %d)", this.errorCode);
	}

}
