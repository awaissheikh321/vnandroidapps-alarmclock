package com.vnandroidapp.alarmclock;

import java.util.List;

import com.vnandroidapp.alarmclock.bean.Clock;
import com.vnandroidapp.alarmclock.db.ConfigManager;
import com.vnandroidapp.alarmclock.util.Common;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final List<Clock> clocks;
	
	public SimpleListAdapter(Activity context, List<Clock> clocks) {
		super(context, R.layout.main_list_item, displayName(clocks));
		this.context = context;
		this.clocks = clocks;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.main_list_item, null, true);
		TextView textView = (TextView) rowView.findViewById(R.id.main_timer);
		CheckBox checkView = (CheckBox) rowView.findViewById(R.id.main_checkbox);
		TextView repeatView = (TextView) rowView.findViewById(R.id.main_repeatType);
		TextView am_pmView = (TextView) rowView.findViewById(R.id.main_AM_PM);
		Clock clock = clocks.get(position);
		
		// initial timer and status
		textView.setText(Common.format(clock.getHour(), clock.getMinute()));
		repeatView.setText(clock.getRepeater().toString());
		am_pmView.setText(clock.getHour() > 11 ? "PM" : "AM");
		if (clock.isActive()) {
			checkView.setChecked(true);
		} else {
			checkView.setChecked(false);
		}
		
		// initial View selected
		LinearLayout timerContainer = (LinearLayout) rowView.findViewById(R.id.main_timerContainer);
		timerContainer.setId(position);
		timerContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfigManager.setCursorIndex(v.getId());
				Intent intent = new Intent(context, AlarmSetting.class);
				context.startActivity(intent);
//				notifyDataSetChanged();
			}});
		
		// initial status changed
		checkView.setId(position);
		checkView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = v.getId();
//				ConfigManager.setCursorIndex(index);
//				Clock clock = ConfigManager.getClock();
				AlarmClockActivity.reverseStatus(index);
//				clock.setActive(! clock.isActive());
			}});
		

		return rowView;
	}
	
	private static String[] displayName(List<Clock> clocks) {
		String[] displayNames = new String[clocks.size()];
		for(int i = 0; i < clocks.size(); i++) {
			displayNames[i] = clocks.get(i).toString();
		}
		return displayNames;
	}
}
