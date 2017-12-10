package bag.small.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.AdvertisingDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.ImageString;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.ITeachClasses;
import bag.small.provider.TeacherMemorandumViewBinder;
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

public class TeacherMemorandumActivity extends BaseActivity implements OnBannerListener {

    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.memorandum_edit_float_image)
    ImageView mFloatImage;
    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;
    ITeachClasses iTeachClasses;
    private List<AdvertisingBean> advertisingBeen;
    IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;
    private boolean isClass;
    private NoticeDialogSnap noticeDialogSnap;

//    private String banjiId = "";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_memorandum;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            isClass = intent.getBooleanExtra("isClass", false);
        }
        if (isClass) {
            setToolTitle("课程表", true);
            mFloatImage.setVisibility(View.GONE);
        } else {
            setToolTitle("备忘录", true);
            mFloatImage.setVisibility(View.VISIBLE);
        }
        items = new Items();
        advertisingBeen = new ArrayList<>(5);
        bannerImages = new ArrayList<>(5);
        multiTypeAdapter = new MultiTypeAdapter(items);
        iTeachClasses = HttpUtil.getInstance().createApi(ITeachClasses.class);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        advertisingDialog = new AdvertisingDialog(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
    }

    @Override
    public void initView() {
        TeacherMemorandumViewBinder teacher = new TeacherMemorandumViewBinder();
        teacher.setClass(isClass);
        multiTypeAdapter.register(TeacherMemorandumBean.class, teacher);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);
        initHttp();
        getTopBannerImage();
    }

    private void initHttp() {
        iTeachClasses.getTeachClasses(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess() && listBaseBean.getData() != null) {
                        if (!listBaseBean.getData().isEmpty()) {
                            items.clear();
                            items.addAll(listBaseBean.getData());
                            multiTypeAdapter.notifyDataSetChanged();
                        }
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


    @OnClick(R.id.memorandum_edit_float_image)
    public void onViewClicked() {
        goActivity(CreateMemorandumActivity.class);
    }
}
