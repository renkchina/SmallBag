package bag.small.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import bag.small.R;
import bag.small.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

public class PasswordForgetActivity extends BaseActivity {

 
    @Bind(R.id.activity_pwd_forget_phone_edt)
    EditText pfPhoneEdt;
    @Bind(R.id.activity_pwd_forget_verification_code_edt)
    EditText pfVerificationCodeEdt;
    @Bind(R.id.activity_pwd_forget_send_code_btn)
    Button pfSendCodeBtn;
    @Bind(R.id.activity_pwd_forget_password_edt)
    EditText pfPasswordEdt;
    @Bind(R.id.activity_pwd_forget_reset_password_edt)
    EditText pfResetPasswordEdt;
    @Bind(R.id.activity_pwd_forget_commit_btn)
    Button pfCommitBtn;

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
