package bag.small.ui.activity;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.dialog.BottomDialog;
import bag.small.dialog.BottomListDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.ChoiceClassLists;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.IInterestClass;
import bag.small.interfaze.IDialog;
import bag.small.provider.ChoiceClassListsBinder;
import bag.small.provider.ChoiceInterestSubjectViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ChoiceInterestClassActivity extends BaseActivity implements IDialog, OnBannerListener {
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.activity_interest_class_one_content_tv)
    TextView iOneContentTv;
    @BindView(R.id.activity_interest_class_one_del_ll)
    LinearLayout iOneDelLl;
    @BindView(R.id.activity_interest_class_two_content_tv)
    TextView iTwoContentTv;
    @BindView(R.id.activity_interest_class_two_del_ll)
    LinearLayout iTwoDelLl;
    @BindView(R.id.activity_interest_class_three_content_tv)
    TextView iThreeContentTv;
    @BindView(R.id.activity_interest_class_three_del_ll)
    LinearLayout iThreeDelLl;
    @BindView(R.id.activity_interest_class_commit_btn)
    TextView iCommitBtn;
    @BindView(R.id.activity_interest_class_list_recycler)
    RecyclerView iListRecycler;
    @BindView(R.id.activity_interest_student_class_tv)
    TextView mClassTv;
    @BindView(R.id.activity_interest_student_teacher_tv)
    TextView mTeacherTv;
    @BindView(R.id.activity_interest_student_time_tv)
    TextView mTimeTv;
    @BindView(R.id.activity_interest_student_classroom_tv)
    TextView mClassroomTv;
    @BindView(R.id.activity_student_show_class_ll)
    LinearLayout activityStudentShowClassLl;
    @BindView(R.id.activity_student_choice_ll)
    LinearLayout activityStudentChoiceLl;
    @BindView(R.id.interest_class_one_ll)
    LinearLayout interestClassOneLl;
    @BindView(R.id.interest_class_two_ll)
    LinearLayout interestClassTwoLl;
    @BindView(R.id.interest_class_three_ll)
    LinearLayout interestClassThreeLl;
    @BindView(R.id.interest_class_one_cv)
    CardView interestClassOneCv;
    @BindView(R.id.interest_class_two_cv)
    CardView interestClassTwoCv;
    @BindView(R.id.interest_class_three_cv)
    CardView interestClassThreeCv;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;

    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;
    IInterestClass iInterestClass;
    ChoiceClassLists.KechenBean firstKeChen;
    String firstId = "";
    String secondeId = "";
    String thirdId = "";
    int position = 0;
    BottomListDialog bottomListDialog;
    BottomDialog bottomDialog;
    List bannerImages;
    private IAdvertising iAdvertising;
    private List<AdvertisingBean> advertisingBeen;
    private NoticeDialogSnap noticeDialogSnap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_choice_interest_class;
    }

    @Override
    public void initData() {
        mItems = new Items();
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(ChoiceClassLists.KechenBean.class, new ChoiceInterestSubjectViewBinder());
        iListRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        iListRecycler.setAdapter(multiTypeAdapter);
        iInterestClass = HttpUtil.getInstance().createApi(IInterestClass.class);
        bannerImages = new ArrayList();
        advertisingBeen = new ArrayList<>(5);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        topBanner.setOnBannerListener(this);
        noticeDialogSnap = new NoticeDialogSnap(this);
        bottomListDialog = new BottomListDialog(this);
        bottomDialog = new BottomDialog(this);
        bottomDialog.setText("更换兴趣课", "删除");
    }

    @Override
    public void initView() {
        setToolTitle("兴趣课", true);
        toolbarRightTv.setText("保存");
        toolbarRightTv.setVisibility(View.VISIBLE);
        iInterestClass.getInterestsForStudent(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess()) {
                        setUiRefresh(listBaseBean.getData());
                    }
                }, new HttpError());
        getTopBannerImage();
    }

    private void setUiRefresh(ChoiceClassLists data) {
        if (data.isCan_xuan_ke()) {
            activityStudentChoiceLl.setVisibility(View.VISIBLE);
            activityStudentShowClassLl.setVisibility(View.GONE);
            if (data.getXuanke() != null && data.getXuanke().getFirst() != null &&
                    !TextUtils.isEmpty(data.getXuanke().getFirst().getCourse_name())) {
                StringUtil.setTextView(iOneContentTv, data.getXuanke().getFirst().getCourse_name());
                interestClassOneLl.setVisibility(View.GONE);
                interestClassOneCv.setVisibility(View.VISIBLE);
            } else {
                interestClassOneCv.setVisibility(View.GONE);
                interestClassOneLl.setVisibility(View.VISIBLE);
            }
            if (data.getXuanke() != null && data.getXuanke().getSecend() != null &&
                    !TextUtils.isEmpty(data.getXuanke().getSecend().getCourse_name())) {
                StringUtil.setTextView(iTwoContentTv, data.getXuanke().getSecend().getCourse_name());
                interestClassTwoLl.setVisibility(View.GONE);
                interestClassTwoCv.setVisibility(View.VISIBLE);
            } else {
                interestClassTwoCv.setVisibility(View.GONE);
                interestClassTwoLl.setVisibility(View.VISIBLE);
            }
            if (data.getXuanke() != null && data.getXuanke().getThird() != null &&
                    !TextUtils.isEmpty(data.getXuanke().getThird().getCourse_name())) {
                StringUtil.setTextView(iThreeContentTv, data.getXuanke().getThird().getCourse_name());
                interestClassThreeLl.setVisibility(View.GONE);
                interestClassThreeCv.setVisibility(View.VISIBLE);
            } else {
                interestClassThreeCv.setVisibility(View.GONE);
                interestClassThreeLl.setVisibility(View.VISIBLE);
            }
            if (ListUtil.unEmpty(data.getKechen())) {
                bottomListDialog.setListData(data.getKechen());
                mItems.addAll(data.getKechen());
                multiTypeAdapter.notifyDataSetChanged();
            }
        } else {
            activityStudentShowClassLl.setVisibility(View.VISIBLE);
            activityStudentChoiceLl.setVisibility(View.GONE);
            toolbarRightTv.setVisibility(View.GONE);
            ChoiceClassLists.ResultBean result = data.getResult();
            if (result != null) {
                StringUtil.setTextView(mClassTv, data.getResult().getName());
                StringUtil.setTextView(mTeacherTv, data.getResult().getTeacher());
                StringUtil.setTextView(mTimeTv, data.getResult().getClass_time());
                StringUtil.setTextView(mClassroomTv, data.getResult().getClass_room());
            }
        }
    }

    @OnClick({R.id.activity_interest_class_one_content_tv,
            R.id.activity_interest_class_one_del_ll,
            R.id.activity_interest_class_two_content_tv,
            R.id.activity_interest_class_two_del_ll,
            R.id.activity_interest_class_three_content_tv,
            R.id.activity_interest_class_three_del_ll,
            R.id.interest_class_one_ll,
            R.id.interest_class_two_ll,
            R.id.toolbar_right_tv,
            R.id.interest_class_three_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_interest_class_one_del_ll:
                position = 1;
                bottomDialog.show();
                break;
            case R.id.activity_interest_class_two_del_ll:
                position = 2;
                bottomDialog.show();
                break;
            case R.id.activity_interest_class_three_del_ll:
                position = 3;
                bottomDialog.show();
                break;
            case R.id.toolbar_right_tv:
                iInterestClass.getInterestsSubmit(UserPreferUtil.getInstanse().getRoleId(),
                        UserPreferUtil.getInstanse().getUserId(),
                        UserPreferUtil.getInstanse().getSchoolId(),
                        firstId, secondeId, thirdId)
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .compose(RxLifecycleCompact.bind(this).withObservable())
                        .subscribe(bean -> {
                            if (bean.isSuccess()) {
                                finish();
                            }
                            toast(bean.getMsg());
                        }, new HttpError());
                break;
            case R.id.interest_class_one_ll:
                position = 1;
                bottomListDialog.show();
                break;
            case R.id.interest_class_two_ll:
                position = 2;
                bottomListDialog.show();
                break;
            case R.id.interest_class_three_ll:
                position = 3;
                bottomListDialog.show();
                break;
        }
    }

    @Override
    public void register() {
        RxBus.get().register(this);
    }

    @Override
    public void unRegister() {
        RxBus.get().unRegister(this);
    }

    @MySubscribe(code = 99999)
    public void clickItem(ChoiceClassLists.KechenBean bean) {
        switch (position) {
            case 1:
                firstId = bean.getId();
                StringUtil.setTextView(iOneContentTv, bean.getName());
                interestClassOneCv.setVisibility(View.VISIBLE);
                interestClassOneLl.setVisibility(View.GONE);
                break;
            case 2:
                secondeId = bean.getId();
                StringUtil.setTextView(iTwoContentTv, bean.getName());
                interestClassTwoLl.setVisibility(View.GONE);
                interestClassTwoCv.setVisibility(View.VISIBLE);
                break;
            case 3:
                thirdId = bean.getId();
                StringUtil.setTextView(iThreeContentTv, bean.getName());
                interestClassThreeLl.setVisibility(View.GONE);
                interestClassThreeCv.setVisibility(View.VISIBLE);
                break;
        }
        bottomListDialog.dismiss();
    }

    //上,更换
    @Override
    public void callBackMethod(Object object, Object bean) {
        bottomListDialog.show();
    }

    //下，删除
    @Override
    public void callBackMiddleMethod() {
        switch (position) {
            case 1:
                iOneContentTv.setText("");
                interestClassOneCv.setVisibility(View.GONE);
                interestClassOneLl.setVisibility(View.VISIBLE);
                break;
            case 2:
                iTwoContentTv.setText("");
                interestClassTwoLl.setVisibility(View.VISIBLE);
                interestClassTwoCv.setVisibility(View.GONE);
                break;
            case 3:
                iThreeContentTv.setText("");
                interestClassThreeLl.setVisibility(View.VISIBLE);
                interestClassThreeCv.setVisibility(View.GONE);
                break;
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
