package com.vnandroidapp.alarmclock;

import java.util.Calendar;

import com.vnandroidapp.alarmclock.bean.Clock;
import com.vnandroidapp.alarmclock.db.ConfigManager;
import com.vnandroidapp.alarmclock.util.Common;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmSetting extends Activity implements OnClickListener {
	private static final int TIME_DIALOG_ID = 1234567;
	
	private Clock clockObj;
	
	private int mHour;
	private int mMinute;
	
	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        
        this.clockObj = ConfigManager.getClock();
        
        /**
         * initial timer
         */
        TableRow timerRow = (TableRow) findViewById(R.id.setting_timerRow);
        timerRow.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(TIME_DIALOG_ID);
			}
		});
        
        /**
         * initial Ringtone
         */
        
        /**
         * initial Repeat
         */
        TableRow repeatRow = (TableRow) findViewById(R.id.setting_repeatRow);
        repeatRow.setOnClickListener(this);
		
		mHour = this.clockObj.getHour();
		mMinute = this.clockObj.getMinute();
		
        updateDisplay();
        
        /**
         * command button OK/Cancel
         */
        Button ok = (Button) findViewById(R.id.setting_ok);
        Button cancel = (Button) findViewById(R.id.setting_cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
	}
	
	// updates the time we display in the TextView
	private void updateDisplay() {
		TextView timer = (TextView) findViewById(R.id.setting_timer);
		timer.setText(Common.format(mHour, mMinute));
		
		TextView repeat = (TextView) findViewById(R.id.setting_repeatType);
		repeat.setText(this.clockObj.getRepeater().toString());
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,	false);
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_repeatRow: {
			Intent intent = new Intent(this, RepeatSetting.class);
			startActivity(intent);
			break;
		}
		case R.id.setting_ok: {
			//save setting
			this.clockObj.setHour(mHour);
			this.clockObj.setMinute(mMinute);
			
			finish();
			break;
		}
		case R.id.setting_cancel: {
			//close 
			finish();
			break;
		}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateDisplay();
	}
}
