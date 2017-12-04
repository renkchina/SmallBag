package bag.small.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
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

    public static int bannerImage = R.mipmap.banner_icon2;
    public static int roleImage = R.mipmap.teacher_head;

    public static String deviceToken;

    public static void setDeviceToken(String deviceToken) {
        MyApplication.deviceToken = deviceToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerActivityLifecycleCallbacks(this);
        setDefaultRefresh();
        loginResults = new ArrayList<>(5);
    }


    private void setPushInit(){
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                setDeviceToken(deviceToken);
                LogUtil.loge(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                setDeviceToken("");
                LogUtil.loge(s+"  "+s1);
            }
        });
        mPushAgent.setDebugMode(false);
    }


    private void setDefaultRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            return new BezierRadarHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
        });
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.loge(activity.getLocalClassName() + " onCreate");
//        PushAgent.getInstance(activity).onAppStart();
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.loge(activity.getLocalClassName() + " onStart");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.loge(activity.getLocalClassName() + " onResume");
//        UMGameAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.loge(activity.getLocalClassName() + " onPause");
//        UMGameAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.loge(activity.getLocalClassName() + " onStop");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.loge(activity.getLocalClassName() + " onDestroy");
    }
}
