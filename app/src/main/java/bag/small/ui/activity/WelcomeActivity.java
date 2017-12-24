package bag.small.ui.activity;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.caimuhao.rxpicker.RxPicker;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.IMLoginEntity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {

    ILoginRequest iLoginRequest;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wellcome;
    }

    @Override
    public void initView() {
        RxPicker.init(new GlideImageLoader());
        iLoginRequest = HttpUtil.getInstance().createApi(ILoginRequest.class);
        String phone = UserPreferUtil.getInstanse().getPhone();
        String password = UserPreferUtil.getInstanse().getPassword();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            skipActivity(LoginActivity.class);
        } else {
            iLoginRequest.appLogin(phone, password)
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .flatMap(bean -> {
                        if (bean.isSuccess()) {
                            MyApplication.isLogin = true;
                            MyApplication.loginResults.clear();
                            MyApplication.loginResults.addAll(bean.getData().getRole());
                            LoginResult.RoleBean mBean = bean.getData().getRole().get(0);
                            mBean.setSelected(true);
                            UserPreferUtil.getInstanse().setPhone(phone);
                            UserPreferUtil.getInstanse().setPassword(password);
                            UserPreferUtil.getInstanse().setUserInfomation(mBean);
                            UserPreferUtil.getInstanse().setUseId(bean.getData().getLogin_id());
                            return iLoginRequest.loginIM(mBean.getRole_id(), bean.getData().getLogin_id(), mBean.getSchool_id()).subscribeOn(Schedulers.io());
                        } else {
                            toast(bean.getMsg());
                            return Observable.error(new Throwable());
                        }

                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(beans -> {
                        IMLoginEntity entity = beans.getData();
                        EMClient.getInstance().login(entity.getUsername(), entity.getPwd(),
                                new EMCallBack() {//回调
                                    @Override
                                    public void onSuccess() {
                                        EMClient.getInstance().groupManager().loadAllGroups();
                                        EMClient.getInstance().chatManager().loadAllConversations();
                                        UserPreferUtil.getInstanse().setUseChatId(entity.getUsername());
                                        LogUtil.show("登录聊天服务器成功！");
                                        skipActivity(MainActivity.class);
                                    }

                                    @Override
                                    public void onProgress(int progress, String status) {
                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        new Handler(Looper.getMainLooper()).post(() -> toast("登录聊天服务器失败！"));
                                        LogUtil.show("登录聊天服务器失败！");
                                        skipActivity(MainActivity.class);
                                    }
                                });
                    }, throwable -> skipActivity(LoginActivity.class));
        }
    }
}
