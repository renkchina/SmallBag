package bag.small.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.entity.LoginResult;
import bag.small.entity.RegisterInfoBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.http.IApi.IRegisterReq;
import bag.small.http.IApi.IRegisterSendCode;
import bag.small.interfaze.IListDialog;
import bag.small.interfaze.IViewBinder;
import bag.small.provider.TeachSubjectClass;
import bag.small.provider.TeachSubjectClassViewBinder;
import bag.small.rx.RxCountDown;
import bag.small.rx.RxUtil;
import bag.small.utils.GlobalValues;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class TeacherInformationActivity extends BaseActivity
        implements IViewBinder {
    @BindView(R.id.ac_teacher_head_iv)
    ImageView mHeadImage;
    @BindView(R.id.activity_guardian_tv)
    TextView aGuardianTv;
    @BindView(R.id.activity_guardian_ll)
    LinearLayout aGuardianLl;
    @BindView(R.id.activity_teacher_send_code_btn)
    Button sendCode;
    @BindView(R.id.activity_teacher_verification_code_edt)
    EditText vertifyCode;
    @BindView(R.id.activity_charge_class_tv)
    TextView aChargeClassTv;
    @BindView(R.id.activity_is_teacher_tv)
    TextView isClassTeacher;
    @BindView(R.id.activity_charge_class_ll)
    LinearLayout aChargeClassLl;
    @BindView(R.id.ac_teacher_number_tv)
    TextView acTeacherNumberTv;
    @BindView(R.id.ac_teacher_grade_tv)
    TextView acTeacherGradeTv;
    @BindView(R.id.ac_teacher_class_tv)
    TextView acTeacherClassTv;
    @BindView(R.id.activity_teacher_class_ll)
    LinearLayout masterClassLl;
    @BindView(R.id.activity_teacher_subject_recycler)
    RecyclerView subjectRecycler;
    @BindView(R.id.activity_teacher_commit_btn)
    Button aTeacherCommitBtn;
    @BindView(R.id.activity_teacher_name_edit)
    EditText aTeacherNameEdit;
    @BindView(R.id.activity_teacher_phone_edit)
    EditText aTeacherPhoneEdit;
    @BindView(R.id.activity_area_school_tv)
    TextView aAreaSchoolTv;
    @BindView(R.id.activity_area_school_ll)
    LinearLayout aAreaSchoolLl;

    List<Object> items;
    MultiTypeAdapter multiTypeAdapter;
    IRegisterSendCode iRegisterSendCode;

    ListDialog listDialog;
    @BindView(R.id.activity_teacher_gender_tv)
    TextView mGenderTv;
    @BindView(R.id.activity_teacher_gender_ll)
    LinearLayout mGenderLl;
    private IRegisterReq iRegisterReq;
    private RegisterInfoBean.SchoolArea area;
    private List<RegisterInfoBean.SchoolArea> areaLists;
    private RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean jie;
    private List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean> course;
    private TeachSubjectClassViewBinder viewBinder;

    private int isMaster = 1;
    private String jieci;
    private int nianji;
    private String banji;
    private File logo;
    private String school_id;
    private RegisterInfoBean.SchoolArea.SchoolBean school;
    private List<RegisterInfoBean.SchoolArea.SchoolBean> areaSchoolList;
    private boolean isMain;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_information;
    }

    @Override
    public void initView() {
        setToolTitle("老师注册", true);
        listDialog = new ListDialog(this);
        if (getIntent().getExtras() != null)
            isMain = getIntent().getExtras().getBoolean("ismain",false);
        getRegisterInfo();
        items = new ArrayList<>();
        items.add(new TeachSubjectClass(false));
        multiTypeAdapter = new MultiTypeAdapter(items);
        viewBinder = new TeachSubjectClassViewBinder(this);
        multiTypeAdapter.register(TeachSubjectClass.class, viewBinder);
        subjectRecycler.setLayoutManager(new LinearLayoutManager(this));
        subjectRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        subjectRecycler.setAdapter(multiTypeAdapter);
        ImageUtil.loadCircleImageForRegister(this, mHeadImage, "");
        iRegisterSendCode = HttpUtil.getInstance().createApi(IRegisterSendCode.class);
    }

    @OnClick({R.id.activity_guardian_ll, R.id.activity_charge_class_ll,
            R.id.ac_teacher_number_tv, R.id.ac_teacher_grade_tv,
            R.id.ac_teacher_class_tv, R.id.activity_teacher_commit_btn,
            R.id.activity_area_school_ll, R.id.ac_teacher_head_iv,
            R.id.activity_teacher_send_code_btn, R.id.activity_teacher_gender_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_teacher_head_iv:
                RxPicker.of()
                        .single(true)
                        .camera(true)
                        .start(this)
                        .subscribe(this::setImages);
                break;
            case R.id.activity_teacher_send_code_btn:
                sendCode();
                break;
            case R.id.activity_area_school_ll://区域
                listDialog.setListData(getArea());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    aAreaSchoolTv.setText(content);
                    area = areaLists.get(position);

                });
                break;
            case R.id.activity_guardian_ll://学校
                listDialog.setListData(getSchool());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    school_id = areaSchoolList.get(position).getId();
                    school = areaSchoolList.get(position);
                    viewBinder.setSchool(school);
                    aGuardianTv.setText(content);
                });

                break;
            case R.id.activity_charge_class_ll://是否班主任
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    isClassTeacher.setText(content);
                    if ("是".equals(content)) {
                        isMaster = 1;
                        masterClassLl.setVisibility(View.VISIBLE);
                    } else {
                        isMaster = 0;
                        masterClassLl.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.ac_teacher_number_tv://届次
                listDialog.setListData(getJieCi());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    jieci = getNumbers(content);
                    acTeacherNumberTv.setText(content);
                    if (school != null && school.getBase() != null) {
                        jie = school.getBase().getJie().get(position);
                        nianji = jie.getNianji();
                        acTeacherGradeTv.setText(jie.getNianji_name());
                    }
                    if (ListUtil.unEmpty(getBanji())) {
                        acTeacherClassTv.setText(getBanji().get(0));
                        banji = getNumbers(getBanji().get(0));
                    } else {
                        acTeacherClassTv.setText("班级");
                    }
                });
                break;
            case R.id.ac_teacher_grade_tv:
                break;
            case R.id.ac_teacher_class_tv://班级
                listDialog.setListData(getBanji());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    acTeacherClassTv.setText(content);
                    course = jie.getKeche();
                    banji = getNumbers(content);
                });
                break;
            case R.id.activity_teacher_commit_btn:
                String phone = StringUtil.EditGetString(aTeacherPhoneEdit);
                String name = StringUtil.EditGetString(aTeacherNameEdit);
                String code = StringUtil.EditGetString(vertifyCode);
                String sex = StringUtil.EditGetString(mGenderTv);
                if (isMaster == 0) {
                    jieci = "0";
                    nianji = -1;
                    banji = "0";
                }
                try {
                    requestRegister(name, phone, code, school_id, isMaster, jieci, nianji,
                            UserPreferUtil.getInstanse().getUserId(), banji, sex,
                            RxUtil.convertImage("logo", logo), setRequests());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    toast("请选择完整！");
                }
                break;
            case R.id.activity_teacher_gender_ll:
                listDialog.setListData(getGender());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> mGenderTv.setText(content));
                break;
        }
    }

    private void sendCode() {
        String phone = StringUtil.EditGetString(aTeacherPhoneEdit);
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号！");
        } else {
            iRegisterSendCode.sendCheckCodeRequest(phone)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(bean -> {
                        if (bean.isSuccess()) {
                            RxCountDown.TimerDown(GlobalValues.COUNT_DOWN_TIME, sendCode);
                        }
                        try {
                            toast(bean.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, new HttpError());
        }
    }

    private void requestRegister(@NonNull String name, @NonNull String phone, @NonNull String code, @NonNull String school_id,
                                 int isMaster, @NonNull String jieci, int nianji, @NonNull String login_id,
                                 @NonNull String banji, String sex, @NonNull MultipartBody.Part logo, @NonNull String strings) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(school_id) || TextUtils.isEmpty(jieci) || TextUtils.isEmpty(banji) || nianji == 0 || TextUtils.isEmpty(login_id
        )) {
            toast("请录入完整！");
        } else {
            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("name", RxUtil.toRequestBodyTxt(name));
            map.put("school_id", RxUtil.toRequestBodyTxt(school_id));
            map.put("login_id", RxUtil.toRequestBodyTxt(login_id));
            map.put("phone", RxUtil.toRequestBodyTxt(phone));
            map.put("is_master", RxUtil.toRequestBodyTxt(isMaster + ""));
            map.put("jieci", RxUtil.toRequestBodyTxt(jieci));
            map.put("nianji", RxUtil.toRequestBodyTxt(nianji + ""));
            map.put("banji", RxUtil.toRequestBodyTxt(banji));
            map.put("verify_code", RxUtil.toRequestBodyTxt(code));
            map.put("sex", RxUtil.toRequestBodyTxt(sex));
            map.put("jiaoxue", RxUtil.toRequestBodyTxt(strings));

            iRegisterReq.goRegisterAsTeacher(map, logo)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(bean -> {
                        if (bean.isSuccess()) {
                            if (isMain)
                                getRoles();
                            else
                                finish();
                        } else {
                            toast(bean.getMsg());
                        }
                    }, new HttpError());
        }
    }

    @Override
    public void add(int type, int position) {
        items.add(new TeachSubjectClass(true));
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(int position) {
        items.remove(position);
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void click(int position) {

    }

    private List<TeachSubjectClass> getSubjects() {
        int size = items.size();
        List<TeachSubjectClass> lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TeachSubjectClass bean = (TeachSubjectClass) items.get(i);
            if (bean.getJieci() != 0 && bean.getNianji() != 0) {
                lists.add(bean);
            }
        }
        return lists;
    }

    private String setRequests() {
        List<TeachSubjectClass> lists = getSubjects();
        Gson gson = new Gson();
        List<Map<String, String>> json = new ArrayList<>();
        if (ListUtil.unEmpty(lists)) {
            int size = lists.size();
            for (int i = 0; i < size; i++) {
                Map<String, String> map = new HashMap<>(4);
                map.put("kemu", getNumbers(lists.get(i).getKemu()));
                map.put("jieci", getNumbers(lists.get(i).getJieci() + ""));
                map.put("nianji", getNumbers(lists.get(i).getNianji() + ""));
                map.put("banji", getNumbers(lists.get(i).getBanji()));
                json.add(map);
            }
        }
        return gson.toJson(json);
    }

    //截取数字
    public String getNumbers(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    //班主任
    private List<String> getChoice() {
        List<String> lists = new ArrayList<>();
        lists.add("是");
        lists.add("否");
        return lists;
    }

    private void getRegisterInfo() {
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        iRegisterReq.getRegisterInfo()
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        LogUtil.show(bean.getData());
                        areaLists = bean.getData().getSchool();
                    }
                }, new HttpError());
    }

    //设置头像
    private void setImages(List<ImageItem> images) {
        String path = "";
        if (ListUtil.unEmpty(images)) {
            path = images.get(0).getPath();
        }
        ImageUtil.loadCircleImages(this, mHeadImage, path);
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

    //区域
    private List<String> getArea() {
        List<String> lists = new ArrayList<>();
        if (ListUtil.unEmpty(areaLists)) {
            for (RegisterInfoBean.SchoolArea beanX : areaLists) {
                lists.add(beanX.getName());
            }
        }
        return lists;
    }

    //学校
    private List<String> getSchool() {
        List<String> lists = new ArrayList<>();
        if (area != null) {
            areaSchoolList = area.getSchool();
            if (ListUtil.unEmpty(areaSchoolList)) {
                for (RegisterInfoBean.SchoolArea.SchoolBean schoolBean : areaSchoolList) {
                    lists.add(schoolBean.getName());
                }
            }
        }
        return lists;
    }

    //届次
    private List<String> getJieCi() {
        List<String> lists = new ArrayList<>();
        if (school == null) {
            return lists;
        }
        List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean> list =
                school.getBase().getJie();
        if (ListUtil.unEmpty(list)) {
            for (RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean jieBean : list) {
                lists.add(jieBean.getName());
            }
        }
        return lists;
    }

    //班级
    private List<String> getBanji() {
        List<String> lists = new ArrayList<>();
        if (jie == null) {
            return lists;
        }
        if (ListUtil.unEmpty(jie.getBanji())) {
            List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean> list = jie.getBanji();
            for (RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean banjiBean : list) {
                lists.add(banjiBean.getText());
            }
        }
        return lists;
    }


    private List<String> getGender() {
        List<String> lists = new ArrayList<>();
        lists.add("男");
        lists.add("女");
        return lists;
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


}
