package vnandroidapps.android.clock;

import vnandroidapps.android.clock.R;
import vnandroidapps.android.clock.utils.Background;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;

/**
 * Clock face picker for the Alarm Clock application.
 */
public class ClockPicker extends Activity implements
        AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private LayoutInflater mFactory;
    private Gallery mGallery;

    private SharedPreferences mPrefs;
    private View mClock;
    private ViewGroup mClockLayout;
    private int mPosition;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Background.setDefaultBackground(getWindow());

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mFactory = LayoutInflater.from(this);
        setContentView(R.layout.clockpicker);

        mGallery = (Gallery) findViewById(R.id.gallery);
        mGallery.setAdapter(new ClockAdapter());
        mGallery.setOnItemSelectedListener(this);
        mGallery.setOnItemClickListener(this);

        mPrefs = getSharedPreferences(CoolAlarm.PREFERENCES, 0);
        int face = mPrefs.getInt(CoolAlarm.PREF_CLOCK_FACE, 0);
        if (face < 0 || face >= CoolAlarm.CLOCKS.length) face = 0;

        mClockLayout = (ViewGroup) findViewById(R.id.clock_layout);
        mClockLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selectClock(mPosition);
                }
        });

        mGallery.setSelection(face, false);
    }

    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        if (mClock != null) {
            mClockLayout.removeView(mClock);
        }
        mClock = mFactory.inflate(CoolAlarm.CLOCKS[position], null);
        mClockLayout.addView(mClock, 0);
        mPosition = position;
    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        selectClock(position);
    }

    private synchronized void selectClock(int position) {
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("face", position);
        ed.commit();

        setResult(RESULT_OK);
        finish();
    }

    public void onNothingSelected(AdapterView parent) {
    }

    class ClockAdapter extends BaseAdapter {

        public ClockAdapter() {
        }

        public int getCount() {
            return CoolAlarm.CLOCKS.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View clock = mFactory.inflate(CoolAlarm.CLOCKS[position], null);
            return clock;
        }

    }
}
