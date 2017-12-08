package bag.small.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import bag.small.R;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.rx.RxUtil;
import bag.small.ui.activity.WebViewActivity;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ImageUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
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
        Context context = holder.topBanner.getContext();
        LayoutUtil.setBanner(holder.topBanner, noticeBanner.getBannerImages());
        ImageUtil.loadImages(holder.fHeadImageIv.getContext(), holder.fHeadImageIv,
                UserPreferUtil.getInstanse().getHeadImagePath());
        StringUtil.setTextView(holder.name, UserPreferUtil.getInstanse().getUserName());
        holder.topBanner.setOnBannerListener(position -> {
            if (ListUtil.unEmpty(noticeBanner.getAdvertisingBeans())) {
                AdvertisingBean bean = noticeBanner.getAdvertisingBeans().get(position);
                if (TextUtils.isEmpty(bean.getUrl())) {
                    //弹框
                    HttpUtil.getInstance().createApi(IAdvertising.class)
                            .getAdvertisingsDetail(UserPreferUtil.getInstanse().getRoleId(),
                                    UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId(),
                                    bean.getAds_id(), bean.getCame_from())
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .subscribe(bean1 -> {
                                if (bean1.isSuccess()) {
                                    AdvertisingDetailBean detail = bean1.getData();
                                    NoticeDialogSnap noticeDialogSnap = new NoticeDialogSnap(context);
                                    noticeDialogSnap.show();
                                    noticeDialogSnap.setShowContent(detail.getTitle(), detail.getContent());
                                    noticeDialogSnap.setList(detail.getImages());
                                }
                            }, new HttpError());
                } else {
                    //网页
                    Intent intent = new Intent(context, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", bean.getUrl());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_growth_head_image_iv)
        ImageView fHeadImageIv;
        @BindView(R.id.top_banner)
        Banner topBanner;
        @BindView(R.id.head_banner_tv)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
