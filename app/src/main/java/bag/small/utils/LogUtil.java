package bag.small.utils;

import android.util.Log;

import com.edriving.BuildConfig;

/**
 * Created by Administrator on 2017/4/14.
 */

public class LogUtil {
    static final String TAG = "*RENK";

    private LogUtil() {
    }

    public static void show(Object object) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        StackTraceElement trace = new Throwable().getStackTrace()[1];
        String name = trace.getClassName();
        int line = trace.getLineNumber();
        Log.e(TAG, "***********************************************************");
        Log.e(TAG, "Clazz -> " + name + ". Line -> " + line);
        Log.e(TAG, "-----------------------------------------------------------");
        if (object == null) {
            Log.e(TAG, "is Null");
        } else {
            Log.e(TAG, object.toString());
        }
        Log.e(TAG, "***********************************************************");
    }
    public static void loge(Object object){
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (object == null) {
            Log.e(TAG, "is Null");
        } else {
            Log.e(TAG, object.toString());
        }
    }
}
