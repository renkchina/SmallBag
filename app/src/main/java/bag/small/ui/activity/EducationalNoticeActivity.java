package bag.small.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.utils.GlideImageLoader;
import butterknife.Bind;
import me.drakeet.multitype.MultiTypeAdapter;

public class EducationalNoticeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @Bind(R.id.activity_education_banner)
    Banner eBanner;
    @Bind(R.id.activity_education_tab_layout)
    TabLayout eTabLayout;
    @Bind(R.id.activity_education_recycler)
    RecyclerView eRecycler;

    private List<Object> bannerImages;
    private TabLayout.Tab tab1;
    private TabLayout.Tab tab2;
    private TabLayout.Tab tab3;
    private int currentPosition;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> items;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_educational_notice;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        items = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);

    }

    @Override
    public void initView() {
        setToolTitle("教务通知", true);
        setBanner(eBanner, bannerImages);
        tab1 = eTabLayout.newTab();
        tab2 = eTabLayout.newTab();
        tab3 = eTabLayout.newTab();
        tab1.setText("第一");
        tab2.setText("第二");
        tab3.setText("第三");
        eTabLayout.addTab(tab1);
        eTabLayout.addTab(tab2);
        eTabLayout.addTab(tab3);
        eTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始轮播
        eBanner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        eBanner.stopAutoPlay();
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
    public void onTabSelected(TabLayout.Tab tab) {
        if (currentPosition != tab.getPosition()) {
            getTab(tab.getPosition());
            currentPosition = tab.getPosition();
        }
    }

    //请求
    private void getTab(int position) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
