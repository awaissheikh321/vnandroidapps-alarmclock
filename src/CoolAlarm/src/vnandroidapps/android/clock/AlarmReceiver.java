package vnandroidapps.android.clock;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
/**
 * Glue class: connects AlarmAlert IntentReceiver to AlarmAlert
 * activity.  Passes through Alarm ID.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /** If the alarm is older than STALE_WINDOW seconds, ignore.  It
        is probably the result of a time or timezone change */
    private final static int STALE_WINDOW = 60 * 30;

    @Override
    public void onReceive(Context context, Intent intent) {
        long now = System.currentTimeMillis();
        int id = intent.getIntExtra(Alarms.ID, 0);
        long setFor = intent.getLongExtra(Alarms.TIME, 0);

        if (now > setFor + STALE_WINDOW * 1000) {
            if (Log.LOGV) Log.v("AlarmReceiver ignoring stale alarm intent id"
                                + id + " setFor " + setFor + " now " + now);
            return;
        }

        if (Log.LOGV) Log.v("*********** Keep UI Alive *************");
        AlarmAlertWakeLock.acquire(context);

        Intent fireAlarm = new Intent(context, AlarmAlert.class);

        fireAlarm.putExtra(Alarms.ID, id);
        fireAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(fireAlarm);
   }
}
