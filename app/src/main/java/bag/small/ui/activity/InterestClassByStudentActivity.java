package bag.small.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.utils.GlideImageLoader;
import butterknife.Bind;

public class InterestClassByStudentActivity extends BaseActivity {

    @Bind(R.id.activity_interest_class_student_banner)
    Banner iStudentBanner;
    @Bind(R.id.activity_interest_student_class_tv)
    TextView iStudentClassTv;
    @Bind(R.id.activity_interest_student_teacher_tv)
    TextView iStudentTeacherTv;
    @Bind(R.id.activity_interest_student_time_tv)
    TextView iStudentTimeTv;
    @Bind(R.id.activity_interest_student_classroom_tv)
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
        setBanner(iStudentBanner, bannerImages);

    }

    private void setBanner(Banner banner, List images) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        fBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
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
