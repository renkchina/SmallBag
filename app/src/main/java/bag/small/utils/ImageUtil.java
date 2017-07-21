package bag.small.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edriving.BuildConfig;
import com.edriving.view.GlideCircleTransform;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ImageUtil {

    public static void loadImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            if (TextUtils.isEmpty(url)) {
                url = "http://www.bz55.com/uploads/allimg/150701/140-150F1142638.jpg";
            }
            if (url.startsWith("http")) {
                Glide.with(context).load(url).into(imageView);
            } else {
                Glide.with(context).load(BuildConfig.BaseApi + url).into(imageView);
            }
        }
    }
    public static void loadLocalImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            Glide.with(context).load(url).into(imageView);
        }
    }

    public static void loadCircleImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            if (TextUtils.isEmpty(url)) {
                url = "http://www.bz55.com/uploads/allimg/150701/140-150F1142638.jpg";
            }
            if (url.startsWith("http")) {
                Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(imageView);
            } else {
                Glide.with(context).load(BuildConfig.BaseApi + url).transform(new GlideCircleTransform(context)).into(imageView);
            }
        }
    }


}
