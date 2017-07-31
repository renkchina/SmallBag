package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.provider.TeachClass;
import bag.small.provider.TeachClassViewBinder;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

public class TeacherInformationActivity extends BaseActivity {


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
    MultiTypeAdapter multiTypeAdapter;
    List items;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_information;
    }

    @Override
    public void initView() {
        items = new ArrayList();
        items.add(new TeachClass());
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(TeachClass.class, new TeachClassViewBinder());
        subjectRecycler.setLayoutManager(new LinearLayoutManager(this));
        subjectRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        subjectRecycler.setAdapter(multiTypeAdapter);
    }

    @OnClick({R.id.activity_guardian_ll, R.id.activity_charge_class_ll,
            R.id.ac_teacher_number_tv, R.id.ac_teacher_grade_tv,
            R.id.ac_teacher_class_tv, R.id.activity_teacher_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_guardian_ll:
                break;
            case R.id.activity_charge_class_ll:
                break;
            case R.id.ac_teacher_number_tv:
                break;
            case R.id.ac_teacher_grade_tv:
                break;
            case R.id.ac_teacher_class_tv:
                break;
            case R.id.activity_teacher_commit_btn:
                break;
        }
    }
}
