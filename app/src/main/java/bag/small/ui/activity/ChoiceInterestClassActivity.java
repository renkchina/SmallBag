package bag.small.ui.activity;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.ChoiceClassLists;
import bag.small.provider.ChoiceClassListsBinder;
import bag.small.utils.GlideImageLoader;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ChoiceInterestClassActivity extends BaseActivity {
    @Bind(R.id.activity_interest_class_student_banner)
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
    private List<Object> bannerImages;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> mItems;

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
        multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(ChoiceClassLists.class, new ChoiceClassListsBinder());
        iListRecycler.setLayoutManager(new LinearLayoutManager(this));
        iListRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        iListRecycler.setAdapter(multiTypeAdapter);
    }

    @Override
    public void initView() {
        setBanner(iStudentBanner, bannerImages);
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

        mItems.add(new ChoiceClassLists());
        mItems.add(new ChoiceClassLists());
        mItems.add(new ChoiceClassLists());
        mItems.add(new ChoiceClassLists());
        mItems.add(new ChoiceClassLists());
        multiTypeAdapter.notifyDataSetChanged();
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
                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_two_content_tv:
                break;
            case R.id.activity_interest_class_two_del_iv:
                iTwoContentTv.setText("");
                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_three_content_tv:
                break;
            case R.id.activity_interest_class_three_del_iv:
                iThreeContentTv.setText("");
                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_interest_class_commit_btn:
                break;
        }
    }
}
