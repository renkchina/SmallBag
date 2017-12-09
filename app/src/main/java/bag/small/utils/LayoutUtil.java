package bag.small.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import bag.small.app.MyApplication;

/**
 * Created by Administrator on 2017/9/4.
 */

public class LayoutUtil {

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static void setBanner(Banner banner, List images) {
        if (ListUtil.isEmpty(images)) {
            return;
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(4000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }


    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int[] getWithAndHei() {
        Resources resources = MyApplication.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int[] wh = new int[2];
        wh[0] = width;
        wh[1] = height;
        return wh;
    }

}
