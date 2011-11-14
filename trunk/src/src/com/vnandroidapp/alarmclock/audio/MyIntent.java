package com.vnandroidapp.alarmclock.audio;

import android.content.Context;
import android.content.Intent;

public class MyIntent extends Intent {
	
	public MyIntent(Context context, Class<?> service) {
		super(context, service);
	}
	
	/**
	 * two intent will be difference if they have difference extra
	 */
	@Override
	public boolean filterEquals(Intent other) {
		//Can't override :(
//		if(super.filterEquals(other)) {
//			return getExtras().get("xxx").equals(other.getExtras().get("xxx"));
//		}
		return false;
	}
}
