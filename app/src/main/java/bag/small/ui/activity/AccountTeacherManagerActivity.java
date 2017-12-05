package bag.small.ui.activity;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import java.io.File;
import java.util.ArrayList;
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
import bag.small.http.IApi.ILoginRequest;
import bag.small.http.IApi.IRegisterReq;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AccountTeacherManagerActivity extends BaseActivity {

    @BindView(R.id.ac_account_teacher_head_iv)
    ImageView acAccountTeacherHeadIv;
    @BindView(R.id.activity_account_teacher_name_tv)
    TextView accountTeacherNameTv;
    @BindView(R.id.activity_account_teacher_number_tv)
    EditText accountTeacherNumberTv;
    @BindView(R.id.activity_account_teacher_email_tv)
    EditText accountTeacherEmailTv;
    @BindView(R.id.activity_account_teacher_birthday_tv)
    TextView accountTeacherBirthdayTv;
    @BindView(R.id.activity_account_teacher_gender_tv)
    TextView accountTeacherGenderTv;
    @BindView(R.id.activity_account_teacher_class_teacher_tv)
    TextView accountTeacherClassTeacherTv;
    @BindView(R.id.activity_account_teacher_class_ll)
    LinearLayout accountTeacherClassLl;
    @BindView(R.id.activity_account_teacher_change_pwd_edt)
    EditText aChangePwdEdt;
    @BindView(R.id.activity_account_teacher_commit_btn)
    Button accountTeacherCommitBtn;
    @BindView(R.id.activity_account_teacher_school_tv)
    TextView schoolTv;
    @BindView(R.id.password_card_view)
    CardView cardView;
    private IRegisterReq iRegisterReq;
    private File logo;
    private String Id;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_teacher_manager;
    }

    @Override
    public void initView() {
        setToolTitle("账号管理", true);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        getRegisterInfomation();
        cardView.setVisibility(View.VISIBLE);
    }

    private void getRegisterInfomation() {
        iRegisterReq.getNewTeacherRegisterInfo(UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        showView(bean.getData());
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
        iRegisterReq.getStudentInfo(UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess() && bean.getData() != null) {
                        Id = bean.getData().getId();
                        if (TextUtils.isEmpty(Id)) {
                            Id = bean.getData().getTarget_id();
                        }
                    } else {
                        try {
                            toast(bean.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new HttpError());
    }

    private void showView(NewRegisterTeacherBean data) {
        ImageUtil.loadImages(this, acAccountTeacherHeadIv, UserPreferUtil.getInstanse().getHeadImagePath());
        StringUtil.setTextView(accountTeacherNameTv, data.getName());
        StringUtil.setTextView(accountTeacherNumberTv, data.getWork_no());
        StringUtil.setTextView(accountTeacherEmailTv, data.getEmail());
        StringUtil.setTextView(accountTeacherBirthdayTv, data.getBirth());
        StringUtil.setTextView(accountTeacherGenderTv, data.getSex());
        StringUtil.setTextView(schoolTv, data.getSchool_name());
        StringUtil.setTextView(accountTeacherClassTeacherTv, data.getIs_banzhuren());
        if (ListUtil.unEmpty(data.getKemuinfo())) {
//            int size = 3;
            int size = data.getKemuinfo().size();
            for (int i = 0; i < size; i++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(15);
                textView.setText(data.getKemuinfo().get(i));
//                textView.setText(data.getKemuinfo().get(0));
                accountTeacherClassLl.addView(textView);
            }
        }
    }

    @OnClick({R.id.toolbar_right_tv,
            R.id.ac_account_teacher_head_iv,
            R.id.activity_account_teacher_name_tv,
            R.id.activity_account_teacher_number_tv,
            R.id.activity_account_teacher_email_tv,
            R.id.activity_account_teacher_birthday_tv,
            R.id.activity_account_teacher_gender_tv,
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
            case R.id.activity_account_teacher_birthday_tv:
                //时间选择器
//                TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
//                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//                    StringUtil.setTextView(accountTeacherBirthdayTv, dateStr);
//                }).setType(new boolean[]{true, true, true, false, false, false})
//                        .build();
//                pvTime.setDate(Calendar.getInstance());
//                pvTime.show();
                break;
            case R.id.activity_account_teacher_gender_tv:
                ListDialog listDialog = new ListDialog(this);
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> StringUtil.setTextView(accountTeacherGenderTv, content));
                break;
            case R.id.activity_account_teacher_class_teacher_tv:
                break;
            case R.id.activity_account_teacher_commit_btn:
                String change = StringUtil.EditGetString(aChangePwdEdt);
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
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("type", RxUtil.toRequestBodyTxt("teacher"));
                map.put("role_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getRoleId()));
                map.put("school_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getSchoolId()));
                map.put("login_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getUserId()));
                map.put("birth", RxUtil.toRequestBodyTxt(birthday));
                map.put("pwd", RxUtil.toRequestBodyTxt(change));
                map.put("sex", RxUtil.toRequestBodyTxt(sex));
                map.put("email", RxUtil.toRequestBodyTxt(email));
                map.put("id", RxUtil.toRequestBodyTxt(Id));
                map.put("work_no", RxUtil.toRequestBodyTxt(number));
                if (logo != null) {
                    iRegisterReq.changeRegisterAsTeacherOrStudent(map, RxUtil.convertImage("logo", logo))
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
