package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import bag.small.R;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ImageUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/12.
 */
public class NoticeBannerViewBinder extends ItemViewBinder<NoticeBanner, NoticeBannerViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_notice_head_banner_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NoticeBanner noticeBanner) {
        holder.ImageIv.setImageResource(noticeBanner.getBannerImages());
        ImageUtil.loadImages(holder.fHeadImageIv.getContext(), holder.fHeadImageIv, noticeBanner.getHeadImage());
        StringUtil.setTextView(holder.name, UserPreferUtil.getInstanse().getUserName());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_growth_head_image_iv)
        ImageView fHeadImageIv;
        @BindView(R.id.banner_imageview)
        ImageView ImageIv;
        @BindView(R.id.head_banner_tv)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
