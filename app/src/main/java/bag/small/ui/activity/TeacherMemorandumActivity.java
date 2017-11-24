package bag.small.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.youth.banner.Banner;
import java.util.List;
import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.provider.TeacherMemorandumViewBinder;
import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TeacherMemorandumActivity extends BaseActivity {


    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.memorandum_edit_float_image)
    ImageView mFloatImage;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_memorandum;
    }

    @Override
    public void initData() {
        setToolTitle("备忘录", true);
        items = new Items();
        items.add(new TeacherMemorandumBean());
        items.add(new TeacherMemorandumBean());
        items.add(new TeacherMemorandumBean());
        multiTypeAdapter = new MultiTypeAdapter(items);
        mFloatImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {
        multiTypeAdapter.register(TeacherMemorandumBean.class, new TeacherMemorandumViewBinder());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiTypeAdapter);
    }

    @OnClick(R.id.memorandum_edit_float_image)
    public void onViewClicked() {
        goActivity(CreateMemorandumActivity.class);
    }
}
