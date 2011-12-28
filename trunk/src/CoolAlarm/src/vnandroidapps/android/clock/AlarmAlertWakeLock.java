package vnandroidapps.android.clock;

import android.content.Context;
import android.os.PowerManager;

/**
 * Hold a wakelock that can be acquired in the AlarmReceiver and
 * released in the AlarmAlert activity
 */
class AlarmAlertWakeLock {

    private static PowerManager.WakeLock sWakeLock;

    static void acquire(Context context) {
        if (sWakeLock != null) {
            sWakeLock.release();
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sWakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, Log.LOGTAG);
        sWakeLock.acquire();
    }

    static void release() {
        if (Log.LOGV) Log.v("AlarmAlertWakeLock release");
        if (sWakeLock != null) {
            sWakeLock.release();
            sWakeLock = null;
        }
    }
}
