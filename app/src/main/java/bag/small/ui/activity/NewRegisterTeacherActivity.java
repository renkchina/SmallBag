package bag.small.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.china.rxbus.RxBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.entity.LoginResult;
import bag.small.entity.NewRegisterTeacherBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class NewRegisterTeacherActivity extends BaseActivity {

    //    @Bind(R.id.ac_new_teacher_head_iv)
//    ImageView acNewTeacherHeadIv;
//    @Bind(R.id.new_register_name_tv)
//    TextView newRegisterNameTv;
//    @Bind(R.id.new_register_type_tv)
//    TextView newRegisterTypeTv;
//    @Bind(R.id.new_register_school_tv)
//    TextView newRegisterSchoolTv;
//    @Bind(R.id.new_register_number_tv)
//    TextView newRegisterNumberTv;
//    @Bind(R.id.new_register_email_tv)
//    TextView newRegisterEmailTv;
//    @Bind(R.id.new_register_birthday_tv)
//    TextView newRegisterBirthdayTv;
//    @Bind(R.id.new_register_gender_tv)
//    TextView newRegisterGenderTv;
//    @Bind(R.id.new_register_class_teacher_tv)
//    TextView newRegisterClassTeacherTv;
//    @Bind(R.id.new_register_class_ll)
//    LinearLayout newRegisterClassLl;
    @Bind(R.id.ac_account_teacher_head_iv)
    ImageView acAccountTeacherHeadIv;
    @Bind(R.id.activity_account_teacher_name_tv)
    TextView accountTeacherNameTv;
    @Bind(R.id.activity_account_teacher_number_tv)
    EditText accountTeacherNumberTv;
    @Bind(R.id.activity_account_teacher_email_tv)
    EditText accountTeacherEmailTv;
    @Bind(R.id.activity_account_teacher_birthday_tv)
    TextView accountTeacherBirthdayTv;
    @Bind(R.id.activity_account_teacher_gender_tv)
    TextView accountTeacherGenderTv;
    @Bind(R.id.activity_account_teacher_class_teacher_tv)
    TextView accountTeacherClassTeacherTv;
    @Bind(R.id.activity_account_teacher_class_ll)
    LinearLayout accountTeacherClassLl;
    @Bind(R.id.activity_account_teacher_change_pwd_edt)
    EditText aChangePwdEdt;
    @Bind(R.id.activity_account_teacher_agree_pwd_edt)
    EditText aAgreePwdEdt;
    @Bind(R.id.activity_account_teacher_commit_btn)
    Button accountTeacherCommitBtn;
    @Bind(R.id.account_teacher_radio_button)
    RadioButton aRadioButton;
    @Bind(R.id.account_teacher_radio_ll)
    LinearLayout accountTeacherRadioLl;
    IRegisterReq iRegisterReq;
    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private String phone;
    private String loginId;
    List<NewRegisterTeacherBean> teachers;
    int count = 1;
    int index = 0;
    private File logo;
    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_teacher_manager;
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
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        acAccountTeacherHeadIv.setImageResource(R.mipmap.teacher_man);
        accountTeacherCommitBtn.setVisibility(View.GONE);
        getRegisterInfomation();
    }

    private void getRegisterInfomation() {
        iRegisterReq.getNewTeacherRegisterInfo(loginId, phone)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess() && ListUtil.unEmpty(bean.getData())) {
                        teachers = bean.getData();
                        count = teachers.size();
                        showmView();
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void showmView() {
        if (count < 1) {
            return;
        }
        if (index > teachers.size() - 1) {
            return;
        }
        count--;
        NewRegisterTeacherBean data = teachers.get(index);

        if (index < count) {
            toolbarRightTv.setText("下一步");
        } else if (index > count) {
            return;
        } else {
            toolbarRightTv.setText("完成");
        }
        index++;
        if (data == null) {
            return;
        }
        if (data.getSex() != null && data.getSex().contains("男")) {
            acAccountTeacherHeadIv.setImageResource(R.mipmap.teacher_man);
        } else {
            acAccountTeacherHeadIv.setImageResource(R.mipmap.teacher_woman);
        }

        StringUtil.setTextView(accountTeacherNameTv, "名字：" + data.getName());
        StringUtil.setTextView(accountTeacherNumberTv, data.getWork_no());
        StringUtil.setTextView(accountTeacherEmailTv, data.getEmail());
        StringUtil.setTextView(accountTeacherBirthdayTv, data.getBirth());
        StringUtil.setTextView(accountTeacherGenderTv, data.getSex());
        StringUtil.setTextView(accountTeacherClassTeacherTv, "班主任：" + data.getIs_banzhuren());
        if (ListUtil.unEmpty(data.getKemuinfo())) {
            int size = data.getKemuinfo().size();
            for (int i = 0; i < size; i++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(14);
                textView.setText(data.getKemuinfo().get(i));
                accountTeacherClassLl.addView(textView);
            }
        }
        id = data.getRole_id();
    }
//        StringUtil.setTextView(newRegisterNameTv, "名字：" + data.getName());
//        StringUtil.setTextView(newRegisterTypeTv, "类型：" + data.getLevel());
//        StringUtil.setTextView(newRegisterSchoolTv, "学校：" + data.getSchool_name());
//        StringUtil.setTextView(newRegisterNumberTv, "工号：" + data.getWork_no());
//        StringUtil.setTextView(newRegisterEmailTv, "邮箱：" + data.getEmail());
//        StringUtil.setTextView(newRegisterBirthdayTv, "生日：" + data.getBirth());
//        StringUtil.setTextView(newRegisterGenderTv, "性别：" + data.getSex());
//        StringUtil.setTextView(newRegisterClassTeacherTv, "班主任：" + data.getIs_banzhuren());
//        if (ListUtil.unEmpty(data.getKemuinfo())) {
//            int size = data.getKemuinfo().size();
//            for (int i = 0; i < size; i++) {
//                TextView textView = new TextView(this);
//                textView.setGravity(Gravity.CENTER_VERTICAL);
//                textView.setTextSize(14);
//                if (ListUtil.unEmpty(data.getKemuinfo())) {
//                    textView.setText(data.getKemuinfo().get(i));
//                }
//                newRegisterClassLl.addView(textView);
//            }
//        }
//    }

    @OnClick({R.id.toolbar_right_tv,
            R.id.ac_account_teacher_head_iv,
            R.id.activity_account_teacher_name_tv,
            R.id.activity_account_teacher_number_tv,
            R.id.activity_account_teacher_email_tv,
            R.id.activity_account_teacher_birthday_tv,
            R.id.activity_account_teacher_gender_tv,
            R.id.account_teacher_radio_button,
            R.id.account_teacher_radio_ll,
            R.id.activity_account_teacher_class_teacher_tv,
            R.id.activity_account_teacher_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_account_teacher_head_iv:
                RxPicker.of()
                        .single(true)
                        .camera(true)
                        .start(this)
                        .subscribe(this::setImages);
                break;
            case R.id.activity_account_teacher_name_tv:
                break;
            case R.id.activity_account_teacher_number_tv:
                break;
            case R.id.account_teacher_radio_ll:
            case R.id.account_teacher_radio_button:
                if (aRadioButton.isChecked()) {
                    aRadioButton.setChecked(false);
//                  aRadioButton.setBackgroundResource(R.mipmap.account_manager_password);
                    aChangePwdEdt.setVisibility(View.GONE);
                    aAgreePwdEdt.setVisibility(View.GONE);
                } else {
                    aRadioButton.setChecked(true);
                    aChangePwdEdt.setVisibility(View.VISIBLE);
                    aAgreePwdEdt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_account_teacher_birthday_tv:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    StringUtil.setTextView(accountTeacherBirthdayTv, dateStr);
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
                break;
            case R.id.activity_account_teacher_gender_tv:
                ListDialog listDialog = new ListDialog(this);
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> StringUtil.setTextView(accountTeacherGenderTv, content));
                break;
            case R.id.activity_account_teacher_class_teacher_tv:
                break;
            case R.id.toolbar_right_tv:
                String change = StringUtil.EditGetString(aChangePwdEdt);
                String agree = StringUtil.EditGetString(aAgreePwdEdt);
                String birthday = StringUtil.EditGetString(accountTeacherBirthdayTv);
                String gender = StringUtil.EditGetString(accountTeacherGenderTv);
                String number = StringUtil.EditGetString(accountTeacherNumberTv);
                String email = StringUtil.EditGetString(accountTeacherEmailTv);
                String sex;
                if (gender.contains("男")) {
                    sex = "0";
                } else {
                    sex = "1";
                }
                if (change.equals(agree)) {
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("type", RxUtil.toRequestBodyTxt("teacher"));
                    map.put("role_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getRoleId()));
                    map.put("school_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getSchoolId()));
                    map.put("login_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getUserId()));
                    map.put("birth", RxUtil.toRequestBodyTxt(birthday));
                    map.put("pwd", RxUtil.toRequestBodyTxt(change));
                    map.put("sex", RxUtil.toRequestBodyTxt(sex));
                    map.put("email", RxUtil.toRequestBodyTxt(email));
                    map.put("id", RxUtil.toRequestBodyTxt(id));
                    map.put("work_no", RxUtil.toRequestBodyTxt(number));
                    if (logo != null)
                        map.put("logo", RequestBody.create(MediaType.parse("image/png"), logo));
                    iRegisterReq.changeRegisterAsTeacherOrStudent(map)
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .compose(RxLifecycleCompact.bind(this).withObservable())
                            .subscribe(bean -> {
                                if (bean.isSuccess()) {
                                    setShowView();
                                } else {
                                    toast(bean.getMsg());
                                }
                            }, new HttpError());
                } else {
                    toast("密码不一致");
                }


                break;
        }
    }

    private void setShowView() {
        if (index >= count) {
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
                            RxBus.get().send(111);
                            skipActivity(MainActivity.class);
                        }
                    }, new HttpError());
        } else {
            showmView();
        }
    }

    private List<String> getChoice() {
        List<String> lists = new ArrayList<>();
        lists.add("男");
        lists.add("女");
        return lists;
    }

    //设置头像
    private void setImages(List<ImageItem> images) {
        String path = "";
        if (ListUtil.unEmpty(images)) {
            path = images.get(0).getPath();
        }
        ImageUtil.loadCircleImages(this, acAccountTeacherHeadIv, path);
        String finalPath = path;
        Luban.with(this)
                .load(new File(path))//传入要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        logo = file;
                    }

                    @Override
                    public void onError(Throwable e) {
                        logo = new File(finalPath);
                    }
                }).launch();//启动压缩

    }
}
