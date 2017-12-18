package bag.small.ui.activity;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.BaseBean;
import bag.small.entity.IMLoginEntity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.rx.RxUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2017/7/22.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_login_image)
    ImageView loginImage;
    @BindView(R.id.activity_login_user_name_edt)
    EditText loginUserNameEdt;
    @BindView(R.id.activity_login_user_password_edt)
    EditText loginUserPasswordEdt;
    @BindView(R.id.activity_login_commit_btn)
    Button loginCommitBtn;
    @BindView(R.id.activity_login_register_tv)
    TextView loginRegisterTv;
    @BindView(R.id.activity_login_forget_password_tv)
    TextView loginForgetPasswordTv;

    ILoginRequest iLoginRequest;

    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    public void initView() {
        toolbar.setVisibility(View.GONE);
        //setToolTitle("登录", false);

        iLoginRequest = HttpUtil.getInstance().createApi(ILoginRequest.class);
        loginUserPasswordEdt.setTypeface(loginUserNameEdt.getTypeface());
        RxBus.get().register(this);

    }

    @OnClick({R.id.activity_login_commit_btn,
            R.id.activity_login_register_tv,
            R.id.activity_login_forget_password_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_commit_btn:
                String phone = StringUtil.EditGetString(loginUserNameEdt);
                String password = StringUtil.EditGetString(loginUserPasswordEdt);
                goLogin(phone, password);
                break;
            case R.id.activity_login_register_tv:
                goActivity(RegisterActivity.class);
                break;
            case R.id.activity_login_forget_password_tv:
                goActivity(PasswordForgetActivity.class);
                break;
        }
    }

    private void goLogin(String phone, final String password) {
        iLoginRequest.appLogin(phone, password)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .filter(bean -> {
                    if (!bean.isSuccess()) {
                        toast(bean.getMsg());
                    }
                    return bean.isSuccess();
                })
                .flatMap(bean -> {
                    toast(bean.getMsg());
                    MyApplication.isLogin = true;
                    MyApplication.loginResults.clear();
                    MyApplication.loginResults.addAll(bean.getData().getRole());
                    LoginResult.RoleBean mBean = bean.getData().getRole().get(0);
                    mBean.setSelected(true);
                    UserPreferUtil.getInstanse().setPhone(phone);
                    UserPreferUtil.getInstanse().setPassword(password);
                    UserPreferUtil.getInstanse().setUserInfomation(mBean);
                    UserPreferUtil.getInstanse().setUseId(bean.getData().getLogin_id());
                    return iLoginRequest.loginIM(mBean.getRole_id(), bean.getData().getLogin_id(), mBean.getSchool_id());
                })
                .filter(bean -> {
                    if (!bean.isSuccess()) {
                        toast(bean.getMsg());
                    }
                    return bean.isSuccess();
                })
                .subscribe(beans -> {
                    IMLoginEntity entity = beans.getData();
                    EMClient.getInstance().login(entity.getUsername(), entity.getPwd(),
                            new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    LogUtil.show("登录聊天服务器成功！");
                                    skipActivity(MainActivity.class);
                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                }

                                @Override
                                public void onError(int code, String message) {
                                    toast("登录聊天服务器失败！");
                                    LogUtil.show("登录聊天服务器失败！");
                                    skipActivity(MainActivity.class);
                                }
                            });
                }, new HttpError());
    }

    @MySubscribe(code = 111)
    public void loginFinish() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
