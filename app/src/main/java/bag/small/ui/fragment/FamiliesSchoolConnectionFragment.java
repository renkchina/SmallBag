package bag.small.ui.fragment;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.entity.ConnectionBinder;
import bag.small.provider.ConnectionViewBinder;
import bag.small.utils.GlideImageLoader;
import butterknife.Bind;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/7/22.
 */

public class FamiliesSchoolConnectionFragment extends BaseFragment {
    @Bind(R.id.fragment_family_banner)
    Banner fBanner;
    @Bind(R.id.fragment_family_grid_view)
    RecyclerView recyclerView;
    private List<Object> bannerImages;
    MultiTypeAdapter mAdapter;
    List<Object> mItemBeans;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_family_school_connection;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        mItemBeans = new ArrayList<>(9);
        ConnectionBinder biner1 = new ConnectionBinder();
        biner1.setId(1);
        biner1.setResImage(R.mipmap.beiwanglu_b);
        biner1.setTitle("备忘录");
        ConnectionBinder biner2 = new ConnectionBinder();
        biner2.setId(2);
        biner2.setResImage(R.mipmap.tongzhi_b);
        biner2.setTitle("教务通知");
        ConnectionBinder biner3 = new ConnectionBinder();
        biner3.setId(3);
        biner3.setResImage(R.mipmap.bingjia_b);
        biner3.setTitle("事/病假");
        ConnectionBinder biner4 = new ConnectionBinder();
        biner4.setId(4);
        biner4.setResImage(R.mipmap.kechengbiao_b);
        biner4.setTitle("课程表");
        ConnectionBinder biner5 = new ConnectionBinder();
        biner5.setId(5);
        biner5.setResImage(R.mipmap.chengzhang_b);
        biner5.setTitle("成长手册");
        ConnectionBinder biner6 = new ConnectionBinder();
        biner6.setId(6);
        biner6.setResImage(R.mipmap.zuoyeben_b);
        biner6.setTitle("作业本");
        ConnectionBinder biner7 = new ConnectionBinder();
        biner7.setId(7);
        biner7.setResImage(R.mipmap.shetuan_b);
        biner7.setTitle("社团");
        ConnectionBinder biner8 = new ConnectionBinder();
        biner8.setId(8);
        biner8.setResImage(R.mipmap.xingquke_b);
        biner8.setTitle("兴趣课");
        ConnectionBinder biner9 = new ConnectionBinder();
        biner9.setId(9);
        biner9.setResImage(R.mipmap.liuyan_b);
        biner9.setTitle("在线留言");
        mItemBeans.add(biner1);
        mItemBeans.add(biner2);
        mItemBeans.add(biner3);
        mItemBeans.add(biner4);
        mItemBeans.add(biner5);
        mItemBeans.add(biner6);
        mItemBeans.add(biner7);
        mItemBeans.add(biner8);
        mItemBeans.add(biner9);
        mAdapter = new MultiTypeAdapter(mItemBeans);
    }

    @Override
    public void initView() {
        setToolTitle("小书包", false);
        setBanner(fBanner, bannerImages);
        mAdapter.register(ConnectionBinder.class, new ConnectionViewBinder());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(mAdapter);
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

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = 0;
            }
        }

    }
}
