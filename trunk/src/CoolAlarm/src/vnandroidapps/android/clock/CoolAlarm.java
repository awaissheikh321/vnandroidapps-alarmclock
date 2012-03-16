package vnandroidapps.android.clock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.Intents;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.Calendar;

import vnandroidapps.android.clock.R;
import vnandroidapps.android.clock.utils.Background;


/**
 * AlarmClock application.
 */
public class CoolAlarm extends Activity {

    final static String PREFERENCES = "AlarmClock";
    final static int SET_ALARM = 1;
    final static String PREF_CLOCK_FACE = "face";
    final static String PREF_SHOW_CLOCK = "show_clock";

    /** Cap alarm count at this number */
    final static int MAX_ALARM_COUNT = 12;

    private SharedPreferences mPrefs;
    private LayoutInflater mFactory;
    private ViewGroup mClockLayout;
    private View mClock = null;
    private MenuItem mAddAlarmItem;
    private MenuItem mToggleClockItem;
    private ListView mAlarmsList;
    private Cursor mCursor;

    /**
     * Which clock face to show
     */
    private int mFace = -1;

    final static int[] CLOCKS = {
        R.layout.analog_clock,
        R.layout.clock_basic_bw,
        R.layout.clock_googly,
        R.layout.clock_droid2,
        R.layout.clock_droids,
        R.layout.digital_clock
    };

    private class AlarmTimeAdapter extends CursorAdapter {
        public AlarmTimeAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View ret = mFactory.inflate(R.layout.alarm_time, parent, false);
            DigitalClock digitalClock = (DigitalClock)ret.findViewById(R.id.digitalClock);
            digitalClock.setLive(false);
            if (Log.LOGV) Log.v("newView " + cursor.getPosition());
            return ret;
        }

        public void bindView(View view, Context context, Cursor cursor) {
            final int id = cursor.getInt(Alarms.AlarmColumns.ALARM_ID_INDEX);
            final int hour = cursor.getInt(Alarms.AlarmColumns.ALARM_HOUR_INDEX);
            final int minutes = cursor.getInt(Alarms.AlarmColumns.ALARM_MINUTES_INDEX);
            final Alarms.DaysOfWeek daysOfWeek = new Alarms.DaysOfWeek(
                    cursor.getInt(Alarms.AlarmColumns.ALARM_DAYS_OF_WEEK_INDEX));
            final boolean enabled = cursor.getInt(Alarms.AlarmColumns.ALARM_ENABLED_INDEX) == 1;

            CheckBox onButton = (CheckBox)view.findViewById(R.id.alarmButton);
            onButton.setChecked(enabled);
            onButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        boolean isChecked = ((CheckBox) v).isChecked();
                        Alarms.enableAlarm(CoolAlarm.this, id, isChecked);
                        if (isChecked) {
                            SetAlarm.popAlarmSetToast(
                                    CoolAlarm.this, hour, minutes, daysOfWeek);
                        }
                    }
            });

            DigitalClock digitalClock = (DigitalClock)view.findViewById(R.id.digitalClock);
            if (Log.LOGV) Log.v("bindView " + cursor.getPosition() + " " + id + " " + hour +
                                ":" + minutes + " " + daysOfWeek.toString(context, true) + " dc " + digitalClock);

            digitalClock.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (true) {
                            Intent intent = new Intent(CoolAlarm.this, SetAlarm.class);
                            intent.putExtra(Alarms.ID, id);
                            startActivityForResult(intent, SET_ALARM);
                        } else {
                            // TESTING: immediately pop alarm
                            Intent fireAlarm = new Intent(CoolAlarm.this, AlarmAlert.class);
                            fireAlarm.putExtra(Alarms.ID, id);
                            fireAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(fireAlarm);
                        }
                    }
                });

            // set the alarm text
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minutes);
            digitalClock.updateTime(c);
            TextView daysOfWeekView = (TextView) digitalClock.findViewById(R.id.daysOfWeek);
            daysOfWeekView.setText(daysOfWeek.toString(CoolAlarm.this, false));
        }
    };


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Background.setDefaultBackground(getWindow());

        // sanity check -- no database, no clock
        if (getContentResolver() == null) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.dberror))
                    .setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create().show();
            return;
        }
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.alarm_clock);
 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.tilte);
        ImageView tv = (ImageView)findViewById(R.id.tv);
        tv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				Intent in = new Intent(v.getContext(), WebHome.class);
				startActivityForResult(in, 0);
				return false;
			}
		});
        mFactory = LayoutInflater.from(this);
        mPrefs = getSharedPreferences(PREFERENCES, 0);

        mCursor = Alarms.getAlarmsCursor(getContentResolver());
        mAlarmsList = (ListView) findViewById(R.id.alarms_list);
        mAlarmsList.setAdapter(new AlarmTimeAdapter(this, mCursor));
        mAlarmsList.setVerticalScrollBarEnabled(true);
        mAlarmsList.setItemsCanFocus(true);

        mClockLayout = (ViewGroup) findViewById(R.id.clock_layout);
        mClockLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent intent = new Intent(CoolAlarm.this, ClockPicker.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

        setClockVisibility(mPrefs.getBoolean(PREF_SHOW_CLOCK, true));
    }

    @Override
    protected void onResume() {
        super.onResume();

        int face = mPrefs.getInt(PREF_CLOCK_FACE, 0);
        if (mFace != face) {
            if (face < 0 || face >= CoolAlarm.CLOCKS.length)
                mFace = 0;
            else
                mFace = face;
            inflateClock();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastMaster.cancelToast();
        mCursor.deactivate();
    }

    protected void inflateClock() {
        if (mClock != null) {
            mClockLayout.removeView(mClock);
        }
        mClock = mFactory.inflate(CLOCKS[mFace], null);
        mClockLayout.addView(mClock, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        mAddAlarmItem = menu.add(0, 0, 0, R.string.add_alarm);
        mAddAlarmItem.setIcon(android.R.drawable.ic_menu_add);

        mToggleClockItem = menu.add(0, 0, 0, R.string.hide_clock);
        mToggleClockItem.setIcon(R.drawable.ic_menu_clock_face);

        if (false) {
            Intent intent = new Intent();
            intent.setClassName(
                    "com.android.settings",
                    "com.android.settings.DateTimeSettings");
            MenuItem settingsItem = menu.add(0, 0, 0, R.string.settings).setIntent(intent);
            settingsItem.setIcon(android.R.drawable.ic_menu_preferences);
        }

        return true;
    }

    /**
     * Only allow user to add a new alarm if there are fewer than
     * MAX_ALARM_COUNT
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mAddAlarmItem.setVisible(mAlarmsList.getChildCount() < MAX_ALARM_COUNT);
        mToggleClockItem.setTitle(getClockVisibility() ? R.string.hide_clock :
                                  R.string.show_clock);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == mAddAlarmItem) {
            Uri uri = Alarms.addAlarm(getContentResolver());
            String segment = uri.getPathSegments().get(1);
            int newId = Integer.parseInt(segment);
            if (Log.LOGV) Log.v("In AlarmClock, new alarm id = " + newId);
            Intent intent = new Intent(CoolAlarm.this, SetAlarm.class);
            intent.putExtra(Alarms.ID, newId);
            startActivityForResult(intent, SET_ALARM);
            return true;
        } else if (item == mToggleClockItem) {
            setClockVisibility(!getClockVisibility());
            saveClockVisibility();
            return true;
        }

        return false;
    }


    private boolean getClockVisibility() {
        return mClockLayout.getVisibility() == View.VISIBLE;
    }

    private void setClockVisibility(boolean visible) {
        mClockLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void saveClockVisibility() {
        mPrefs.edit().putBoolean(PREF_SHOW_CLOCK, getClockVisibility()).commit();
    }
}
