package bag.small.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.http.IApi.IRegisterSendCode;
import bag.small.rx.RxCountDown;
import bag.small.rx.RxUtil;
import bag.small.utils.GlobalValues;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

/**
 * Created by Administrator on 2017/7/23.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.activity_register_phone_edt)
    EditText rPhoneEdt;
    @BindView(R.id.activity_register_verification_code_edt)
    EditText rVerificationCodeEdt;
    @BindView(R.id.activity_register_send_code_btn)
    Button rSendCodeBtn;
    @BindView(R.id.activity_register_password_edt)
    EditText rPasswordEdt;
    @BindView(R.id.activity_register_reset_password_edt)
    EditText rResetPasswordEdt;
    @BindView(R.id.ac_register_parent_ll)
    Button rParentLl;
    @BindView(R.id.ac_register_teacher_ll)
    Button rTeacherLl;
    @BindView(R.id.activity_register_login_tv)
    TextView LoginTv;

    IRegisterSendCode iRegisterSendCode;
    private IRegisterReq iRegisterReq;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setToolTitle("注册", true);
        iRegisterSendCode = HttpUtil.getInstance().createApi(IRegisterSendCode.class);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
    }

    @OnClick({R.id.activity_register_send_code_btn,
            R.id.ac_register_parent_ll,
            R.id.ac_register_teacher_ll,
            R.id.activity_register_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_register_send_code_btn:
                String phone = StringUtil.EditGetString(rPhoneEdt);
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入手机号");
                } else {
                    getCodeByPhone(phone);
                }
                break;
            case R.id.ac_register_parent_ll:
                registerPhone(1);
                break;
            case R.id.ac_register_teacher_ll:
                registerPhone(2);
                break;
            case R.id.activity_register_login_tv:
                finish();
                break;
        }
    }

    private void registerPhone(int i) {
        String phone = StringUtil.EditGetString(rPhoneEdt);
        String password = StringUtil.EditGetString(rPasswordEdt);
        String rePassword = StringUtil.EditGetString(rResetPasswordEdt);
        String code = StringUtil.EditGetString(rVerificationCodeEdt);
        if (!password.equals(rePassword)) {
            toast("密码不一致");
            return;
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
            toast("请输入内容");
            return;
        }
        requestRegister(i, phone, password, code);
    }

    private void getCodeByPhone(String phone) {
        iRegisterSendCode.sendCodeRequest(phone)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        RxCountDown.TimerDown(GlobalValues.COUNT_DOWN_TIME, rSendCodeBtn);
                        toast(bean.getMsg());
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void requestRegister(int type, String phone, String password, String verify) {
        iRegisterReq.goRegister(phone, password, verify)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
//                    toast(bean.getMsg());
                    if (bean.isSuccess() && bean.getData() != null) {
                        String loginId = bean.getData().getLogin_id();
                        if (type == 1) {
                            goStudent(loginId, phone);
                        } else {
                            goTeacher(loginId, phone);
                        }
                    } else {
                        toast("注册失败");
                    }

                }, new HttpError());
    }

    private void goStudent(String loginId, String phone) {
        iRegisterReq.getNewStudentRegisterInfo(loginId, phone)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("loginId", loginId);
                        bundle.putString("phone", phone);
                        goActivity(NewRegisterStudentActivity.class, bundle);
                        finish();
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void goTeacher(String loginId, String phone) {
        iRegisterReq.getNewTeacherRegisterInfo(loginId, phone)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("loginId", loginId);
                        bundle.putString("phone", phone);
                        goActivity(NewRegisterTeacherActivity.class, bundle);
                        finish();
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

}
