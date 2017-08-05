package bag.small.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.umeng.analytics.game.UMGameAgent;

import java.util.List;

import bag.small.entity.LoginResult;
import bag.small.utils.LogUtil;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    static Context mContext;
    public static boolean isLogin;
    public static List<LoginResult.RoleBean> loginResults;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
       registerActivityLifecycleCallbacks(this);
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.show(activity.getLocalClassName()+" onCreate");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+" onStart");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+" onResume");
        UMGameAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+" onPause");
        UMGameAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+" onStop");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.show(activity.getLocalClassName()+" onDestroy");
    }
}
