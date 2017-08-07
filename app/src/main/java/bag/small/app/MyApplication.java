package bag.small.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.game.UMGameAgent;

import java.util.List;

import bag.small.R;
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
        setDefaultRefresh();
    }

    private void setDefaultRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            BezierRadarHeader header = new BezierRadarHeader(context);
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            return header;
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            ClassicsFooter footer = new ClassicsFooter(context);
            return footer;//指定为经典Footer，默认是 BallPulseFooter
        });
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.show(activity.getLocalClassName() + " onCreate");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.show(activity.getLocalClassName() + " onStart");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.show(activity.getLocalClassName() + " onResume");
        UMGameAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.show(activity.getLocalClassName() + " onPause");
        UMGameAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.show(activity.getLocalClassName() + " onStop");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.show(activity.getLocalClassName() + " onDestroy");
    }
}
