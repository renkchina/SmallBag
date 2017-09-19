package bag.small.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.NoticeDetailBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.INotification;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ImageUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class NoticeDetailActivity extends BaseActivity {

//    @Bind(R.id.activity_notice_detail_banner)
//    Banner mDetailBanner;
    @Bind(R.id.activity_notice_title_tv)
    TextView mTitleTv;
    @Bind(R.id.activity_notice_time_tv)
    TextView mTimeTv;
    @Bind(R.id.activity_notice_image_iv)
    ImageView mImageIv;
    @Bind(R.id.activity_notice_content_tv)
    TextView mContentTv;
    private List<Object> bannerImages;
    INotification iNotification;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        iNotification = HttpUtil.getInstance().createApi(INotification.class);
    }

    @Override
    public void initView() {
        setToolTitle("教务通知", true);
//        setBanner(mDetailBanner, bannerImages);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getNoticeDetail(bundle.getInt("notify_id", 0));
        }

    }

    private void getNoticeDetail(int id) {
        if (id < 1) {
            toast("notify_id错误");
            finish();
            return;
        }
        iNotification.getNoticeDetail(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), id)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        setUi(bean.getData().getNotice());
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());

    }

    private void setUi(NoticeDetailBean.NoticeBean notice) {
        StringUtil.setTextView(mTitleTv, notice.getTitle(), "");
        StringUtil.setTextView(mTimeTv, notice.getCreate_at(), "");
        if (!TextUtils.isEmpty(notice.getContent())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mContentTv.setText(Html.fromHtml(notice.getContent(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mContentTv.setText(Html.fromHtml(notice.getContent()));
            }
        }

        if (!TextUtils.isEmpty(notice.getImage())) {
            mImageIv.setVisibility(View.VISIBLE);
            ImageUtil.loadImages(this, mImageIv, notice.getImage());
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
