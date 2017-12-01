package bag.small.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.GradeClass;
import bag.small.entity.RelateBanjiBean;
import bag.small.provider.RelateBanjiViewBinder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CheckClassActivity extends BaseActivity {

    @BindView(R.id.check_class_cancel_tv)
    TextView cancelTv;
    @BindView(R.id.toolbar_title_tv)
    TextView titleTv;
    @BindView(R.id.check_class_send_tv)
    TextView commitTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.check_class_recyclerView)
    RecyclerView mRecyclerView;
    private List<RelateBanjiBean> banjiList;
    MultiTypeAdapter multiTypeAdapter;
    List items;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_check_class;
    }

    @Override
    public void initData() {
        toolbar.setNavigationIcon(null);
        items = new Items(9);
        multiTypeAdapter = new MultiTypeAdapter(items);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            items.addAll(bundle.getParcelableArrayList("list"));
        } else {
            finish();
        }
    }

    @Override
    public void initView() {
        multiTypeAdapter.register(RelateBanjiBean.class, new RelateBanjiViewBinder());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);
    }

    @OnClick({R.id.check_class_cancel_tv, R.id.check_class_send_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_class_cancel_tv:
                break;
            case R.id.check_class_send_tv:
                GradeClass gradeClass = new GradeClass();
                gradeClass.setRelate_banji(items);
                RxBus.get().send(333333,gradeClass);
                break;
        }
        finish();
    }
}
