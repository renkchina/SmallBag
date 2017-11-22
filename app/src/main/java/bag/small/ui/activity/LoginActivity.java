package bag.small.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.rx.RxUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

/**
 * Created by Administrator on 2017/7/22.
 */

public class LoginActivity extends BaseActivity {
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
        setToolTitle("登录", false);
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
                .subscribe(bean -> {
                    toast(bean.getMsg());
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
                        skipActivity(MainActivity.class);
                    } else {
                        toast("登录失败！");
                    }
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
