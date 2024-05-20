package com.pinguela;

public interface LogMessages {
	
	// Database operation logs
	public static String RETRIEVED_FROM_DB = "Retrieved {} from database: {}";
	public static String INSERTING_INTO_DB = "Inserting {} into database: {}";
	public static String DELETING_FROM_DB = "Deleting {} with ID {} from database...";
	public static String UPDATING_DB = "Updating datab of {} with ID {}...";
	
	// Database error logs
	public static String INSERT_FAILED = "Error while executing INSERT statement for {}";
	public static String UPDATE_FAILED = "Error while executing UPDATE statement for {} with ID {}";
	
}
