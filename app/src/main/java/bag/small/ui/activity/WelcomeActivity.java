package bag.small.ui.activity;

import android.text.TextUtils;
import android.util.Log;

import com.caimuhao.rxpicker.RxPicker;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.ILoginRequest;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.UserPreferUtil;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class WelcomeActivity extends BaseActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.activity_wellcome;
    }

    @Override
    public void initView() {
        RxPicker.init(new GlideImageLoader());
        String phone = UserPreferUtil.getInstanse().getPhone();
        String password = UserPreferUtil.getInstanse().getPassword();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            skipActivity(LoginActivity.class);
        } else {
            HttpUtil.getInstance().createApi(ILoginRequest.class).appLogin(phone, password)
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .subscribe(bean -> {
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
//                            EMClient.getInstance().login("","",new EMCallBack() {//回调
//                                @Override
//                                public void onSuccess() {
//                                    EMClient.getInstance().groupManager().loadAllGroups();
//                                    EMClient.getInstance().chatManager().loadAllConversations();
//                                    Log.e("main", "登录聊天服务器成功！");
//                                    skipActivity(MainActivity.class);
//                                }
//
//                                @Override
//                                public void onProgress(int progress, String status) {
//
//                                }
//
//                                @Override
//                                public void onError(int code, String message) {
//                                    Log.e("main", "登录聊天服务器失败！");
//                                    skipActivity(LoginActivity.class);
//                                }
//                            });
                            skipActivity(MainActivity.class);
                        } else {
                            skipActivity(LoginActivity.class);
                        }
                    }, throwable -> skipActivity(LoginActivity.class));
        }
    }
}
