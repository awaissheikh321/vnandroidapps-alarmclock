package vnandroidapps.android.clock;

import android.os.SystemClock;
import android.util.Config;

class Log {
    public final static String LOGTAG = "AlarmClock";

    private static final boolean DEBUG = false;
    static final boolean LOGV = DEBUG ? Config.LOGD : Config.LOGV;

    static void v(String logMe) {
        android.util.Log.v(LOGTAG, /* SystemClock.uptimeMillis() + " " + */ logMe);
    }

    static void e(String logMe) {
        android.util.Log.e(LOGTAG, logMe);
    }
}
