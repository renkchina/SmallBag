package bag.small.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.rx.RxUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

/**
 * Created by Administrator on 2017/7/22.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.activity_login_image)
    ImageView loginImage;
    @Bind(R.id.activity_login_user_name_edt)
    EditText loginUserNameEdt;
    @Bind(R.id.activity_login_user_password_edt)
    EditText loginUserPasswordEdt;
    @Bind(R.id.activity_login_commit_btn)
    Button loginCommitBtn;
    @Bind(R.id.activity_login_register_tv)
    TextView loginRegisterTv;
    @Bind(R.id.activity_login_forget_password_tv)
    TextView loginForgetPasswordTv;

    ILoginRequest iLoginRequest;

    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    public void initView() {
        setToolTitle("登录", false);
        iLoginRequest = HttpUtil.getInstance().createApi(ILoginRequest.class);
        loginUserPasswordEdt.setTypeface(loginUserNameEdt.getTypeface());

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
                        MyApplication.loginResults = bean.getData().getRole();
                        UserPreferUtil.getInstanse().setUserInfomation(bean.getData().getRole().get(0));
                        UserPreferUtil.getInstanse().setUseId(bean.getData().getLogin_id());
                        skipActivity(MainActivity.class);
                    } else {
                        toast("登录失败！");
                    }
                }, new HttpError());

    }


}
