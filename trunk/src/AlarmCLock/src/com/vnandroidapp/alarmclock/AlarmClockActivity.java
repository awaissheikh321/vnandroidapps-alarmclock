package com.vnandroidapp.alarmclock;

import java.util.Calendar;
import java.util.List;

import com.vnandroidapp.alarmclock.audio.*;
import com.vnandroidapp.alarmclock.audio.service.*;
import com.vnandroidapp.alarmclock.bean.Clock;
import com.vnandroidapp.alarmclock.bean.Repeater;
import com.vnandroidapp.alarmclock.db.ConfigManager;
import com.vnandroidapp.alarmclock.db.DatabaseAdapter;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmClockActivity extends ListActivity implements OnClickListener {
	public static final int WEEK_TIME = 7 * 24 * 60 * 60 * 60 * 1000;
	private List<Clock> clocks = null;
	private SimpleListAdapter listAdapter = null;
	private static boolean[] remainStatus = null;
	private DatabaseAdapter dbHelper;
	private Cursor cursor;
	public static final String ON = "On";
	public static final String OFF = "Off";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		dbHelper = new DatabaseAdapter(this);
		dbHelper.open();
		fillData();

		clocks = ConfigManager.loadConfig();
		remainStatus = new boolean[clocks.size()];
		for(int i = 0; i < remainStatus.length; i++) {
			remainStatus[i] = clocks.get(i).isActive();
		}
		
		this.listAdapter = new SimpleListAdapter(this, clocks);		
		setListAdapter(listAdapter);
		
		Button ok = (Button) findViewById(R.id.main_ok);
		Button cancel = (Button) findViewById(R.id.main_cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		update();
	}
	
	private void update() {
		if(this.listAdapter != null)
			this.listAdapter.notifyDataSetChanged();
	}
	
	public static void reverseStatus(int index) {
		remainStatus[index] = ! remainStatus[index];
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_ok: {
			//update status
			for(int i = 0; i < remainStatus.length; i++) {
				clocks.get(i).setActive(remainStatus[i]);
			}
			
			for(int i = 0; i < clocks.size(); i++) {
				Clock clock = clocks.get(i);
				Repeater repeater = clock.getRepeater();
				
				schedule(clock, repeater.isActived(Repeater.MONDAY), Calendar.MONDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.TUESDAY), Calendar.TUESDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.WEDNESDAY), Calendar.WEDNESDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.THURSDAY), Calendar.THURSDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.FRIDAY), Calendar.FRIDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.SATURDAY), Calendar.SATURDAY, i + 1);
				schedule(clock, repeater.isActived(Repeater.SUNDAY), Calendar.SUNDAY, i + 1);
			}
			
			update();
			finish();
			break;
		}
		case R.id.main_cancel: {
			finish();
			break;
		}
		}
	}
	
	private void schedule(Clock clock, boolean actived, int dayOfWeek, int index) {
		//select service
		Class<?> service = null;
		switch(dayOfWeek) {
		case Calendar.MONDAY: {
			switch(index) {
			case 1: {
				service = Monday1.class;
				break;
			}
			case 2: {
				service = Monday2.class;
				break;
			}
			case 3: {
				service = Monday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.TUESDAY: {
			switch(index) {
			case 1: {
				service = Tuesday1.class;
				break;
			}
			case 2: {
				service = Tuesday2.class;
				break;
			}
			case 3: {
				service = Tuesday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.WEDNESDAY: {
			switch(index) {
			case 1: {
				service = Wednesday1.class;
				break;
			}
			case 2: {
				service = Wednesday2.class;
				break;
			}
			case 3: {
				service = Wednesday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.THURSDAY: {
			switch(index) {
			case 1: {
				service = Thursday1.class;
				break;
			}
			case 2: {
				service = Thursday2.class;
				break;
			}
			case 3: {
				service = Thursday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.FRIDAY: {
			switch(index) {
			case 1: {
				service = Friday1.class;
				break;
			}
			case 2: {
				service = Friday2.class;
				break;
			}
			case 3: {
				service = Friday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.SATURDAY: {
			switch(index) {
			case 1: {
				service = Saturday1.class;
				break;
			}
			case 2: {
				service = Saturday2.class;
				break;
			}
			case 3: {
				service = Saturday3.class;
				break;
			}
			}
			break;
		}
		case Calendar.SUNDAY: {
			switch(index) {
			case 1: {
				service = Sunday1.class;
				break;
			}
			case 2: {
				service = Sunday2.class;
				break;
			}
			case 3: {
				service = Sunday3.class;
				break;
			}
			}
			break;
		}
		}
		
		Calendar systime = Calendar.getInstance();
		systime.setTimeInMillis(System.currentTimeMillis());
		int hour = systime.get(Calendar.HOUR_OF_DAY);
		int minute = systime.get(Calendar.MINUTE);
		
		//get time
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.add(Calendar.HOUR_OF_DAY, clock.getHour() - hour);
//		calendar.add(Calendar.MINUTE, clock.getMinute() - minute);
//		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		
		long curTime = SystemClock.currentThreadTimeMillis();

		Intent intent = new MyIntent(this, MusicService.class);
//		intent.putExtra("xxx", String.valueOf(Math.random()));//TODO
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		if(clock.isActive()) {//start or stop some service
			if(actived) {
				Log.d("Alarm Clock", clock.getHour() + ":"+clock.getMinute() + "?"+dayOfWeek+"="+
						hour+":"+minute+"");
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), WEEK_TIME, pendingIntent);
			}
			else {
				alarmManager.cancel(pendingIntent);
			}
		}
		else {
			alarmManager.cancel(pendingIntent);//stop all service
		}
	}
	
	private void fillData() {
		cursor = dbHelper.fetchAllAlarms();		
		
//		/** ************************************************* */
		// TODO
		cursor.close();
		/** ************************************************* */
		
	}

	
}