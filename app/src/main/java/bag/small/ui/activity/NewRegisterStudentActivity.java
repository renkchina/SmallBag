package bag.small.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.LoginResult;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.rx.RxUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class NewRegisterStudentActivity extends BaseActivity {

    @Bind(R.id.ac_new_student_head_iv)
    ImageView acNewStudentHeadIv;
    @Bind(R.id.new_register_student_name_tv)
    TextView studentNameTv;
    @Bind(R.id.new_register_student_type_tv)
    TextView studentTypeTv;
    @Bind(R.id.new_register_student_school_tv)
    TextView studentSchoolTv;
    @Bind(R.id.new_register_student_number_tv)
    TextView studentNumberTv;
    @Bind(R.id.new_register_student_class_tv)
    TextView studentClassTv;
    @Bind(R.id.new_register_student_class_student_tv)
    TextView studentxuehaoTv;
    @Bind(R.id.new_register_student_birthday_tv)
    TextView studentBirthdayTv;
    @Bind(R.id.new_register_student_gender_tv)
    TextView studentGenderTv;
    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private IRegisterReq iRegisterRequest;
    private String phone;
    private String loginId;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_register_student;
    }

    @Override
    public void initData() {
        setToolTitle("注册", true);
        toolbarRightTv.setText("完成");
        iRegisterRequest = HttpUtil.getInstance().createApi(IRegisterReq.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phone = bundle.getString("phone");
            loginId = bundle.getString("loginId");
        } else {
            phone = UserPreferUtil.getInstanse().getPhone();
            loginId = UserPreferUtil.getInstanse().getUserId();
        }
    }

    @Override
    public void initView() {
        acNewStudentHeadIv.setBackgroundResource(R.mipmap.student_boy);
        iRegisterRequest.getNewStudentRegisterInfo(loginId, phone)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        if (bean.getData().getSex() != null && bean.getData().getSex().contains("男")) {
                            acNewStudentHeadIv.setBackgroundResource(R.mipmap.student_boy);
                        } else {
                            acNewStudentHeadIv.setBackgroundResource(R.mipmap.student_girl);
                        }
                        StringUtil.setTextView(studentNameTv, "姓名：" + bean.getData().getName());
                        StringUtil.setTextView(studentTypeTv, "类型：学生");
                        StringUtil.setTextView(studentSchoolTv, "学校：" + bean.getData().getSchool_name());
                        StringUtil.setTextView(studentNumberTv, "年级：" + bean.getData().getBanci());
                        StringUtil.setTextView(studentClassTv, "班级：" + bean.getData().getBanji());
                        StringUtil.setTextView(studentxuehaoTv, "学号：" + bean.getData().getStudent_no());
                        StringUtil.setTextView(studentBirthdayTv, "生日：" + bean.getData().getBirth());
                        StringUtil.setTextView(studentGenderTv, "性别：" + bean.getData().getSex());
                    } else {
                        try {
                            toast(bean.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new HttpError());
    }


    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        iRegisterRequest.getNewRegisterInfo(loginId, phone)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        MyApplication.isLogin = true;
                        MyApplication.loginResults = bean.getData().getRole();
                        LoginResult.RoleBean mBean = bean.getData().getRole().get(0);
                        mBean.setSelected(true);
                        UserPreferUtil.getInstanse().setUserInfomation(mBean);
                        UserPreferUtil.getInstanse().setUseId(bean.getData().getLogin_id());

                        RxBus.get().send(111);
                        skipActivity(MainActivity.class);
                    }
                }, new HttpError());
    }


}
