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

/**
 * Created by Administrator on 2017/7/23.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.activity_register_phone_edt)
    EditText rPhoneEdt;
    @Bind(R.id.activity_register_verification_code_edt)
    EditText rVerificationCodeEdt;
    @Bind(R.id.activity_register_send_code_btn)
    Button rSendCodeBtn;
    @Bind(R.id.activity_register_password_edt)
    EditText rPasswordEdt;
    @Bind(R.id.activity_register_reset_password_edt)
    EditText rResetPasswordEdt;
    @Bind(R.id.activity_register_commit_parent_btn)
    Button rParentBtn;
    @Bind(R.id.activity_register_commit_teacher_btn)
    Button rTeacherBtn;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.activity_register_send_code_btn,
            R.id.activity_register_commit_parent_btn,
            R.id.activity_register_commit_teacher_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_register_send_code_btn:
                break;
            case R.id.activity_register_commit_parent_btn:
                skipActivity(ParentInformationActivity.class);
                break;
            case R.id.activity_register_commit_teacher_btn:
                skipActivity(TeacherInformationActivity.class);
                break;
        }
    }
}
