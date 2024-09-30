package com.pinguela.yourpc.util;

public class StringUtils {
	
	private static final String DELIMITER = "@@@";
	
	public static final String join(String... strings) {
		return String.join(DELIMITER, strings);
	}
	
	public static final String[] split(String string) {
		String[] splitString = string.split(DELIMITER);
		return splitString.length == 0 ? new String[]{string} : splitString;
	}

}
