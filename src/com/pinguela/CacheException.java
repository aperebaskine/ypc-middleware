package com.pinguela;

@SuppressWarnings("serial")
public class CacheException extends YPCException {

	public CacheException() {
		super();
	}

	public CacheException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public CacheException(int errorCode, String message) {
		super(errorCode, message);
	}

	public CacheException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public CacheException(int errorCode) {
		super(errorCode);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

	
	
}
