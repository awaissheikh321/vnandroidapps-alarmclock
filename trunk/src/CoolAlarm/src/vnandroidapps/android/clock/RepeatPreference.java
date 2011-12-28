package vnandroidapps.android.clock;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class RepeatPreference extends ListPreference {
    private Alarms.DaysOfWeek mDaysOfWeek = new Alarms.DaysOfWeek();
    private OnRepeatChangeListener mOnRepeatChangeListener;

    public interface OnRepeatChangeListener {
        /** Called when this preference has changed */
        public void onRepeatChanged(Alarms.DaysOfWeek daysOfWeek);
    }

    public RepeatPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setDaysOfWeek(Alarms.DaysOfWeek daysOfWeek) {
        /* we keep a local copy, so the host can set itself on a
           positive result and ignore on a negative */
        mDaysOfWeek.set(daysOfWeek);
    }

    void setOnRepeatChangeListener(OnRepeatChangeListener listener) {
        mOnRepeatChangeListener = listener;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mOnRepeatChangeListener.onRepeatChanged(mDaysOfWeek);
        }
    }

    @Override
    protected void onPrepareDialogBuilder(Builder builder) {
        CharSequence[] entries = getEntries();
        CharSequence[] entryValues = getEntryValues();

        if (entries == null || entryValues == null) {
            throw new IllegalStateException(
                    "RepeatPreference requires an entries array and an entryValues array.");
        }
        builder.setMultiChoiceItems(
                entries, mDaysOfWeek.getBooleanArray(),
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mDaysOfWeek.set(which, isChecked);
                    }
                });
    }
}

