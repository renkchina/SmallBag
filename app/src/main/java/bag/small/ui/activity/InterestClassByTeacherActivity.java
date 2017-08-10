package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.TeacherClass;
import bag.small.provider.TeacherClassViewBinder;
import bag.small.utils.GlideImageLoader;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class InterestClassByTeacherActivity extends BaseActivity {

    @Bind(R.id.mbanner)
    Banner mbanner;
    @Bind(R.id.activity_interest_teacher_class_tv)
    TextView tClassTv;
    @Bind(R.id.activity_interest_teacher_time_tv)
    TextView tTimeTv;
    @Bind(R.id.activity_interest_teacher_classroom_tv)
    TextView tClassroomTv;
    @Bind(R.id.activity_interest_teacher_recycler)
    RecyclerView tRecycler;
    private List<Object> bannerImages;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_interest_class_by_teacher;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        mItems = new Items();
        mItems.add(new TeacherClass());
        mItems.add(new TeacherClass());
        mItems.add(new TeacherClass());
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(TeacherClass.class, new TeacherClassViewBinder());
        tRecycler.setLayoutManager(new LinearLayoutManager(this));
        tRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        tRecycler.setAdapter(multiTypeAdapter);
    }

    @Override
    public void initView() {
        setBanner(mbanner, bannerImages);
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
}
