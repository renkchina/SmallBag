package bag.small.ui.activity;

import android.os.Bundle;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AccountStudentManagerActivity extends BaseActivity {

    @Bind(R.id.ac_account_student_head_iv)
    ImageView acAccountStudentHeadIv;
    @Bind(R.id.activity_account_student_name_tv)
    TextView accountStudentNameTv;
    @Bind(R.id.activity_account_student_number_tv)
    EditText accountStudentNumberTv;
    @Bind(R.id.activity_account_student_grade_tv)
    TextView accountStudentGradeTv;
    @Bind(R.id.activity_account_student_class_tv)
    TextView accountStudentClassTv;
    @Bind(R.id.activity_account_student_gender_tv)
    TextView accountStudentGenderTv;
    @Bind(R.id.activity_account_student_birthday_tv)
    TextView accountStudentBirthdayTv;
    @Bind(R.id.activity_account_student_change_pwd_edt)
    EditText aChangePwdEdt;
    @Bind(R.id.activity_account_student_agree_pwd_edt)
    EditText aAgreePwdEdt;
    @Bind(R.id.activity_account_student_commit_btn)
    Button accountStudentCommitBtn;
    @Bind(R.id.account_student_radio_button)
    RadioButton aRadioButton;
    @Bind(R.id.account_student_password_ll)
    LinearLayout accountStudentPasswordLl;
    private File logo;
    private ListDialog listDialog;
    private IRegisterReq iRegisterReq;
    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_student_manager;
    }

    @Override
    public void initView() {
        setToolTitle("账号管理", true);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        getRegisterInfomation();
    }


    private void getRegisterInfomation() {
        iRegisterReq.getStudentInfo(UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        StringUtil.setTextView(accountStudentNameTv, "姓名：" + bean.getData().getName());
                        StringUtil.setTextView(accountStudentGradeTv, "年级：" + bean.getData().getBanci());
                        StringUtil.setTextView(accountStudentClassTv, "班级：" + bean.getData().getBanji());
                        StringUtil.setTextView(accountStudentNumberTv, bean.getData().getStudent_no());
                        StringUtil.setTextView(accountStudentBirthdayTv, bean.getData().getBirth());
                        StringUtil.setTextView(accountStudentGenderTv, bean.getData().getSex());
                        id = bean.getData().getTarget_id();
                        if (TextUtils.isEmpty(id)) {
                            id = bean.getData().getId();
                        }
                    } else {
                        try {
                            toast(bean.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new HttpError());
        ImageUtil.loadImages(this, acAccountStudentHeadIv, UserPreferUtil.getInstanse().getHeadImagePath());
    }

    @OnClick({R.id.toolbar_right_tv,
            R.id.ac_account_student_head_iv,
            R.id.activity_account_student_name_tv,
            R.id.activity_account_student_number_tv,
            R.id.activity_account_student_grade_tv,
            R.id.activity_account_student_class_tv,
            R.id.activity_account_student_gender_tv,
            R.id.activity_account_student_birthday_tv,
            R.id.account_student_password_ll,
            R.id.account_student_radio_button,
            R.id.activity_account_student_commit_btn})
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
            case R.id.activity_account_student_grade_tv:
                break;
            case R.id.activity_account_student_class_tv:
                break;
            case R.id.account_student_password_ll:
            case R.id.account_student_radio_button:
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
            case R.id.activity_account_student_gender_tv:
                listDialog = new ListDialog(this);
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) ->
                        StringUtil.setTextView(accountStudentGenderTv, content));

                break;
            case R.id.activity_account_student_birthday_tv:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    StringUtil.setTextView(accountStudentBirthdayTv, dateStr);
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
                break;
            case R.id.activity_account_student_commit_btn:
                String change = StringUtil.EditGetString(aChangePwdEdt);
                String agree = StringUtil.EditGetString(aAgreePwdEdt);
                String birthday = StringUtil.EditGetString(accountStudentBirthdayTv);
                String number = StringUtil.EditGetString(accountStudentNumberTv);
                if (birthday.startsWith("生日")) {
                    birthday = birthday.replace("生日：", "");
                }
                String gender = StringUtil.EditGetString(accountStudentGenderTv);
                if (gender.equals("男")) {
                    gender = "0";
                } else {
                    gender = "1";
                }
                if (change.equals(agree)) {
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("type", RxUtil.toRequestBodyTxt("student"));
                    map.put("role_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getRoleId()));
                    map.put("school_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getSchoolId()));
                    map.put("login_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getUserId()));
                    map.put("birth", RxUtil.toRequestBodyTxt(birthday));
                    map.put("pwd", RxUtil.toRequestBodyTxt(change));
                    map.put("sex", RxUtil.toRequestBodyTxt(gender));
                    map.put("email", RxUtil.toRequestBodyTxt(""));
                    map.put("id", RxUtil.toRequestBodyTxt(id));
                    map.put("work_no", RxUtil.toRequestBodyTxt(number));
                    if (logo != null){
                        map.put("logo", RequestBody.create(MediaType.parse("image/png"), logo));
                    }
                    iRegisterReq.changeRegisterAsTeacherOrStudent(map)
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .compose(RxLifecycleCompact.bind(this).withObservable())
                            .subscribe(bean -> {
                                if (bean.isSuccess()) {
                                    getRoles();
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


    private void getRoles() {
        HttpUtil.getInstance().createApi(ILoginRequest.class).getAllRole(UserPreferUtil.getInstanse().getUserId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        LoginResult.RoleBean mBean = bean.getData().get(0);
                        mBean.setSelected(true);
                        UserPreferUtil.getInstanse().setUserInfomation(mBean);
                        MyApplication.loginResults = bean.getData();
                    }
                    finish();
                }, new HttpError());
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

    private List<String> getChoice() {
        List<String> lists = new ArrayList<>();
        lists.add("男");
        lists.add("女");
        return lists;
    }
}
