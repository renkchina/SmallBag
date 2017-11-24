package bag.small.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.youth.banner.Banner;

import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.MassOrgBean;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.provider.MassOrgViewBinder;
import bag.small.provider.TeacherMemorandumViewBinder;
import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/11/24.
 */

public class MassOrgActivity extends BaseActivity {
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_memorandum;
    }

    @Override
    public void initData() {
        setToolTitle("社团", true);
        items = new Items();
        items.add(new MassOrgBean());
        items.add(new MassOrgBean());
        items.add(new MassOrgBean());
        multiTypeAdapter = new MultiTypeAdapter(items);
    }

    @Override
    public void initView() {
        multiTypeAdapter.register(MassOrgBean.class, new MassOrgViewBinder());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);
    }



}
