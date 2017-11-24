package bag.small.ui.activity;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.ChoiceClassLists;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IInterestClass;
import bag.small.provider.ChoiceClassListsBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ChoiceInterestClassActivity extends BaseActivity {
    @BindView(R.id.ac_choice_student_banner_iv)
    ImageView iStudentBanner;
    @BindView(R.id.activity_interest_class_one_content_tv)
    TextView iOneContentTv;
    @BindView(R.id.activity_interest_class_one_del_iv)
    ImageView iOneDelIv;
    @BindView(R.id.activity_interest_class_two_content_tv)
    TextView iTwoContentTv;
    @BindView(R.id.activity_interest_class_two_del_iv)
    ImageView iTwoDelIv;
    @BindView(R.id.activity_interest_class_three_content_tv)
    TextView iThreeContentTv;
    @BindView(R.id.activity_interest_class_three_del_iv)
    ImageView iThreeDelIv;
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
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;
    IInterestClass iInterestClass;
    ChoiceClassLists.KechenBean firstKeChen;
    String firstId = "";
    String secondeId = "";
    String thirdId = "";
    int position = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_choice_interest_class;
    }

    @Override
    public void initData() {
        iStudentBanner.setBackgroundResource(MyApplication.bannerImage);
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
            if (data.getXuanke() != null && data.getXuanke().getFirst() != null)
                StringUtil.setTextView(iOneContentTv, data.getXuanke().getFirst().getCourse_name());
            if (data.getXuanke() != null && data.getXuanke().getSecend() != null)
                StringUtil.setTextView(iTwoContentTv, data.getXuanke().getSecend().getCourse_name());
            if (data.getXuanke() != null && data.getXuanke().getThird() != null)
                StringUtil.setTextView(iThreeContentTv, data.getXuanke().getThird().getCourse_name());
            if (ListUtil.unEmpty(data.getKechen())) {
                mItems.addAll(data.getKechen());
                multiTypeAdapter.notifyDataSetChanged();
            }
        } else {
            activityStudentShowClassLl.setVisibility(View.VISIBLE);
            activityStudentChoiceLl.setVisibility(View.GONE);
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
            R.id.activity_interest_class_one_del_iv,
            R.id.activity_interest_class_two_content_tv,
            R.id.activity_interest_class_two_del_iv,
            R.id.activity_interest_class_three_content_tv,
            R.id.activity_interest_class_three_del_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_interest_class_one_content_tv:
                position = 1;
                iListRecycler.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_interest_class_one_del_iv:
                iOneContentTv.setText("");
//                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_two_content_tv:
                position = 2;
                iListRecycler.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_interest_class_two_del_iv:
                iTwoContentTv.setText("");
//                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_three_content_tv:
                iListRecycler.setVisibility(View.VISIBLE);
                position = 3;
                break;
            case R.id.activity_interest_class_three_del_iv:
                iThreeContentTv.setText("");
                break;
//            case R.id.activity_interest_class_commit_btn:
//                iInterestClass.getInterestsSubmit(UserPreferUtil.getInstanse().getRoleId(),
//                        UserPreferUtil.getInstanse().getUserId(),
//                        UserPreferUtil.getInstanse().getSchoolId(),
//                        firstId, secondeId, thirdId)
//                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
//                        .compose(RxLifecycleCompact.bind(this).withObservable())
//                        .subscribe(bean -> {
//                            if (bean.isSuccess()) {
//                                finish();
//                            }
//                            toast(bean.getMsg());
//                        }, new HttpError());
//                break;
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
        switch (position) {
            case 1:
                firstId = bean.getId();
                StringUtil.setTextView(iOneContentTv, bean.getName());
                break;
            case 2:
                secondeId = bean.getId();
                StringUtil.setTextView(iTwoContentTv, bean.getName());
                break;
            case 3:
                thirdId = bean.getId();
                StringUtil.setTextView(iThreeContentTv, bean.getName());
                break;
        }
        iListRecycler.setVisibility(View.GONE);
    }
}
