package bag.small.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.List;

import bag.small.base.BaseFragment;

/**
 * Created by Administrator on 2017/4/11.
 */

public class GuideAdapter extends FragmentStatePagerAdapter {
    List<BaseFragment> mDatas;

    public GuideAdapter(FragmentManager fm, List<BaseFragment> mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

}
