package bag.small.ui.activity;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.BaseBean;
import bag.small.entity.ChoiceClassLists;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IInterestClass;
import bag.small.interfaze.IRecyclerListener;
import bag.small.provider.ChoiceClassListsBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ChoiceInterestClassActivity extends BaseActivity {
    @Bind(R.id.mbanner)
    Banner iStudentBanner;
    @Bind(R.id.activity_interest_class_one_content_tv)
    TextView iOneContentTv;
    @Bind(R.id.activity_interest_class_one_del_iv)
    ImageView iOneDelIv;
    @Bind(R.id.activity_interest_class_two_content_tv)
    TextView iTwoContentTv;
    @Bind(R.id.activity_interest_class_two_del_iv)
    ImageView iTwoDelIv;
    @Bind(R.id.activity_interest_class_three_content_tv)
    TextView iThreeContentTv;
    @Bind(R.id.activity_interest_class_three_del_iv)
    ImageView iThreeDelIv;
    @Bind(R.id.activity_interest_class_commit_btn)
    Button iCommitBtn;
    @Bind(R.id.activity_interest_class_list_recycler)
    RecyclerView iListRecycler;
    @Bind(R.id.activity_interest_student_class_tv)
    TextView mClassTv;
    @Bind(R.id.activity_interest_student_teacher_tv)
    TextView mTeacherTv;
    @Bind(R.id.activity_interest_student_time_tv)
    TextView mTimeTv;
    @Bind(R.id.activity_interest_student_classroom_tv)
    TextView mClassroomTv;
    @Bind(R.id.activity_student_show_class_ll)
    LinearLayout activityStudentShowClassLl;
    @Bind(R.id.activity_student_choice_ll)
    LinearLayout activityStudentChoiceLl;
    private List<Object> bannerImages;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;
    IInterestClass iInterestClass;
    ChoiceClassLists.KechenBean firstKeChen;
    String firstId = "";
    String secondeId = "";
    String thirdId = "";


    @Override
    public int getLayoutResId() {
        return R.layout.activity_choice_interest_class;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        mItems = new Items();
        firstKeChen = new ChoiceClassLists.KechenBean("", "兴趣课名", "上课教室", "上课时间", "授课老师");
        mItems.add(firstKeChen);
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(ChoiceClassLists.KechenBean.class, new ChoiceClassListsBinder());
        iListRecycler.setLayoutManager(new LinearLayoutManager(this));
        iListRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        iListRecycler.setAdapter(multiTypeAdapter);
        iInterestClass = HttpUtil.getInstance().createApi(IInterestClass.class);
    }

    @Override
    public void initView() {
        setToolTitle("兴趣课", true);
        setBanner(iStudentBanner, bannerImages);
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
    }

    private void setUiRefresh(ChoiceClassLists data) {
        if (data.isCan_xuan_ke()) {
            activityStudentChoiceLl.setVisibility(View.VISIBLE);
            activityStudentShowClassLl.setVisibility(View.GONE);
            if (ListUtil.unEmpty(data.getKechen())) {
                mItems.addAll(data.getKechen());
                multiTypeAdapter.notifyDataSetChanged();
            }
        } else {
            activityStudentShowClassLl.setVisibility(View.VISIBLE);
            activityStudentChoiceLl.setVisibility(View.GONE);
            ChoiceClassLists.ResultBean result = data.getResult();
            if(result!=null){
                StringUtil.setTextView(mClassTv, data.getResult().getName());
                StringUtil.setTextView(mTeacherTv, data.getResult().getTeacher());
                StringUtil.setTextView(mTimeTv, data.getResult().getClass_time());
                StringUtil.setTextView(mClassroomTv, data.getResult().getClass_room());
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        iStudentBanner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iStudentBanner.stopAutoPlay();
    }

    @OnClick({R.id.activity_interest_class_one_content_tv,
            R.id.activity_interest_class_one_del_iv,
            R.id.activity_interest_class_two_content_tv,
            R.id.activity_interest_class_two_del_iv,
            R.id.activity_interest_class_three_content_tv,
            R.id.activity_interest_class_three_del_iv,
            R.id.activity_interest_class_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_interest_class_one_content_tv:
                break;
            case R.id.activity_interest_class_one_del_iv:
                iOneContentTv.setText("");
//                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_two_content_tv:
                break;
            case R.id.activity_interest_class_two_del_iv:
                iTwoContentTv.setText("");
//                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_three_content_tv:
                break;
            case R.id.activity_interest_class_three_del_iv:
                iThreeContentTv.setText("");
//                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_commit_btn:
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

    @MySubscribe(code = 9999)
    public void clickItem(ChoiceClassLists.KechenBean bean) {
        if (TextUtils.isEmpty(StringUtil.EditGetString(iOneContentTv))) {
            firstId = bean.getId();
            StringUtil.setTextView(iOneContentTv, bean.getName());
        } else if (TextUtils.isEmpty(StringUtil.EditGetString(iTwoContentTv))) {
            secondeId = bean.getId();
            StringUtil.setTextView(iTwoContentTv, bean.getName());
        } else if (TextUtils.isEmpty(StringUtil.EditGetString(iThreeContentTv))) {
            thirdId = bean.getId();
            StringUtil.setTextView(iThreeContentTv, bean.getName());
        }
    }
}
