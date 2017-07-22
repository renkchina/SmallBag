package bag.small.ui.activity;

import android.os.Handler;
import bag.small.R;
import bag.small.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.activity_wellcome;
    }

    @Override
    public void initView() {
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
