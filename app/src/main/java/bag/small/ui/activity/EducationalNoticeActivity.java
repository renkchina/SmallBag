package bag.small.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.AdvertisingDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.EducationNoticeBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.INotification;
import bag.small.provider.EducationNoticeViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;

public class EducationalNoticeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, OnBannerListener {
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.activity_education_tab_layout)
    TabLayout eTabLayout;
    @BindView(R.id.activity_education_recycler)
    RecyclerView eRecycler;

    private int currentPosition = -1;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> items;
    INotification iNotification;
    List<EducationNoticeBean> noticeList;
    List bannerImages;
    private IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;
    private List<AdvertisingBean> advertisingBeen;
    private NoticeDialogSnap noticeDialogSnap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_educational_notice;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        items = new ArrayList<>();
        noticeList = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(EducationNoticeBean.ResultsBean.class, new EducationNoticeViewBinder());
        iNotification = HttpUtil.getInstance().createApi(INotification.class);
        advertisingBeen = new ArrayList<>(5);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        advertisingDialog = new AdvertisingDialog(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
    }

    @Override
    public void initView() {
        setToolTitle("教务通知", true);
        eTabLayout.addOnTabSelectedListener(this);
        eRecycler.setLayoutManager(new LinearLayoutManager(this));
        eRecycler.setAdapter(multiTypeAdapter);
    }

    private void requestHttp() {
        iNotification.getNotice(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        if (ListUtil.unEmpty(bean.getData())) {
                            noticeList.clear();
                            noticeList.addAll(bean.getData());
                            setTitles(bean.getData());
                        } else {
                            toast("没有通知消息！");
                        }
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void setTitles(List<EducationNoticeBean> data) {
        int position = 0;
        eTabLayout.removeAllTabs();
        position = currentPosition;
        currentPosition = -1;
        if (ListUtil.unEmpty(data)) {
            int size = data.size();
            if (size < 21) {
                for (EducationNoticeBean bean : data) {
                    eTabLayout.addTab(eTabLayout.newTab().setText(bean.getLabel()));
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    eTabLayout.addTab(eTabLayout.newTab().setText(data.get(i).getLabel()));
                }
            }
            if (eTabLayout.getTabAt(position) != null) {
                eTabLayout.getTabAt(position).select();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始轮播
        requestHttp();
        getTopBannerImage();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        items.clear();
        items.addAll(noticeList.get(position).getResults());
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
