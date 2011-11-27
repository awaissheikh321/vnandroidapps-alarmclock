package com.vnandroidapp.alarmclock.audio;

import com.vnandroidapp.alarmclock.*;;

public class RandomMusic {
	static int[] resIds = {
		R.raw.alarm,
		R.raw.ringtone1,
//		R.raw.ringtone2,
//		R.raw.ringtone3,
		R.raw.toyeucau,
		R.raw.why
	};

	public static int getRandomMusic() {
		return resIds[(int) (Math.random() * resIds.length)];
	}
}
