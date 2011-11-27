package com.vnandroidapp.alarmclock.util;

public class Common {
	
	/**
	 * format time to hh:mm
	 * @return
	 */
	public static String format(int hour, int minute) {
		return (hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour))
			+ ":" +
			(minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute));
	}
}
