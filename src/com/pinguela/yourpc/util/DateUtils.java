package com.pinguela.yourpc.util;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	/**
	 * Constructs a Date object containing the date corresponding to the parameters received
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return {@link java.util.Date} object containing the desired date
	 */
	public static Date getDate(int year, int month, int day) {	
		return new GregorianCalendar(year, month, day).getTime();
	}
	
	/**
	 * Constructs a Date object containing the date and time corresponding to the parameters received.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minutes
	 * @param seconds
	 * @return {@link java.util.Date} object containing the desired date and time
	 */
	public static Date getDateTime(int year, int month, int day, int hour, int minutes, int seconds) {
		return new GregorianCalendar(year, month, day, hour, minutes, seconds).getTime();
	}

}
