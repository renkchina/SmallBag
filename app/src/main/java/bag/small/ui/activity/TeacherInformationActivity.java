package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.entity.RegisterInfoBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IRegisterReq;
import bag.small.interfaze.IListDialog;
import bag.small.interfaze.IViewBinder;
import bag.small.provider.TeachClass;
import bag.small.provider.TeachSubject;
import bag.small.provider.TeachSubjectClass;
import bag.small.provider.TeachSubjectClassViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;

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

    MultiTypeAdapter multiTypeAdapter;
    List<Object> items;

    ListDialog listDialog;
    private IRegisterReq iRegisterReq;
    private List<RegisterInfoBean.SchoolBeanX> areaLists;
    private RegisterInfoBean.SchoolBeanX area;
    private RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean jie;
    private List<RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean.KecheBean> course;
    private TeachSubjectClassViewBinder viewBinder;

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
                    aGuardianTv.setText(area.getSchool().getName());
                });
                break;
            case R.id.activity_guardian_ll:
                break;
            case R.id.activity_charge_class_ll://是否班主任
                listDialog.setListData(getChoice());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> isClassTeacher.setText(content));
                break;
            case R.id.ac_teacher_number_tv:
                listDialog.setListData(getJieCi());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    acTeacherNumberTv.setText(content);
                    jie = area.getSchool().getBase().getJie().get(position);
                    acTeacherGradeTv.setText(jie.getNianji_name());
                });
                break;
            case R.id.ac_teacher_grade_tv:
                break;
            case R.id.ac_teacher_class_tv:
                listDialog.setListData(getBanji());
                listDialog.show(view);
                listDialog.setListDialog((position, content) -> {
                    acTeacherClassTv.setText(content);
                    course = jie.getKeche();
                });
                break;
            case R.id.activity_teacher_commit_btn:

                break;
        }
    }

    @Override
    public void add(int type, int position) {
        position += 1;
        if (type == 1) {
            items.add(position, new TeachSubject());
        } else if (type == 2) {
            items.add(position, new TeachClass());
        } else {
            items.add(new TeachSubjectClass(true));
        }
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(int position) {
        items.remove(position);
        multiTypeAdapter.notifyDataSetChanged();
    }


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

    private void setImages(List<ImageItem> images) {
        String path = "";
        if (ListUtil.unEmpty(images)) {
            path = images.get(0).getPath();
        }
        ImageUtil.loadCircleImages(this, mHeadImage, path);
        //
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

    private List<String> getArea() {
        List<String> lists = new ArrayList<>();
        if (ListUtil.unEmpty(areaLists)) {
            for (RegisterInfoBean.SchoolBeanX beanX : areaLists) {
                lists.add(beanX.getName());
            }
        }
        return lists;
    }

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
