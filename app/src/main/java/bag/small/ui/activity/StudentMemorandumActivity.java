package bag.small.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.BottemDialog;
import bag.small.entity.MemorandumItemBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.IMemorandum;
import bag.small.interfaze.IDialog;
import bag.small.provider.MemorandumTxtViewBinder;
import bag.small.utils.GlideImageLoader;
import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class StudentMemorandumActivity extends BaseActivity implements IDialog {

    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.memorandum_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;

    MultiTypeAdapter multiTypeAdapter;
    List items;
    List bannerImages;
    BottemDialog bottemDialog;
    IMemorandum iMemorandum;
    MemorandumTxtViewBinder viewBinder;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_memorandum;
    }

    @Override
    public void initData() {
        setToolTitle("备忘录", true);
        toolbarRightIv.setVisibility(View.VISIBLE);
        toolbarRightIv.setImageResource(R.mipmap.sortby);
        items = new Items();
        iMemorandum = HttpUtil.getInstance().createApi(IMemorandum.class);
        items.add(new MemorandumItemBean());
        items.add(new MemorandumItemBean());
        items.add(new MemorandumItemBean());
        items.add(new MemorandumItemBean());
        bannerImages = new ArrayList(5);
        bottemDialog = new BottemDialog(this);
    }

    @Override
    public void initView() {
        multiTypeAdapter = new MultiTypeAdapter(items);
        viewBinder = new MemorandumTxtViewBinder();
        multiTypeAdapter.register(MemorandumItemBean.class, viewBinder);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(multiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getMemorandum();

    }

    private void getMemorandum() {

    }



    @OnClick(R.id.toolbar_right_iv)
    public void onViewClicked() {
        bottemDialog.show();
    }

    //学科排序
    @Override
    public void callBackMethod(Object object, Object bean) {
        viewBinder.setSortType(0);
    }

    //时间排序
    @Override
    public void callBackMiddleMethod() {
        viewBinder.setSortType(1);
    }
}

