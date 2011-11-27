package com.vnandroidapp.alarmclock.audio;

import com.vnandroidapp.alarmclock.RepeatSetting;
import com.vnandroidapp.alarmclock.SmartScreen;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
	private static final String TAG = "MusicService";
	private MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
//		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");

		player = MediaPlayer.create(this, RandomMusic.getRandomMusic());
		player.setLooping(false); // Set looping
	}

	@Override
	public void onDestroy() {
//		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
		player.stop();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.i("Service", "onStart() is called"); 
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.putExtras(intent);
		callIntent.putExtra("xxx2", "Hello world");
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		callIntent.setClass(this, SmartScreen.class);
		startActivity(callIntent);
		
//		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		player.start();
	}
}