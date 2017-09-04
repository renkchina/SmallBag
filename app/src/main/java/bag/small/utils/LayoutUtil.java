package bag.small.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/9/4.
 */

public class LayoutUtil {

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
