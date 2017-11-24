package bag.small.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.youth.banner.Banner;

import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.ClassScheduleBean;
import bag.small.provider.ClassScheduleViewBinder;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ClassScheduleActivity extends BaseActivity {

    List items;
    MultiTypeAdapter multiTypeAdapter;
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.class_schedule_recycler)
    RecyclerView classScheduleRecycler;
    @BindView(R.id.class_schedule_tips_tv)
    TextView classScheduleTipsTv;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_class_schedule;
    }

    @Override
    public void initData() {
        setToolTitle("课程表", true);
        items = new Items(9);
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        items.add(new ClassScheduleBean());
        multiTypeAdapter = new MultiTypeAdapter(items);
    }

    @Override
    public void initView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        classScheduleRecycler.setLayoutManager(new GridLayoutManager(this, 8));
        multiTypeAdapter.register(ClassScheduleBean.class, new ClassScheduleViewBinder());
        classScheduleRecycler.setAdapter(multiTypeAdapter);
    }

}
