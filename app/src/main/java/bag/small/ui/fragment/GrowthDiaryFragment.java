package bag.small.ui.fragment;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseFragment;
import bag.small.dialog.EvaluateDialog;
import bag.small.entity.MomentsBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMoments;
import bag.small.interfaze.IDialog;
import bag.small.provider.MomentsViewBinder;
import bag.small.provider.NoticeBanner;
import bag.small.provider.NoticeBannerViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/7/22.
 */

public class GrowthDiaryFragment extends BaseFragment implements IDialog {
    @BindView(R.id.fragment_growth_root_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fragment_growth_recycler)
    RecyclerView recyclerView;
    MultiTypeAdapter multiTypeAdapter;
    Items mItems;
    IMoments iMoments;

    private int pageIndex = 1;
    EvaluateDialog evaluateDialog;
    NoticeBanner noticeBanner;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_growth_diary;
    }

    @Override
    public void initData() {
        mItems = new Items();
        noticeBanner = new NoticeBanner();
        noticeBanner.setHeadImage(UserPreferUtil.getInstanse().getHeadImagePath());
        mItems.add(noticeBanner);
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(NoticeBanner.class, new NoticeBannerViewBinder());
        multiTypeAdapter.register(MomentsBean.class, new MomentsViewBinder(this));
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refresh -> requestHTTP(pageIndex = 1, refresh));
        refreshLayout.setOnLoadmoreListener(refresh -> requestHTTP(++pageIndex, refresh));
        iMoments = HttpUtil.getInstance().createApi(IMoments.class);
        evaluateDialog = new EvaluateDialog(getContext());
        evaluateDialog.setiDialog(this);
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        recyclerView.setAdapter(multiTypeAdapter);
        requestHTTP(pageIndex, null);
    }

    private void requestHTTP(int page, RefreshLayout refresh) {
        iMoments.getMoments(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), page)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .doFinally(() -> {
                    if (refresh != null) {
                        refresh.finishRefresh();
                        refresh.finishLoadmore();
                    }
                    multiTypeAdapter.notifyDataSetChanged();
                })
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        if (page == 1) {
                            mItems.clear();
                            mItems.add(noticeBanner);
                        }
                        if (ListUtil.unEmpty(bean.getData())) {
                            mItems.addAll(bean.getData());
                        }
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    //第一次初始化不执行
    @Override
    public void onFragmentShow() {
        setHttp();
    }

    private void setHttp() {
        pageIndex = 1;
        noticeBanner.setBannerImages(MyApplication.bannerImage);
        noticeBanner.setHeadImage(UserPreferUtil.getInstanse().getHeadImagePath());
        requestHTTP(pageIndex, null);
    }

    @Override
    public void onFragmentHide() {
//        growthBanner.stopAutoPlay();
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
        evaluateDialog.clear();
        evaluateDialog.dismiss();
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

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space = 1;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
            int position = parent.getChildLayoutPosition(view);
            if (position == 0) {
                outRect.bottom = 0;
            }
        }

    }

}
