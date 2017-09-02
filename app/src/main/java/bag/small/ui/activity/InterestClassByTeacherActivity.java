package bag.small.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.entity.BaseBean;
import bag.small.entity.TeacherClass;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IInterestClass;
import bag.small.provider.InterestClassViewBinder;
import bag.small.provider.TeacherClassViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class InterestClassByTeacherActivity extends BaseActivity {
    @Bind(R.id.activity_interest_teacher_banner_iv)
    ImageView bannerIv;
    @Bind(R.id.activity_interest_teacher_class_tv)
    TextView tClassTv;
    @Bind(R.id.activity_interest_teacher_time_tv)
    TextView tTimeTv;
    @Bind(R.id.activity_interest_teacher_classroom_tv)
    TextView tClassroomTv;
    @Bind(R.id.activity_interest_teacher_recycler)
    RecyclerView tRecycler;
    @Bind(R.id.interest_by_teacher_show_ll)
    LinearLayout teacherShowLl;
    @Bind(R.id.interest_by_teacher_choice_ll)
    LinearLayout teacherChoiceLl;
    @Bind(R.id.interest_empty_ll)
    LinearLayout emptyLL;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;
    IInterestClass iInterestClass;

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
        tRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        tRecycler.setAdapter(multiTypeAdapter);
        iInterestClass = HttpUtil.getInstance().createApi(IInterestClass.class);
    }

    @Override
    public void initView() {
        setToolTitle("兴趣课", true);
        bannerIv.setBackgroundResource(MyApplication.bannerImage);
        iInterestClass.getInterestsForTeacher(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess() && ListUtil.unEmpty(listBaseBean.getData().getClassX())) {
                        teacherShowLl.setVisibility(View.GONE);
                        teacherChoiceLl.setVisibility(View.VISIBLE);
                        emptyLL.setVisibility(View.GONE);
                        mItems.clear();
                        mItems.addAll(listBaseBean.getData().getClassX());
                        multiTypeAdapter.notifyDataSetChanged();
                    } else {
                        emptyLL.setVisibility(View.VISIBLE);
                        teacherShowLl.setVisibility(View.GONE);
                        teacherChoiceLl.setVisibility(View.GONE);
                    }
                }, new HttpError());
    }

    public void setShowStudents(String id) {
        if (!TextUtils.isEmpty(id)) {
            iInterestClass.getInterestsForTeachersStudent(id, UserPreferUtil.getInstanse().getSchoolId())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(this).withObservable())
                    .subscribe(bean -> {
                        if (bean.isSuccess() && bean.getData() != null) {
                            teacherShowLl.setVisibility(View.VISIBLE);
                            teacherChoiceLl.setVisibility(View.GONE);
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

}
