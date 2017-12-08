package bag.small.utils;

import android.support.compat.BuildConfig;
import android.util.Log;


/**
 * Created by Administrator on 2017/4/14.
 */

public class LogUtil {
    private static final String TAG = "*RENK";

    private LogUtil() {
    }

    public static void show(Object object) {
//        if (BuildConfig.DEBUG) {
//            return;
//        }
        StackTraceElement trace = new Throwable().getStackTrace()[1];
        String name = trace.getClassName();
        int line = trace.getLineNumber();
        Log.e(TAG, "<<***********************************************************");
        Log.e(TAG, "Clazz -> " + name + ". Line -> " + line);
        if (object == null) {
            Log.e(TAG, "is Null");
        } else {
            String msg = object.toString();
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(TAG, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(TAG, logContent);
                }
                Log.e(TAG, msg);// 打印剩余日志
            }
        }
        Log.e(TAG, "***********************************************************>>");
    }

    public static void loge(Object object) {
        if (BuildConfig.DEBUG) {
            return;
        }
        if (object == null) {
            Log.e(TAG, "is Null");
        } else {
            String msg = object.toString();
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(TAG, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(TAG, logContent);
                }
                Log.e(TAG, msg);// 打印剩余日志
            }
        }
    }


}
