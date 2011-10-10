package com.myandroid.alarmclock;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CheckboxCursorAdapter extends SimpleCursorAdapter {

	private Cursor c;
	private Context context;
	private ArrayList<String> checkList = new ArrayList<String>();

	public CheckboxCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
	}

	public View getView(int pos, View inView, ViewGroup parent) {
		View v = inView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.check_list, null);
		}
		this.c.moveToPosition(pos);
		String bookmark = this.c.getString(this.c
				.getColumnIndex(Browser.BookmarkColumns.TITLE));
		CheckBox cBox = (CheckBox) v.findViewById(R.id.bcheck);
		cBox.setTag(Integer.parseInt(this.c.getString(this.c
				.getColumnIndex(Browser.BookmarkColumns._ID))));
		cBox.setText(bookmark);
		if (Integer.parseInt(this.c.getString(this.c
				.getColumnIndex(Browser.BookmarkColumns._ID))) % 2 != 0) {
			cBox.setChecked(true);
//			checkList.add((String) cBox.getTag());
		}
		cBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox cBox = (CheckBox) v.findViewById(R.id.bcheck);
				if (cBox.isChecked()) {
					// cBox.setChecked(false);
//					checkList.add((String) cBox.getTag());
					Intent i = new Intent(context, ClockConfig.class);

					context.startActivity(i);
				} else if (!cBox.isChecked()) {
					// cBox.setChecked(true);
//					checkList.remove(cBox.getTag());
				}
			}
		});
		return (v);
	}

}
