package bag.small.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.HttpResultFilter;
import bag.small.http.IApi.IRegisterSendCode;
import bag.small.rx.RxCountDown;
import bag.small.rx.RxUtil;
import bag.small.utils.GlobalValues;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

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

    IRegisterSendCode iRegisterSendCode;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        iRegisterSendCode = HttpUtil.getInstance().createApi(IRegisterSendCode.class);
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

    private void getCodeByPhone(String phone) {
        iRegisterSendCode.sendCodeRequest(phone)
                //1.线程切换的封装
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                //2.当前Activity onStop时自动取消请求
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .filter(new HttpResultFilter<>())
                .subscribe(bean -> {
                    if (!TextUtils.isEmpty(bean.getData())) {
                        RxCountDown.TimerDown(GlobalValues.COUNT_DOWN_TIME, rSendCodeBtn);
                    }
                }, new HttpError());
    }
}
