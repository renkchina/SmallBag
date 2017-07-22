package bag.small.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import bag.small.utils.LogUtil;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
       registerActivityLifecycleCallbacks(this);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.show(activity.getLocalClassName()+"onCreate");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+"onStart");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+"onResume");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+"onPause");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+"onStop");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+"onDestroy");
    }
}
