package bag.small.ui.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ImageUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/7/22.
 */

public class GrowthDiaryFragment extends BaseFragment {
    @Bind(R.id.fragment_growth_banner)
    Banner growthBanner;
    @Bind(R.id.fragment_growth_head_image_iv)
    ImageView headImage;
    @Bind(R.id.fragment_growth_recycler)
    RecyclerView recyclerView;
    private List<Object> bannerImages;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_growth_diary;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        mItems = new Items();
        multiTypeAdapter = new MultiTypeAdapter(mItems);
    }

    @Override
    public void initView() {
        setToolTitle("小书包", false);
        setBanner(growthBanner, bannerImages);
        ImageUtil.loadImages(getContext(), headImage, UserPreferUtil.getInstanse().getHeadImagePath());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(getContext(), R.color.un_enable_gray)));
        recyclerView.setAdapter(multiTypeAdapter);
    }

    @Override
    public void onFragmentShow() {
        growthBanner.startAutoPlay();
    }

    @Override
    public void onFragmentHide() {
        growthBanner.stopAutoPlay();
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
