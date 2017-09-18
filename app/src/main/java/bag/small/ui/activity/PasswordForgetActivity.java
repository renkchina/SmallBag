package bag.small.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.http.IApi.IRegisterSendCode;
import bag.small.rx.RxCountDown;
import bag.small.rx.RxUtil;
import bag.small.utils.GlobalValues;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class PasswordForgetActivity extends BaseActivity {


    @BindView(R.id.activity_pwd_forget_phone_edt)
    EditText pfPhoneEdt;
    @BindView(R.id.activity_pwd_forget_verification_code_edt)
    EditText pfVerificationCodeEdt;
    @BindView(R.id.activity_pwd_forget_send_code_btn)
    Button pfSendCodeBtn;
    @BindView(R.id.activity_pwd_forget_password_edt)
    EditText pfPasswordEdt;
    @BindView(R.id.activity_pwd_forget_reset_password_edt)
    EditText pfResetPasswordEdt;
    @BindView(R.id.activity_pwd_forget_commit_btn)
    Button pfCommitBtn;
    IRegisterSendCode iRegisterSendCode;
    IRegisterReq iRegisterReq;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_password_forget;
    }

    @Override
    public void initView() {
        setToolTitle("忘记密码", true);
        iRegisterSendCode = HttpUtil.getInstance().createApi(IRegisterSendCode.class);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
    }


    @OnClick({R.id.activity_pwd_forget_send_code_btn, R.id.activity_pwd_forget_commit_btn})
    public void onViewClicked(View view) {
        String phone = StringUtil.EditGetString(pfPhoneEdt);
        switch (view.getId()) {
            case R.id.activity_pwd_forget_send_code_btn:
                iRegisterSendCode.sendCheckCodeRequest(phone)
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .compose(RxLifecycleCompact.bind(this).withObservable())
                        .subscribe(bean -> {
                            if (bean.isSuccess()) {
                                RxCountDown.TimerDown(GlobalValues.COUNT_DOWN_TIME, (Button) view);
                            }
                            try {
                                toast(bean.getMsg());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, new HttpError());

                break;
            case R.id.activity_pwd_forget_commit_btn:
                String code = StringUtil.EditGetString(pfVerificationCodeEdt);
                String password = StringUtil.EditGetString(pfPasswordEdt);
                String repassword = StringUtil.EditGetString(pfResetPasswordEdt);
                if (!password.equals(repassword)) {
                    toast("密码不一致");
                    return;
                }
                iRegisterReq.changePassword(phone, password, code)
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .compose(RxLifecycleCompact.bind(this).withObservable())
                        .subscribe(bean -> {
                            if (bean.isSuccess()) {
                                skipActivity(LoginActivity.class);
                            }
                            toast(bean.getMsg());
                        }, new HttpError());

                break;
        }
    }
}
