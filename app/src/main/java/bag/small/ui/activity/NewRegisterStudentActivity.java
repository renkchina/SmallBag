package bag.small.ui.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
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
import bag.small.entity.NewRegisterStudentOrTeacherBean;
import bag.small.entity.NewRegisterTeacherBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.http.IApi.IRegisterReq;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class NewRegisterStudentActivity extends BaseActivity {

    //    @Bind(R.id.ac_new_student_head_iv)
//    ImageView acNewStudentHeadIv;
//    @Bind(R.id.new_register_student_name_tv)
//    TextView studentNameTv;
//    @Bind(R.id.new_register_student_type_tv)
//    TextView studentTypeTv;
//    @Bind(R.id.new_register_student_school_tv)
//    TextView studentSchoolTv;
//    @Bind(R.id.new_register_student_number_tv)
//    TextView studentNumberTv;
//    @Bind(R.id.new_register_student_class_tv)
//    TextView studentClassTv;
//    @Bind(R.id.new_register_student_class_student_tv)
//    TextView studentxuehaoTv;
//    @Bind(R.id.new_register_student_birthday_tv)
//    TextView studentBirthdayTv;
//    @Bind(R.id.new_register_student_gender_tv)
//    TextView studentGenderTv;
    @Bind(R.id.ac_account_student_head_iv)
    ImageView acAccountStudentHeadIv;
    @Bind(R.id.activity_account_student_name_tv)
    TextView accountStudentNameTv;
    @Bind(R.id.activity_account_student_number_tv)
    TextView accountStudentNumberTv;
//    @Bind(R.id.activity_account_student_grade_tv)
//    TextView accountStudentGradeTv;
    @Bind(R.id.activity_account_student_class_tv)
    TextView accountStudentClassTv;
    @Bind(R.id.activity_account_student_gender_tv)
    TextView accountStudentGenderTv;
    @Bind(R.id.activity_account_student_birthday_tv)
    TextView accountStudentBirthdayTv;
    @Bind(R.id.activity_account_student_agree_pwd_edt)
    EditText aAgreePwdEdt;
    @Bind(R.id.activity_account_student_commit_btn)
    Button accountStudentCommitBtn;
    @Bind(R.id.activity_account_student_school_tv)
    TextView schoolTv;
    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private IRegisterReq iRegisterRequest;
    private String phone;
    private String loginId;
    private List<NewRegisterStudentOrTeacherBean> students;
    private int count = 1, index = 0;
    private String id;
    private File logo;
    private ListDialog listDialog;
    private IRegisterReq iRegisterReq;
    NewRegisterStudentOrTeacherBean current;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_student_manager;
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
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
    }

    @Override
    public void initView() {
        acAccountStudentHeadIv.setImageResource(R.mipmap.student_boy);
        accountStudentCommitBtn.setVisibility(View.GONE);
        iRegisterRequest.getNewStudentRegisterInfo(loginId, phone)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess() && ListUtil.unEmpty(bean.getData())) {
                        students = bean.getData();
                        count = students.size();
                        showView();
                    } else {
                        try {
                            toast(bean.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new HttpError());
    }


    private void showView() {
        if (count < 1) {
            return;
        }
        if (index > students.size() - 1) {
            return;
        }
        count--;
        NewRegisterStudentOrTeacherBean bean = students.get(index);
        current = bean;
        if (index < count) {
            toolbarRightTv.setText("下一步");
        } else if (index > count) {
            return;
        } else {
            toolbarRightTv.setText("完成");
        }
        index++;
        if (bean == null) {
            return;
        }

        if (TextUtils.isEmpty(bean.getLogo())) {
            if (bean.getSex() != null && bean.getSex().contains("男")) {
                acAccountStudentHeadIv.setImageResource(R.mipmap.student_boy);
            } else {
                acAccountStudentHeadIv.setImageResource(R.mipmap.student_girl);
            }
        } else {
            ImageUtil.loadCircleImages(this, acAccountStudentHeadIv, bean.getLogo());
        }
        StringUtil.setTextView(accountStudentNameTv, bean.getName());
        StringUtil.setTextView(schoolTv, bean.getSchool_name());
        StringUtil.setTextView(accountStudentClassTv, bean.getBanji());
        StringUtil.setTextView(accountStudentNumberTv, bean.getStudent_no());
        StringUtil.setTextView(accountStudentBirthdayTv, bean.getBirth());
        StringUtil.setTextView(accountStudentGenderTv, bean.getSex());
        id = bean.getId();
    }

    @OnClick({R.id.toolbar_right_tv,
            R.id.ac_account_student_head_iv,
            R.id.activity_account_student_name_tv,
            R.id.activity_account_student_number_tv,
            R.id.activity_account_student_class_tv,
            R.id.activity_account_student_gender_tv,
            R.id.activity_account_student_birthday_tv
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_account_student_head_iv:
                RxPicker.of()
                        .single(true)
                        .camera(true)
                        .start(this)
                        .subscribe(this::setImages);
                break;
            case R.id.activity_account_student_name_tv:
                break;
            case R.id.activity_account_student_number_tv:
                break;
            case R.id.toolbar_right_tv:
                String birthday = StringUtil.EditGetString(accountStudentBirthdayTv);
                if (birthday.startsWith("生日")) {
                    birthday = birthday.replace("生日：", "");
                }
                String gender = StringUtil.EditGetString(accountStudentGenderTv);
                if (gender.equals("男")) {
                    gender = "0";
                } else {
                    gender = "1";
                }
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("type", RxUtil.toRequestBodyTxt("student"));
                map.put("role_id", RxUtil.toRequestBodyTxt(current.getRole_id()));
                map.put("school_id", RxUtil.toRequestBodyTxt(current.getSchool_id()));
                map.put("login_id", RxUtil.toRequestBodyTxt(loginId));
                map.put("birth", RxUtil.toRequestBodyTxt(birthday));
                map.put("sex", RxUtil.toRequestBodyTxt(gender));
                map.put("email", RxUtil.toRequestBodyTxt(""));
                map.put("id", RxUtil.toRequestBodyTxt(current.getTarget_id()));
                map.put("work_no", RxUtil.toRequestBodyTxt(""));
                if (logo != null)
                    map.put("logo", RequestBody.create(MediaType.parse("image/png"), logo));
                iRegisterReq.changeRegisterAsTeacherOrStudent(map)
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .compose(RxLifecycleCompact.bind(this).withObservable())
                        .subscribe(bean -> {
                            if (bean.isSuccess()) {
                                setview();
                            } else {
                                toast(bean.getMsg());
                            }
                        }, new HttpError());

                break;
            case R.id.activity_account_student_class_tv:
                break;
            case R.id.activity_account_student_gender_tv:
                listDialog = new ListDialog(this);
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) ->
                        StringUtil.setTextView(accountStudentGenderTv, content));
                break;
            case R.id.activity_account_student_birthday_tv:
                //时间选择器
//                TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
//                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//                    StringUtil.setTextView(accountStudentBirthdayTv, dateStr);
//                }).setType(new boolean[]{true, true, true, false, false, false})
//                        .build();
//                pvTime.setDate(Calendar.getInstance());
//                pvTime.show();
                break;
        }
    }

    private void setview() {
        if (index >= count) {
            iRegisterRequest.getNewRegisterInfo(loginId, phone)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(data -> {
                        if (data.isSuccess()) {
                            MyApplication.isLogin = true;
                            MyApplication.loginResults.clear();
                            MyApplication.loginResults.addAll(data.getData().getRole());
                            LoginResult.RoleBean mBean = data.getData().getRole().get(0);
                            mBean.setSelected(true);
                            UserPreferUtil.getInstanse().setUserInfomation(mBean);
                            UserPreferUtil.getInstanse().setUseId(data.getData().getLogin_id());
                            RxBus.get().send(111);
                            skipActivity(MainActivity.class);
                        }
                    }, new HttpError());
        } else {
            showView();
        }
    }

    //设置头像
    private void setImages(List<ImageItem> images) {
        String path = "";
        if (ListUtil.unEmpty(images)) {
            path = images.get(0).getPath();
        }
        ImageUtil.loadCircleImages(this, acAccountStudentHeadIv, path);
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

    private void getRoles() {
        HttpUtil.getInstance().createApi(ILoginRequest.class).getAllRole(UserPreferUtil.getInstanse().getUserId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        LoginResult.RoleBean mBean = bean.getData().get(0);
                        mBean.setSelected(true);
                        UserPreferUtil.getInstanse().setUserInfomation(mBean);
                        MyApplication.loginResults.clear();
                        MyApplication.loginResults.addAll(bean.getData());
                    }
                    finish();
                }, new HttpError());
    }

    private List<String> getChoice() {
        List<String> lists = new ArrayList<>();
        lists.add("男");
        lists.add("女");
        return lists;
    }

}
