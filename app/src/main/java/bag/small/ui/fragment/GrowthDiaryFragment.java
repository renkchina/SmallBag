package bag.small.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.dialog.EvaluateDialog;
import bag.small.entity.MomentsBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMoments;
import bag.small.interfaze.IDialog;
import bag.small.provider.MomentsViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static me.drakeet.multitype.MultiTypeAsserts.assertHasTheSameAdapter;

/**
 * Created by Administrator on 2017/7/22.
 */

public class GrowthDiaryFragment extends BaseFragment implements IDialog {
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.fragment_growth_root_refresh)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.fragment_growth_banner)
    Banner growthBanner;
    @Bind(R.id.fragment_growth_head_image_float)
    FloatingActionButton headImage;
    @Bind(R.id.fragment_growth_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    private List<Object> bannerImages;
    MultiTypeAdapter multiTypeAdapter;
    Items mItems;
    IMoments iMoments;
    private int pageIndex = 1;
    EvaluateDialog evaluateDialog;

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
        multiTypeAdapter.register(MomentsBean.class, new MomentsViewBinder(this));
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(refresh -> requestHTTP(pageIndex = 1, refresh));
        refreshLayout.setOnLoadmoreListener(refresh -> requestHTTP(++pageIndex, refresh));
        toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (verticalOffset == 0) {
                toolbarLayout.setTitle("");
            } else if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange()) {
                toolbarLayout.setTitle("");
            } else {
                toolbarLayout.setTitle("小书包");
            }
        });
        iMoments = HttpUtil.getInstance().createApi(IMoments.class);
        evaluateDialog = new EvaluateDialog(getContext());
        evaluateDialog.setiDialog(this);
    }

    @Override
    public void initView() {
        setToolTitle("", false);
        setBanner(growthBanner, bannerImages);
        ImageUtil.loadImages(getContext(), headImage, UserPreferUtil.getInstanse().getHeadImagePath());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(getContext(), R.color.un_enable_gray)));
        recyclerView.setAdapter(multiTypeAdapter);
        assertHasTheSameAdapter(recyclerView, multiTypeAdapter);
        requestHTTP(pageIndex, null);
    }

    private void requestHTTP(int page, RefreshLayout refresh) {
        iMoments.getMoments(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), page)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (refresh != null) {
                        refresh.finishRefresh();
                        refresh.finishLoadmore();
                    }
                    if (bean.isSuccess()) {
                        if (ListUtil.isEmpty(bean.getData())) {
                            return;
                        }
                        if (page > 1) {
                            mItems.clear();
                        }
                        mItems.addAll(bean.getData());
                        multiTypeAdapter.notifyDataSetChanged();
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError(refresh));
    }

    //第一次初始化不执行
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

    MomentsBean.RepayBean repayBean;
    String msgId;
    int msgPosition = -1;

    @Override
    public void callBackMethod(Object object, Object obj) {
        String evaluate = (String) object;
        String review = "0";
        if (repayBean != null && !TextUtils.isEmpty(repayBean.getReview_id())) {
            review = repayBean.getReview_id();
        }
        iMoments.getEvaluateMsg(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), 1, evaluate,
                review, msgId)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean1 -> {
                    if (bean1.isSuccess()) {
                        MomentsBean momentsBean = (MomentsBean) mItems.get(msgPosition);
                        momentsBean.setRepay(bean1.getData());
                        multiTypeAdapter.notifyDataSetChanged();
                    }
                    evaluateDialog.dismiss();
                    toast(bean1.getMsg());
                }, new HttpError());
    }

    public void showEvaluationL(int msgPosition, int repayPosition) {
        MomentsBean momentsBean = (MomentsBean) mItems.get(msgPosition);
        this.msgPosition = msgPosition;
        msgId = momentsBean.getId();
        if (repayPosition >= 0) {
            repayBean = momentsBean.getRepay().get(repayPosition);
        } else {
            repayBean = null;
        }

        if (repayBean != null && !TextUtils.isEmpty(repayBean.getReview_id())) {
            String hint = "回复" + repayBean.getTitle() + "的评论";
            evaluateDialog.setHint(hint);
        }
        evaluateDialog.show();
    }

    @Override
    public void callBackMiddleMethod() {

    }
}
