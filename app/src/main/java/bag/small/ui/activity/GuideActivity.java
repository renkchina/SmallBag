package bag.small.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.adapter.GuideAdapter;
import bag.small.base.BaseActivity;
import bag.small.base.BaseFragment;
import bag.small.ui.fragment.GuideFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.activity_guide_viewpager)
    ViewPager mViewpager;

    List<BaseFragment> fragments;
    GuideAdapter mGuideAdapter;
    @BindView(R.id.activity_guide_btn)
    Button mGuideBtn;
    GuideFragment guideFragment1;
    GuideFragment guideFragment2;
    GuideFragment guideFragment3;
    GuideFragment guideFragment4;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_guide;
    }


    @Override
    public void initData() {
        fragments = new ArrayList<>(4);
        guideFragment1 = new GuideFragment();
        guideFragment2 = new GuideFragment();
        guideFragment3 = new GuideFragment();
        guideFragment4 = new GuideFragment();
        guideFragment1.setImageRes(R.mipmap.ic_launcher);
        guideFragment2.setImageRes(R.mipmap.ic_launcher);
        guideFragment3.setImageRes(R.mipmap.ic_launcher);
        guideFragment4.setImageRes(R.mipmap.ic_launcher);

        fragments.add(guideFragment1);
        fragments.add(guideFragment2);
        fragments.add(guideFragment3);
        fragments.add(guideFragment4);
    }

    @Override
    public void initView() {
        mGuideAdapter = new GuideAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mGuideAdapter);
        mViewpager.addOnPageChangeListener(this);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == fragments.size() - 1) {
            mGuideBtn.setVisibility(View.VISIBLE);
        } else {
            mGuideBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.activity_guide_btn)
    public void onViewClicked() {
        setBoolValue("unFirst", true);
        skipActivity(LoginActivity.class);
    }
}
