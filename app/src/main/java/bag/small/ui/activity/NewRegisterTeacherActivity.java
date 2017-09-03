package bag.small.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.LoginResult;
import bag.small.entity.NewRegisterTeacherBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class NewRegisterTeacherActivity extends BaseActivity {

    @Bind(R.id.ac_new_teacher_head_iv)
    ImageView acNewTeacherHeadIv;
    @Bind(R.id.new_register_name_tv)
    TextView newRegisterNameTv;
    @Bind(R.id.new_register_type_tv)
    TextView newRegisterTypeTv;
    @Bind(R.id.new_register_school_tv)
    TextView newRegisterSchoolTv;
    @Bind(R.id.new_register_number_tv)
    TextView newRegisterNumberTv;
    @Bind(R.id.new_register_email_tv)
    TextView newRegisterEmailTv;
    @Bind(R.id.new_register_birthday_tv)
    TextView newRegisterBirthdayTv;
    @Bind(R.id.new_register_gender_tv)
    TextView newRegisterGenderTv;
    @Bind(R.id.new_register_class_teacher_tv)
    TextView newRegisterClassTeacherTv;
    @Bind(R.id.new_register_class_ll)
    LinearLayout newRegisterClassLl;

    IRegisterReq iRegisterReq;
    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private String phone;
    private String loginId;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_register_teacher;
    }

    @Override
    public void initData() {
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
        setToolTitle("注册", true);
        toolbarRightTv.setText("完成");
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        acNewTeacherHeadIv.setBackgroundResource(R.mipmap.teacher_man);
        getRegisterInfomation();
    }

    private void getRegisterInfomation() {
        iRegisterReq.getNewTeacherRegisterInfo(loginId, phone)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        showView(bean.getData());
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void showView(NewRegisterTeacherBean data) {
        if (data.getSex() != null && data.getSex().contains("男")) {
            acNewTeacherHeadIv.setBackgroundResource(R.mipmap.teacher_man);
        } else {
            acNewTeacherHeadIv.setBackgroundResource(R.mipmap.teacher_woman);
        }
        StringUtil.setTextView(newRegisterNameTv, "名字：" + data.getName());
        StringUtil.setTextView(newRegisterTypeTv, "类型：" + data.getLevel());
        StringUtil.setTextView(newRegisterSchoolTv, "学校：" + data.getSchool_name());
        StringUtil.setTextView(newRegisterNumberTv, "工号：" + data.getWork_no());
        StringUtil.setTextView(newRegisterEmailTv, "邮箱：" + data.getEmail());
        StringUtil.setTextView(newRegisterBirthdayTv, "生日：" + data.getBirth());
        StringUtil.setTextView(newRegisterGenderTv, "性别：" + data.getSex());
        StringUtil.setTextView(newRegisterClassTeacherTv, "班主任：" + data.getIs_banzhuren());
        if (ListUtil.unEmpty(data.getKemuinfo())) {
            int size = data.getKemuinfo().size();
            for (int i = 0; i < size; i++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(14);
                textView.setText(data.getKemuinfo().get(i));
                newRegisterClassLl.addView(textView);
            }
        }
    }


    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        iRegisterReq.getNewRegisterInfo(loginId, phone)
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
                        skipActivity(MainActivity.class);
                    }
                }, new HttpError());
    }
}
