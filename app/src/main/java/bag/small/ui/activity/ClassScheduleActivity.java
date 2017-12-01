package bag.small.ui.activity;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.AdvertisingDialog;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.BaseBean;
import bag.small.entity.ClassScheduleBean;
import bag.small.entity.ClassScheduleItemBean;
import bag.small.entity.ImageString;
import bag.small.entity.WeekBean;
import bag.small.entity.WeeklyBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IClassSchedule;
import bag.small.http.IApi.ITeachClasses;
import bag.small.provider.ClassScheduleViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ClassScheduleActivity extends BaseActivity implements OnBannerListener {

    List items;
    MultiTypeAdapter multiTypeAdapter;
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.class_schedule_recycler)
    RecyclerView classScheduleRecycler;
    @BindView(R.id.class_schedule_tips_tv)
    TextView classScheduleTipsTv;
    IClassSchedule iClassSchedule;
    private String className;
    private String classId;
    List bannerImages;
    private List<AdvertisingBean> advertisingBeen;
    IAdvertising iAdvertising;
    private AdvertisingDialog advertisingDialog;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_class_schedule;
    }

    @Override
    public void initData() {
        className = getIntent().getStringExtra("class");
        classId = getIntent().getStringExtra("banji_id");
        if (!TextUtils.isEmpty(className)) {
            setToolTitle(className, true);
        } else {
            setToolTitle("课程表", true);
        }
        items = new Items(9);
        advertisingBeen = new ArrayList<>(8);
        bannerImages = new ArrayList<>(8);
        iClassSchedule = HttpUtil.getInstance().createApi(IClassSchedule.class);
        multiTypeAdapter = new MultiTypeAdapter(items);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        advertisingDialog = new AdvertisingDialog(this);
    }

    @Override
    public void initView() {
        classScheduleRecycler.setLayoutManager(new GridLayoutManager(this, 1));
        multiTypeAdapter.register(ClassScheduleItemBean.class, new ClassScheduleViewBinder());
        classScheduleRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(this, R.color.un_enable_gray)));
        classScheduleRecycler.setAdapter(multiTypeAdapter);
        initHttp();
        getTopBannerImage();
    }

    private void initHttp() {
        Observable<BaseBean<ClassScheduleBean>> obser;
        if (UserPreferUtil.getInstanse().isTeacher()) {
            obser = iClassSchedule.getClassScheduleTeacher(UserPreferUtil.getInstanse().getRoleId(),
                    UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER));
        } else {
            obser = iClassSchedule.getClassSchedule(UserPreferUtil.getInstanse().getRoleId(),
                    UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER));
        }
        obser.subscribe(baseBean -> {
            if (baseBean.isSuccess()) {
                if (baseBean.getData() != null) {
                    ClassScheduleBean bean = baseBean.getData();
                    if (ListUtil.unEmpty(bean.getExchanges())) {
                        StringBuilder sb = new StringBuilder();
                        List<String> exchange = bean.getExchanges();
                        int size = exchange.size();
                        for (int i = 0; i < size; i++) {
                            String s = exchange.get(i);
                            if (i > 0)
                                sb.append("\n");
                            sb.append(s);
                        }
                        classScheduleTipsTv.setText(sb.toString());
                    }

                    if (ListUtil.unEmpty(bean.getKechens()) && ListUtil.unEmpty(bean.getWeekly())) {
                        ClassScheduleItemBean itemBean;
                        ClassScheduleItemBean firstBean = new ClassScheduleItemBean();
                        firstBean.getWeeklyBean().add(new WeeklyBean(bean.getMonth()));
                        List<WeeklyBean> week = bean.getWeekly();
                        List<ClassScheduleBean.KechensBean> clazz = bean.getKechens();
                        for (WeeklyBean weeklyBean : week) {
                            firstBean.getWeeklyBean().add(weeklyBean);
                        }
                        items.add(firstBean);
                        for (ClassScheduleBean.KechensBean kechensBean : clazz) {
                            itemBean = new ClassScheduleItemBean();
                            itemBean.setSubject(kechensBean.getLabel());
                            itemBean.getItem().add(kechensBean.getWeek_1());
                            itemBean.getItem().add(kechensBean.getWeek_2());
                            itemBean.getItem().add(kechensBean.getWeek_3());
                            itemBean.getItem().add(kechensBean.getWeek_4());
                            itemBean.getItem().add(kechensBean.getWeek_5());
                            itemBean.getItem().add(kechensBean.getWeek_6());
                            itemBean.getItem().add(kechensBean.getWeek_7());
                            items.add(itemBean);
                        }
                        multiTypeAdapter.notifyDataSetChanged();
                    }
                }
            }
            toast(baseBean.getMsg());
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
                        List list = new ArrayList();
                        if (!TextUtils.isEmpty(detail.getContent())) {
                            list.add(detail.getContent());
                        }
                        if (ListUtil.unEmpty(detail.getImages())) {
                            for (String s : detail.getImages()) {
                                ImageString imageString = new ImageString();
                                imageString.setUrl(s);
                                list.add(imageString);
                            }
                        }
                        if (ListUtil.unEmpty(list)) {
                            advertisingDialog.setListData(list);
                            advertisingDialog.show(topBanner);
                        }
                    }
                }, new HttpError());
    }
}
