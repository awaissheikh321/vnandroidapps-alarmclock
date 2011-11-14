package com.vnandroidapp.alarmclock.db;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.vnandroidapp.alarmclock.bean.Clock;

public class ConfigManager {
	private static final String TAG = "ConfigManager";
	private static List<Clock> TIMER_CONFIG = null;
	
	private static int cursorIndex = 0;
	
	public static List<Clock> loadConfig() {
		if(TIMER_CONFIG == null) {
			TIMER_CONFIG = new ArrayList<Clock>();
			for(int i = 0; i < 3; i++) {
				TIMER_CONFIG.add(new Clock());
			}
		}
		return TIMER_CONFIG;
	}
	
	public void saveConfig() {
		//TODO
	}

	public static void setCursorIndex(int index) {
		Log.i(TAG, "move to cursor = "+index);
		cursorIndex = index;
	}
	
	/**
	 * get current clock which is selected
	 * @return
	 */
	public static Clock getClock() {
		Log.i(TAG, "get clock = "+cursorIndex);
		return TIMER_CONFIG.get(cursorIndex);
	}
}
