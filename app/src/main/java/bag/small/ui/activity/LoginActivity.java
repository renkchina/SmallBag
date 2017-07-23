package bag.small.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import bag.small.R;
import bag.small.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

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

    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    public void initView() {
        setToolTitle("登录", false);
    }

    @OnClick({R.id.activity_login_commit_btn,
            R.id.activity_login_register_tv,
            R.id.activity_login_forget_password_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_commit_btn:
                skipActivity(MainActivity.class);
                break;
            case R.id.activity_login_register_tv:
                goActivity(RegisterActivity.class);
                break;
            case R.id.activity_login_forget_password_tv:
                break;
        }
    }
}
