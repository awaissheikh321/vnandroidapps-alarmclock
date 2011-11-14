package com.vnandroidapp.alarmclock.bean;

public class Clock {
	
	private boolean isActive;
	
	private int hour;
	
	private int minute;
	
	private Repeater repeater;
	
	public Clock() {
		this(0, 0);
	}
	
	public Clock(int hour, int minute) {
		this.isActive = false;
		this.hour = hour;
		this.minute = minute;
		this.repeater = new Repeater();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Repeater getRepeater() {
		return repeater;
	}

	public void setRepeater(Repeater repeater) {
		this.repeater = repeater;
	}
}
