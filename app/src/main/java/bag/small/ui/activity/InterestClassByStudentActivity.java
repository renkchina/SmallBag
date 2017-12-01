package bag.small.ui.activity;

import android.widget.TextView;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.List;
import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.utils.LayoutUtil;
import butterknife.BindView;

public class InterestClassByStudentActivity extends BaseActivity {

    @BindView(R.id.activity_interest_class_student_banner)
    Banner iStudentBanner;
    @BindView(R.id.activity_interest_student_class_tv)
    TextView iStudentClassTv;
    @BindView(R.id.activity_interest_student_teacher_tv)
    TextView iStudentTeacherTv;
    @BindView(R.id.activity_interest_student_time_tv)
    TextView iStudentTimeTv;
    @BindView(R.id.activity_interest_student_classroom_tv)
    TextView iStudentClassroomTv;

    private List<Object> bannerImages;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_interest_class_by_student;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
    }

    @Override
    public void initView() {
        LayoutUtil.setBanner(iStudentBanner, bannerImages);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iStudentBanner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iStudentBanner.stopAutoPlay();
    }

}
