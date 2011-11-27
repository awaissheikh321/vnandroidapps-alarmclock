package com.vnandroidapp.alarmclock.bean;

public class Repeater {
	public static final int MONDAY = 0;
	public static final int TUESDAY = 1;
	public static final int WEDNESDAY = 2;
	public static final int THURSDAY = 3;
	public static final int FRIDAY = 4;
	public static final int SATURDAY = 5;
	public static final int SUNDAY = 6;
	
	private boolean[] values;
	private String[] names;
	
	public Repeater() {
		this.values = new boolean[7];
		set(MONDAY, true);
		set(TUESDAY, true);
		set(WEDNESDAY, true);
		set(THURSDAY, true);
		set(FRIDAY, true);
		set(SATURDAY, true);
		set(SUNDAY, true);
		this.names = new String[] {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
	}
	
	public boolean isActived(int type) {
		return this.values[type];
	}
	
	public String getName(int type) {
		return this.names[type];
	}
	
	public void set(int type, boolean isActive) {
		this.values[type] = isActive;
	}
	
	public Repeater clone() {
		Repeater r = new Repeater();
		r.values = this.values;
		return r;
	}
	
	@Override
	public String toString() {
		String str = "";
		boolean fullDays = true;
		if(isActived(MONDAY))
			str += " Mon";
		else
			fullDays = false;
		if(isActived(TUESDAY))
			str += " Tue";
		else
			fullDays = false;
		if(isActived(WEDNESDAY))
			str += " Wed";
		else
			fullDays = false;
		if(isActived(THURSDAY))
			str += " Thu";
		else
			fullDays = false;
		if(isActived(FRIDAY))
			str += " Fri";
		else
			fullDays = false;
		if(isActived(SATURDAY))
			str += " Sat";
		else
			fullDays = false;
		if(isActived(SUNDAY))
			str += " Sun";
		else
			fullDays = false;
		if("".equals(str.trim())) {
			return "None";
		}
		else {
			if(fullDays)
				return "every day";
			else
				return str;
		}
	}
}
