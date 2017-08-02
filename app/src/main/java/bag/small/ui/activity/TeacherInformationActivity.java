package bag.small.ui.activity;

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

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.entity.BaseBean;
import bag.small.entity.RegisterInfoBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.interfaze.IViewBinder;
import bag.small.provider.TeachSubjectClass;
import bag.small.provider.TeachSubjectClassViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.MultiTypeAdapter;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class TeacherInformationActivity extends BaseActivity
        implements IViewBinder {
    @Bind(R.id.ac_teacher_head_iv)
    ImageView mHeadImage;
    @Bind(R.id.activity_guardian_tv)
    TextView aGuardianTv;
    @Bind(R.id.activity_guardian_ll)
    LinearLayout aGuardianLl;
    @Bind(R.id.activity_charge_class_tv)
    TextView aChargeClassTv;
    @Bind(R.id.activity_is_teacher_tv)
    TextView isClassTeacher;
    @Bind(R.id.activity_charge_class_ll)
    LinearLayout aChargeClassLl;
    @Bind(R.id.ac_teacher_number_tv)
    TextView acTeacherNumberTv;
    @Bind(R.id.ac_teacher_grade_tv)
    TextView acTeacherGradeTv;
    @Bind(R.id.ac_teacher_class_tv)
    TextView acTeacherClassTv;
    @Bind(R.id.activity_teacher_class_ll)
    LinearLayout masterClassLl;
    @Bind(R.id.activity_teacher_subject_recycler)
    RecyclerView subjectRecycler;
    @Bind(R.id.activity_teacher_commit_btn)
    Button aTeacherCommitBtn;
    @Bind(R.id.activity_teacher_name_edit)
    EditText aTeacherNameEdit;
    @Bind(R.id.activity_teacher_phone_edit)
    EditText aTeacherPhoneEdit;
    @Bind(R.id.activity_area_school_tv)
    TextView aAreaSchoolTv;
    @Bind(R.id.activity_area_school_ll)
    LinearLayout aAreaSchoolLl;

    List<Object> items;
    MultiTypeAdapter multiTypeAdapter;

    ListDialog listDialog;
    private IRegisterReq iRegisterReq;
    private RegisterInfoBean.SchoolBeanX area;
    private List<RegisterInfoBean.SchoolBeanX> areaLists;
    private RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean jie;
    private List<RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean.KecheBean> course;
    private TeachSubjectClassViewBinder viewBinder;

    private String[][] jiaoxue;
    private int isMaster;
    private String jieci;
    private int nianji;
    private String banji;
    private String logo;
    private String school_id;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_information;
    }

    @Override
    public void initView() {
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        listDialog = new ListDialog(this);
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
        ImageUtil.loadCircleImages(this, mHeadImage, "");
    }

    @OnClick({R.id.activity_guardian_ll, R.id.activity_charge_class_ll,
            R.id.ac_teacher_number_tv, R.id.ac_teacher_grade_tv,
            R.id.ac_teacher_class_tv, R.id.activity_teacher_commit_btn,
            R.id.activity_area_school_ll, R.id.ac_teacher_head_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_teacher_head_iv:
                RxPicker.of()
                        .single(true)
                        .camera(true)
                        .start(this)
                        .subscribe(this::setImages);
                break;
            case R.id.activity_area_school_ll:
                listDialog.setListData(getArea());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    aAreaSchoolTv.setText(content);
                    area = areaLists.get(position);
                    viewBinder.setArea(area);
                    school_id = area.getSchool().getId();
                    aGuardianTv.setText(area.getSchool().getName());
                });
                break;
            case R.id.activity_guardian_ll:
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
                    jie = area.getSchool().getBase().getJie().get(position);
                    nianji = jie.getNianji();
                    acTeacherGradeTv.setText(jie.getNianji_name());
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
                if (isMaster == 0) {
                    jieci = "0";
                    nianji = -1;
                    banji = "0";
                }
                try {
                    requestRegister(name, phone, school_id, isMaster, jieci, nianji, UserPreferUtil.getInstanse().getUserId(), banji, logo, setRequests());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    toast("请选择完整！");
                }
                break;
        }
    }

    private void requestRegister(@NonNull String name, @NonNull String phone, @NonNull String school_id,
                                 int isMaster, @NonNull String jieci, int nianji, int userId,
                                 @NonNull String banji, @NonNull String logo, @NonNull String[][] strings) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(school_id) || TextUtils.isEmpty(jieci) || TextUtils.isEmpty(logo) || TextUtils.isEmpty(banji) || nianji == 0 || userId == 0) {
            toast("请录入完整！");
        } else {
            iRegisterReq.goRegisterAsTeacher(
                    userId, name, phone, school_id, isMaster,
                    jieci, nianji, banji, logo, strings)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(bean -> {
                        if (bean.isSuccess()) {

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

    private String[][] setRequests() {
        List<TeachSubjectClass> lists = getSubjects();
        if (ListUtil.unEmpty(lists)) {
            int size = lists.size();
            jiaoxue = new String[size][4];
            for (int i = 0; i < size; i++) {
                jiaoxue[i][0] = getNumbers(lists.get(i).getKemu());
                jiaoxue[i][1] = getNumbers(lists.get(i).getJieci() + "");
                jiaoxue[i][2] = getNumbers(lists.get(i).getNianji() + "");
                jiaoxue[i][3] = getNumbers(lists.get(i).getBanji());
            }
        }
        return jiaoxue;
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
        iRegisterReq.getRegisterInfo()
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        LogUtil.show(bean.getData());
                        areaLists = getAreaList(bean.getData());
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
                .load(new File(path))//传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        try {
                            logo = ImageUtil.encodeBase64File(file.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                logo = ImageUtil.encodeBase64File(finalPath);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            logo = ImageUtil.encodeBase64File(finalPath);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }).launch();//启动压缩

    }

    private List<RegisterInfoBean.SchoolBeanX> getAreaList(RegisterInfoBean bean) {
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

    //区域
    private List<String> getArea() {
        List<String> lists = new ArrayList<>();
        if (ListUtil.unEmpty(areaLists)) {
            for (RegisterInfoBean.SchoolBeanX beanX : areaLists) {
                lists.add(beanX.getName());
            }
        }
        return lists;
    }

    //届次
    private List<String> getJieCi() {
        List<String> lists = new ArrayList<>();
        if (area == null) {
            return lists;
        }
        List<RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean> list =
                area.getSchool().getBase().getJie();
        if (ListUtil.unEmpty(list)) {
            for (RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean jieBean : list) {
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
            List<RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean.BanjiBean> list = jie.getBanji();
            for (RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean.BanjiBean banjiBean : list) {
                lists.add(banjiBean.getText());
            }
        }
        return lists;
    }

}
