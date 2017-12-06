package bag.small.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.AdvertisingDialog;
import bag.small.dialog.BottomDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.ImageString;
import bag.small.entity.MemorandumItemBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IMemorandum;
import bag.small.interfaze.IDialog;
import bag.small.provider.MemorandumTxtViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class StudentMemorandumActivity extends BaseActivity implements IDialog, OnBannerListener {

    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    @BindView(R.id.memorandum_subject_title_tv)
    TextView subjectTitle;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;
    BottomDialog bottomDialog;
    IMemorandum iMemorandum;
    private IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;
    private List<AdvertisingBean> advertisingBeen;

    boolean mSort;
    private ProgressDialog progressDialog;
    private NoticeDialogSnap noticeDialogSnap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_memorandum;
    }

    @Override
    public void initData() {
        setToolTitle("备忘录", true);
        toolbarRightIv.setVisibility(View.VISIBLE);
        toolbarRightIv.setImageResource(R.mipmap.sortby);
        items = new Items();
        iMemorandum = HttpUtil.getInstance().createApi(IMemorandum.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在切换，请等待...");
        progressDialog.setCanceledOnTouchOutside(false);
        bannerImages = new ArrayList(5);
        bottomDialog = new BottomDialog(this);
        advertisingBeen = new ArrayList<>(5);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
        advertisingDialog = new AdvertisingDialog(this);
    }

    @Override
    public void initView() {
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(MemorandumItemBean.class, new MemorandumTxtViewBinder());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(multiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getSubjectMemorandum();
        getTopBannerImage();
    }

    private void getSubjectMemorandum() {
        mSort = true;
        progressDialog.show();
        subjectTitle.setText("科目排序");
        iMemorandum.getSubjectMemorandum(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .doFinally(() -> progressDialog.dismiss())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess()) {
                        items.clear();
                        if (listBaseBean != null && ListUtil.unEmpty(listBaseBean.getData())) {
                            items.addAll(listBaseBean.getData());
                        }
                        multiTypeAdapter.notifyDataSetChanged();
                    }
                }, new HttpError());
    }


    @OnClick(R.id.toolbar_right_iv)
    public void onViewClicked() {
        bottomDialog.show();
    }

    //学科排序
    @Override
    public void callBackMethod(Object object, Object bean) {
        if (!mSort) {
            getSubjectMemorandum();
        }
    }

    //时间排序
    @Override
    public void callBackMiddleMethod() {
        if (mSort) {
            getTimeMemorandum();
        }
    }

    private void getTimeMemorandum() {
        mSort = false;
        progressDialog.show();
        subjectTitle.setText("时间排序");
        iMemorandum.getTimeMemorandum(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .doFinally(() -> progressDialog.dismiss())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess()) {
                        items.clear();
                        if (listBaseBean != null && ListUtil.unEmpty(listBaseBean.getData())) {
                            items.addAll(listBaseBean.getData());
                        }
                        multiTypeAdapter.notifyDataSetChanged();
                    }
                }, new HttpError());
    }

    private void getTopBannerImage() {
        iAdvertising.getAdvertisings(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    List<AdvertisingBean> list = bean.getData();
                    if (bean.isSuccess() && ListUtil.unEmpty(list)) {
                        advertisingBeen.clear();
                        bannerImages.clear();
                        advertisingBeen.addAll(list);
                        for (AdvertisingBean advertising : list) {
                            bannerImages.add(advertising.getImg());
                        }
                    } else {
                        bannerImages.add(R.mipmap.banner_icon1);
                        bannerImages.add(R.mipmap.banner_icon2);
                    }
                    LayoutUtil.setBanner(topBanner, bannerImages);
                }, new HttpError());
    }


    @Override
    public void OnBannerClick(int position) {
        LogUtil.show("position: " + position);
        AdvertisingBean bean = advertisingBeen.get(position);
        if (TextUtils.isEmpty(bean.getUrl())) {
            //弹框
            getBannerDetail(bean.getAds_id(), bean.getCame_from());
        } else {
            //网页
            Bundle bundle = new Bundle();
            bundle.putString("url", bean.getUrl());
            goActivity(WebViewActivity.class, bundle);
        }
    }

    private void getBannerDetail(int absId, String comeFrom) {
        iAdvertising.getAdvertisingsDetail(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId(),
                absId, comeFrom)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        AdvertisingDetailBean detail = bean.getData();
                        noticeDialogSnap.show();
                        noticeDialogSnap.setShowContent(detail.getTitle(), detail.getContent());
                        noticeDialogSnap.setList(detail.getImages());
                    }
                }, new HttpError());
    }
}

