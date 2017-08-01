package bag.small.ui.activity;


import android.text.TextUtils;
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
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class ParentInformationActivity extends BaseActivity {

    @Bind(R.id.activity_head_image)
    ImageView pHeadImage;
    @Bind(R.id.activity_student_name_edt)
    EditText pStudentNameEdt;
    @Bind(R.id.activity_student_number_edt)
    EditText pStudentNumberEdt;
    @Bind(R.id.activity_area_school_tv)
    TextView pAreaSchoolTv;
    @Bind(R.id.activity_area_school_ll)
    LinearLayout pAreaSchoolLl;
    @Bind(R.id.activity_study_school_tv)
    TextView pStudySchoolTv;
    @Bind(R.id.activity_study_school_ll)
    LinearLayout pStudySchoolLl;
    @Bind(R.id.activity_study_num_tv)
    TextView pStudyNumTv;
    @Bind(R.id.activity_study_num_ll)
    LinearLayout pStudyNumLl;
    @Bind(R.id.activity_grade_tv)
    TextView pGradeTv;
    @Bind(R.id.activity_grade_ll)
    LinearLayout pGradeLl;
    @Bind(R.id.activity_class_tv)
    TextView pClassTv;
    @Bind(R.id.activity_class_ll)
    LinearLayout pClassLl;
    @Bind(R.id.activity_guardian_tv)
    TextView pGuardianTv;
    @Bind(R.id.activity_guardian_ll)
    LinearLayout pGuardianLl;
    @Bind(R.id.activity_parent_name_edt)
    EditText pParentNameEdt;
    @Bind(R.id.activity_parent_phone_edit)
    EditText pParentPhoneEdit;
    @Bind(R.id.activity_parent_commit_btn)
    Button pParentCommitBtn;
    private ListDialog listDiaolg;
    private IRegisterReq iRegisterReq;
    private List<RegisterInfoBean.SchoolBeanX> areaLists;
    private RegisterInfoBean.SchoolBeanX area;
    private RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean jie;
    private List<RegisterInfoBean.SchoolBeanX.SchoolBean.BaseBean.JieBean.KecheBean> course;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_parent_infomation;
    }

    @Override
    public void initView() {
        listDiaolg = new ListDialog(this);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        getRegisterInfo();
    }

    @OnClick({R.id.activity_head_image, R.id.activity_area_school_ll,
            R.id.activity_study_school_ll, R.id.activity_study_num_ll,
            R.id.activity_grade_ll, R.id.activity_class_ll,
            R.id.activity_guardian_ll, R.id.activity_parent_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_head_image:
                RxPicker.of()
                        .single(true)
                        .camera(true)
                        .start(this)
                        .subscribe(this::setImages);
                break;
            case R.id.activity_area_school_ll:
                listDiaolg.setListData(getArea());
                listDiaolg.show(view);
                listDiaolg.setListDialog((position, content) -> {
                    pAreaSchoolTv.setText(content);
                    area = areaLists.get(position);
                    pStudySchoolTv.setText(area.getSchool().getName());
                });
                break;
            case R.id.activity_study_school_ll:
                break;
            case R.id.activity_study_num_ll:
                listDiaolg.setListData(getJieCi());
                listDiaolg.show(view);
                listDiaolg.setListDialog((position, content) -> {
                    pStudyNumTv.setText(content);
                    jie = area.getSchool().getBase().getJie().get(position);
                    pGradeTv.setText(jie.getNianji_name());
                });
                break;
            case R.id.activity_grade_ll:
                break;
            case R.id.activity_class_ll:
                listDiaolg.setListData(getBanji());
                listDiaolg.show(view);
                listDiaolg.setListDialog((position, content) -> {
                    pClassTv.setText(content);
                    course = jie.getKeche();
                });
                break;
            case R.id.activity_guardian_ll:
                listDiaolg.setListData(getChoice());
                listDiaolg.show(view);
                listDiaolg.setListDialog((position, content) -> pGuardianTv.setText(content));
                break;
            case R.id.activity_parent_commit_btn:
                break;
        }
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
        ImageUtil.loadCircleImages(this, pHeadImage, path);
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

    private List<String> getChoice() {
        List<String> lists = new ArrayList<>();
        lists.add("父亲");
        lists.add("母亲");
        return lists;
    }

}
