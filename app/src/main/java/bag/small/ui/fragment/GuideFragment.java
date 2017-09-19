package bag.small.ui.fragment;

import android.widget.ImageView;

import bag.small.R;
import bag.small.base.BaseFragment;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/16.
 */

public class GuideFragment extends BaseFragment {
    int imageRes;
    @Bind(R.id.fragment_guide_iv)
    ImageView fragmentGuideIv;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_guide_iv;
    }

    @Override
    public void onFragmentShow() {

    }
    public void setImageRes(int res) {
        imageRes = res;
    }

    @Override
    public void initData() {
        if (imageRes > 0)
            fragmentGuideIv.setImageResource(imageRes);
    }

}
