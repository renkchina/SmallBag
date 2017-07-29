package bag.small.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import butterknife.Bind;
import butterknife.OnClick;

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

    @Override
    public int getLayoutResId() {
        return R.layout.activity_parent_infomation;
    }

    @Override
    public void initView() {
        listDiaolg = new ListDialog(this);
        getRegisterData();
    }

    private void getRegisterData() {

    }

    @OnClick({R.id.activity_head_image, R.id.activity_area_school_ll,
            R.id.activity_study_school_ll, R.id.activity_study_num_ll,
            R.id.activity_grade_ll, R.id.activity_class_ll,
            R.id.activity_guardian_ll, R.id.activity_parent_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_head_image:
                break;
            case R.id.activity_area_school_ll:
                break;
            case R.id.activity_study_school_ll:
                break;
            case R.id.activity_study_num_ll:
                break;
            case R.id.activity_grade_ll:
                break;
            case R.id.activity_class_ll:
                break;
            case R.id.activity_guardian_ll:
                break;
            case R.id.activity_parent_commit_btn:
                break;
        }
    }
}
