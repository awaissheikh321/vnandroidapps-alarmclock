package com.myandroid.alarmclock;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ClockConfig extends Activity {
	private static final int TIME_DIALOG_ID = 1234567;
	
	private ArrayAdapter<String> adapterStatus;
	private TextView timeDisplay;
	
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
        setContentView(R.layout.config);
        
        /**
         * set status ON/OFF
         */
        Spinner cboStatus = (Spinner) findViewById(R.id.conf_status);
        adapterStatus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapterStatus.add("ON");
        adapterStatus.add("OFF");
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        cboStatus.setAdapter(adapterStatus);
        
        /**
         * timer
         */
        timeDisplay = (TextView) findViewById(R.id.conf_timeDisplay);
		timeDisplay.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
        // get the current time
        final Calendar c = Calendar.getInstance();
        this.mHour = c.get(Calendar.HOUR_OF_DAY);
        this.mMinute = c.get(Calendar.MINUTE);

        // display the current date
        updateDisplay();
        
        /**
         * command button OK/Cancel
         */
        Button ok = (Button) findViewById(R.id.conf_ok);
        Button cancel = (Button) findViewById(R.id.conf_cancel);
		ok.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//stop old media
				startService(new Intent(MusicService.ACTION_STOP));
				//start media
				startService(new Intent(MusicService.ACTION_PLAY));
				//close this ativity
				finish();
			}
		});
		cancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//close this ativity
				finish();
			}
		});
	}
	
	// updates the time we display in the TextView
	private void updateDisplay() {
		timeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":").append(pad(mMinute)));
	}

	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,	false);
		}
		return null;
	}
}
