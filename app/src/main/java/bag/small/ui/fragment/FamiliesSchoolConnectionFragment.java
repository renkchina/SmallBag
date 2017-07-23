package bag.small.ui.fragment;

import android.widget.GridView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.utils.GlideImageLoader;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/22.
 */

public class FamiliesSchoolConnectionFragment extends BaseFragment {
    @Bind(R.id.fragment_family_banner)
    Banner fBanner;
    @Bind(R.id.fragment_family_grid_view)
    GridView fGridView;
    List mItemBeans;
    private List<String> bannerImages;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_family_school_connection;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add("http://www.jrhcw.com/images/attachement/jpg/site102/20151228/fcaa14683b9a17eaf17c06.jpg");
        bannerImages.add("http://www.jrhcw.com/images/attachement/jpg/site102/20151228/fcaa14683b9a17eaf1cd4b.jpg");
        bannerImages.add("http://www.jrhcw.com/images/attachement/jpg/site102/20151228/fcaa14683b9a17eaf18b0b.jpg");
        setBanner(fBanner, bannerImages);
    }


    @Override
    public void initView() {

    }

    @Override
    public void onFragmentShow() {
        //开始轮播
        fBanner.startAutoPlay();
    }

    @Override
    public void onFragmentHide() {
        //结束轮播
        fBanner.stopAutoPlay();
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
