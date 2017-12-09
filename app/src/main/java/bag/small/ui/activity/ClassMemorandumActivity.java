package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
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
import bag.small.entity.ClassMemorandumBean;
import bag.small.entity.ImageString;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IGetMemorandumBy;
import bag.small.http.IApi.ITeachClasses;
import bag.small.provider.ClassMemorandumViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.functions.Action;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ClassMemorandumActivity extends BaseActivity implements OnBannerListener {

    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.activity_memorandum_root_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.memorandum_edit_float_image)
    ImageView mFloatImage;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;
    boolean isShowDel;
    private List<AdvertisingBean> advertisingBeen;
    IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;
    private ITeachClasses iTeachClasses;
    private int pageIndex;
    private String banjiId;
    private NoticeDialogSnap noticeDialogSnap;
    StringBuilder stringBuilder;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_memorandum_list;
    }

    @Override
    public void initData() {
        setToolTitle("备忘录", true);
        toolbarRightTv.setText("编辑");
        mFloatImage.setVisibility(View.VISIBLE);
        items = new Items();
        advertisingBeen = new ArrayList<>(5);
        bannerImages = new ArrayList<>(5);
        multiTypeAdapter = new MultiTypeAdapter(items);
        iTeachClasses = HttpUtil.getInstance().createApi(ITeachClasses.class);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        advertisingDialog = new AdvertisingDialog(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
        if (getIntent() != null) {
            banjiId = getIntent().getStringExtra("banji_id");
        }
    }

    @Override
    public void initView() {
        ClassMemorandumViewBinder viewBinder = new ClassMemorandumViewBinder();
        viewBinder.setClassId(banjiId);
        viewBinder.setTeacher(true);
        stringBuilder = new StringBuilder();
        multiTypeAdapter.register(ClassMemorandumBean.class, viewBinder);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);

        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(refresh -> requestHTTP(pageIndex = 1, refresh));
        refreshLayout.setOnLoadmoreListener(refresh -> requestHTTP(++pageIndex, refresh));
        getTopBannerImage();
        requestHTTP(pageIndex = 1, null);
    }

    private void requestHTTP(int page, RefreshLayout refresh) {
        if (TextUtils.isEmpty(banjiId)) {
            return;
        }
        iTeachClasses.getClassMemorandum(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), banjiId, page)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .doFinally(() -> {
                    if (refresh != null) {
                        refresh.finishRefresh();
                        refresh.finishLoadmore();
                    }
                })
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

    @OnClick({R.id.memorandum_edit_float_image, R.id.toolbar_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_tv:
                if (isShowDel) {
                    isShowDel = false;
                    String ids = stringBuilder.toString();
                    if (!TextUtils.isEmpty(ids)) {
                        deleteMemo(ids.substring(0, ids.length() - 1));
                    }
                    toolbarRightTv.setText("编辑");
                } else {
                    toolbarRightTv.setText("完成");
                    isShowDel = true;
                }
                setDelete(isShowDel);
                break;
            case R.id.memorandum_edit_float_image:
                goActivity(CreateMemorandumActivity.class);
                break;
        }
    }

    private void setDelete(boolean flag) {
        for (Object item : items) {
            if (item instanceof ClassMemorandumBean) {
                ((ClassMemorandumBean) item).setShowDel(flag);
            }
        }
        multiTypeAdapter.notifyDataSetChanged();
    }

    public void deleteMemo(String ids) {
        iTeachClasses.deleteMemorandum(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), banjiId, ids)
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .doFinally(() -> stringBuilder = new StringBuilder())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess() && listBaseBean.getData() != null) {
                        if (!listBaseBean.getData().isEmpty()) {
                            items.clear();
                            items.addAll(listBaseBean.getData());
                            multiTypeAdapter.notifyDataSetChanged();
                        }
                        toast(listBaseBean.getMsg());
                    }
                }, new HttpError());
    }

    @MySubscribe(code = 911, threadMode = ThreadMode.MAIN)
    public void getDeleteId(String string) {
        stringBuilder.append(string).append(",");
    }

    @Override
    public void register() {
        RxBus.get().register(this);
    }

    @Override
    public void unRegister() {
        RxBus.get().unRegister(this);
    }
}
