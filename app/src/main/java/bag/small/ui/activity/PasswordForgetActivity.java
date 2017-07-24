package bag.small.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bag.small.R;
import bag.small.base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordForgetActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.activity_pwd_forget_phone_edt)
    EditText activityPwdForgetPhoneEdt;
    @Bind(R.id.activity_pwd_forget_verification_code_edt)
    EditText activityPwdForgetVerificationCodeEdt;
    @Bind(R.id.activity_pwd_forget_send_code_btn)
    Button activityPwdForgetSendCodeBtn;
    @Bind(R.id.activity_pwd_forget_password_edt)
    EditText activityPwdForgetPasswordEdt;
    @Bind(R.id.activity_pwd_forget_reset_password_edt)
    EditText activityPwdForgetResetPasswordEdt;
    @Bind(R.id.activity_pwd_forget_commit_btn)
    Button activityPwdForgetCommitBtn;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_password_forget;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.activity_pwd_forget_send_code_btn, R.id.activity_pwd_forget_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_pwd_forget_send_code_btn:
                break;
            case R.id.activity_pwd_forget_commit_btn:
                break;
        }
    }
}
