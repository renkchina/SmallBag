package bag.small.ui.activity;

import android.os.Handler;

import com.caimuhao.rxpicker.RxPicker;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.utils.GlideImageLoader;

public class WelcomeActivity extends BaseActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.activity_wellcome;
    }

    @Override
    public void initView() {
        RxPicker.init(new GlideImageLoader());
        boolean unFirst = getBoolValue("unFirst");
        new Handler().postDelayed(() -> {
            if (unFirst) {
                skipActivity(LoginActivity.class);
            } else {
                skipActivity(GuideActivity.class);
            }
        }, 500);

    }
}
