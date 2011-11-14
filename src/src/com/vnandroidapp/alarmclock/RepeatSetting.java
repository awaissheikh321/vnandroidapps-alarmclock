package com.vnandroidapp.alarmclock;

import com.vnandroidapp.alarmclock.bean.Clock;
import com.vnandroidapp.alarmclock.bean.Repeater;
import com.vnandroidapp.alarmclock.db.ConfigManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class RepeatSetting extends Activity implements OnClickListener {
	private Repeater repeater;
	private Clock clock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repeat);
		
		this.clock = ConfigManager.getClock();
		this.repeater = clock.getRepeater().clone();
		
		//initial value for view
		((CheckBox) findViewById(R.id.repeat_Monday)).setChecked(repeater.isActived(Repeater.MONDAY));
		((CheckBox) findViewById(R.id.repeat_Tuesday)).setChecked(repeater.isActived(Repeater.TUESDAY));
		((CheckBox) findViewById(R.id.repeat_Wednesday)).setChecked(repeater.isActived(Repeater.WEDNESDAY));
		((CheckBox) findViewById(R.id.repeat_Thursday)).setChecked(repeater.isActived(Repeater.THURSDAY));
		((CheckBox) findViewById(R.id.repeat_Friday)).setChecked(repeater.isActived(Repeater.FRIDAY));
		((CheckBox) findViewById(R.id.repeat_Saturday)).setChecked(repeater.isActived(Repeater.SATURDAY));
		((CheckBox) findViewById(R.id.repeat_Sunday)).setChecked(repeater.isActived(Repeater.SUNDAY));
		
		//add listener
		findViewById(R.id.repeat_Monday).setOnClickListener(this);
		findViewById(R.id.repeat_Tuesday).setOnClickListener(this);
		findViewById(R.id.repeat_Wednesday).setOnClickListener(this);
		findViewById(R.id.repeat_Thursday).setOnClickListener(this);
		findViewById(R.id.repeat_Friday).setOnClickListener(this);
		findViewById(R.id.repeat_Saturday).setOnClickListener(this);
		findViewById(R.id.repeat_Sunday).setOnClickListener(this);
		findViewById(R.id.repeat_ok).setOnClickListener(this);
		findViewById(R.id.repeat_cancel).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.repeat_Monday: {
			repeater.set(Repeater.MONDAY, ! repeater.isActived(Repeater.MONDAY));
			break;
		}
		case R.id.repeat_Tuesday: {
			repeater.set(Repeater.TUESDAY, ! repeater.isActived(Repeater.TUESDAY));
			break;
		}
		case R.id.repeat_Wednesday: {
			repeater.set(Repeater.WEDNESDAY, ! repeater.isActived(Repeater.WEDNESDAY));
			break;
		}
		case R.id.repeat_Thursday: {
			repeater.set(Repeater.THURSDAY, ! repeater.isActived(Repeater.THURSDAY));
			break;
		}
		case R.id.repeat_Friday: {
			repeater.set(Repeater.FRIDAY, ! repeater.isActived(Repeater.FRIDAY));
			break;
		}
		case R.id.repeat_Saturday: {
			repeater.set(Repeater.SATURDAY, ! repeater.isActived(Repeater.SATURDAY));
			break;
		}
		case R.id.repeat_Sunday: {
			repeater.set(Repeater.SUNDAY, ! repeater.isActived(Repeater.SUNDAY));
			break;
		}
		case R.id.repeat_ok: {
			this.clock.setRepeater(this.repeater);
			finish();
			break;
		}
		case R.id.repeat_cancel: {
			finish();
			break;
		}
		}
	}
}
