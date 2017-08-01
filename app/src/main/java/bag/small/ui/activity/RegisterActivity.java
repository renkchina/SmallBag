package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.BaseBean;
import bag.small.entity.RegisterInfoBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.HttpResultFilter;
import bag.small.http.IApi.IRegisterReq;
import bag.small.http.IApi.IRegisterSendCode;
import bag.small.rx.RxCountDown;
import bag.small.rx.RxUtil;
import bag.small.utils.GlobalValues;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.ac_register_parent_ll)
    LinearLayout rParentLl;
    @Bind(R.id.ac_register_teacher_ll)
    LinearLayout rTeacherLl;
    @Bind(R.id.activity_register_login_tv)
    TextView LoginTv;

    IRegisterSendCode iRegisterSendCode;
    private IRegisterReq iRegisterReq;

    private List<RegisterInfoBean.SchoolBeanX> areaLists;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setToolTitle("注册", true);
        iRegisterSendCode = HttpUtil.getInstance().createApi(IRegisterSendCode.class);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        getRegisterInfo();
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
                skipActivity(TeacherInformationActivity.class);
                break;
            case R.id.activity_register_login_tv:
                skipActivity(LoginActivity.class);
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
        }
        requestRegister(i, phone, password, code);
    }

    private void getCodeByPhone(String phone) {
        iRegisterSendCode.sendCodeRequest(phone)
                //1.线程切换的封装
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                //2.当前Activity onStop时自动取消请求
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .filter(new HttpResultFilter<>())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        RxCountDown.TimerDown(GlobalValues.COUNT_DOWN_TIME, rSendCodeBtn);
                        toast(bean.getData());
                    }
                }, new HttpError());
    }

    private void requestRegister(int type, String phone, String password, String verify) {
        iRegisterReq.goRegister(phone, password, verify)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
//                .filter(new HttpResultFilter<>())
                .subscribe(bean -> {
                    LogUtil.show(bean);
                    if (bean.isSuccess()) {
                        UserPreferUtil.getInstanse().setUseId(bean.getData().getLogin_id());
                        if (type == 1) {
                            skipActivity(ParentInformationActivity.class);
                        } else {
                            skipActivity(TeacherInformationActivity.class);
                        }
                    } else {
                    }

                }, new HttpError());
    }

    private void getRegisterInfo() {
        iRegisterReq.getRegisterInfo()
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        LogUtil.show(bean);
                        areaLists = getArea(bean.getData());
                    }
                }, new HttpError());
    }

    private List<RegisterInfoBean.SchoolBeanX> getArea(RegisterInfoBean bean) {
        List<RegisterInfoBean.SchoolBeanX> lists = new ArrayList<>();
        if (bean != null && ListUtil.unEmpty(bean.getSchool())) {
            int size = bean.getSchool().size();
            for (int i = 0; i < size; i++) {
                RegisterInfoBean.SchoolBeanX info = bean.getSchool().get(i);
                lists.add(info);
            }
        }
        return lists;
    }

//    private void  get
}
