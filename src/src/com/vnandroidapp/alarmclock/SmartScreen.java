package com.vnandroidapp.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SmartScreen extends Activity implements OnClickListener {
	private String timerStr = "";
	private String noteStr = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smart);
		try {
			timerStr = savedInstanceState.getString("xxx") == null ? "" : savedInstanceState.getString("xxx") + 
			":"+savedInstanceState.getString("xxx2") == null ? "": savedInstanceState.getString("xxx2");
		} catch(Exception e) {
			timerStr = "???";
		}
		TextView timer = (TextView) findViewById(R.id.smart_timer);
		timer.setText(timerStr);
		TextView note = (TextView) findViewById(R.id.smart_note);
		note.setText(noteStr);
		
		Button ok = (Button) findViewById(R.id.smart_ok);
		ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.smart_ok: {
			finish();
			break;
		}
		}
	}
	
	@Override
	public void startActivity(Intent intent) {
		Object obj = intent.getExtras().get("xxx");
		noteStr = obj == null ? "??" : obj.toString();
		super.startActivity(intent);
	}
}
