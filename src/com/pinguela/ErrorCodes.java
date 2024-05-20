package com.pinguela;

public interface ErrorCodes {
	
	public static final int UNKNOWN_ERROR = -1;
	
	/** SQL UPDATE statement did not execute correctly */
	public static final int UPDATE_FAILED = 100;
	
	/** SQL INSERT statement did not executecorrectly */
	public static final int INSERT_FAILED = 101;
	
	/** Incorrect username or password */
	public static final int INVALID_LOGIN_CREDENTIALS = 102;
	
	/** Attenoted to create an empty SQL INSERT statement */
	public static final int NO_VALUES_TO_INSERT = 103;
	
	/** Value retrieved from database did not match a set of expected values */
	public static final int UNEXPECTED_RETRIEVED_VALUE = 104;
	
	/** Class related to email service threw an exception */
	public static final int MAIL_SERVICE_FAILURE = 105;
	
	/** Unexpected number of rows in JDBC result set */
	public static final int UNEXPECTED_RESULT_SET_SIZE = 106;
	
	/** Required parameter for method is null */
	public static final int NULL_REQUIRED_PARAMETER = 107;

}
