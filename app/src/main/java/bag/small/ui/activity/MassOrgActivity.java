package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import bag.small.entity.BaseBean;
import bag.small.entity.MassOrgBean;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IMChats;
import bag.small.interfaze.IDialog;
import bag.small.provider.MassOrgViewBinder;
import bag.small.provider.TeacherMemorandumViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/11/24.
 */

public class MassOrgActivity extends BaseActivity implements IDialog, OnBannerListener {
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    IMChats imChats;

    List bannerImages;
    private IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;
    private List<AdvertisingBean> advertisingBeen;
    private NoticeDialogSnap noticeDialogSnap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_memorandum;
    }

    @Override
    public void initData() {
        setToolTitle("社团", true);
        items = new Items();
        imChats = HttpUtil.getInstance().createApi(IMChats.class);
        multiTypeAdapter = new MultiTypeAdapter(items);
        bannerImages = new ArrayList();
        advertisingBeen = new ArrayList<>(5);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
        advertisingDialog = new AdvertisingDialog(this);
    }

    @Override
    public void initView() {
        multiTypeAdapter.register(MassOrgBean.class, new MassOrgViewBinder());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);
        imChats.getUserGroup(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(massOrgBean -> {
                    if (massOrgBean.isSuccess()) {
                        if (ListUtil.unEmpty(massOrgBean.getData())) {
                            items.clear();
                            items.addAll(massOrgBean.getData());
                        }
                        multiTypeAdapter.notifyDataSetChanged();
                    } else {
                        toast(massOrgBean.getMsg());
                    }
                }, new HttpError());
        getTopBannerImage();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void callBackMethod(Object object, Object bean) {

    }

    @Override
    public void callBackMiddleMethod() {

    }
}
