package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.BaseBean;
import bag.small.entity.TeacherClass;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IInterestClass;
import bag.small.provider.InterestClassViewBinder;
import bag.small.provider.TeacherClassViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class InterestClassByTeacherActivity extends BaseActivity implements OnBannerListener {
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.activity_interest_teacher_class_tv)
    TextView tClassTv;
    @BindView(R.id.activity_interest_teacher_time_tv)
    TextView tTimeTv;
    @BindView(R.id.activity_interest_teacher_classroom_tv)
    TextView tClassroomTv;
    @BindView(R.id.activity_interest_teacher_recycler)
    RecyclerView tRecycler;
    @BindView(R.id.interest_by_teacher_show_ll)
    LinearLayout teacherShowLl;
    @BindView(R.id.interest_title_tv)
    TextView textView;

    @BindView(R.id.interest_empty_ll)
    LinearLayout emptyLL;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;
    IInterestClass iInterestClass;
    List bannerImages;
    private IAdvertising iAdvertising;
    private List<AdvertisingBean> advertisingBeen;
    private NoticeDialogSnap noticeDialogSnap;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_interest_class_by_teacher;
    }

    @Override
    public void initData() {
        mItems = new Items();
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(TeacherClass.ClassBean.class, new InterestClassViewBinder(this));
        multiTypeAdapter.register(TeacherClass.ClassBean.StudentsBean.class, new TeacherClassViewBinder());
        tRecycler.setLayoutManager(new LinearLayoutManager(this));
        tRecycler.setAdapter(multiTypeAdapter);
        iInterestClass = HttpUtil.getInstance().createApi(IInterestClass.class);
        bannerImages = new ArrayList();
        advertisingBeen = new ArrayList<>(5);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
    }

    @Override
    public void initView() {
        setToolTitle("兴趣课", true);
        iInterestClass.getInterestsForTeacher(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess() && ListUtil.unEmpty(listBaseBean.getData().getClassX())) {
                        teacherShowLl.setVisibility(View.GONE);
                        emptyLL.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        mItems.clear();
                        mItems.addAll(listBaseBean.getData().getClassX());
                        multiTypeAdapter.notifyDataSetChanged();
                    } else {
                        textView.setVisibility(View.GONE);
                        emptyLL.setVisibility(View.VISIBLE);
                        teacherShowLl.setVisibility(View.GONE);
                    }
                }, new HttpError());
        getTopBannerImage();
    }

    public void setShowStudents(String id) {
        if (!TextUtils.isEmpty(id)) {
            iInterestClass.getInterestsForTeachersStudent(id, UserPreferUtil.getInstanse().getSchoolId())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(bean -> {
                        if (bean.isSuccess() && bean.getData() != null) {
                            teacherShowLl.setVisibility(View.VISIBLE);
                            emptyLL.setVisibility(View.GONE);
                            StringUtil.setTextView(tClassTv, bean.getData().getName());
                            StringUtil.setTextView(tTimeTv, bean.getData().getClass_time());
                            StringUtil.setTextView(tClassroomTv, bean.getData().getClass_room());
                            mItems.clear();
                            mItems.add(new TeacherClass.ClassBean.StudentsBean());
                            mItems.addAll(bean.getData().getStudents());
                            multiTypeAdapter.notifyDataSetChanged();
                        }
                    }, new HttpError());


        }
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
