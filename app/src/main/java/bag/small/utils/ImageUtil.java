package bag.small.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;

import bag.small.R;
import bag.small.view.GlideCircleTransform;


/**
 * Created by Administrator on 2017/4/18.
 */

public class ImageUtil {

    public static void loadImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            if (TextUtils.isEmpty(url)) {
                url = "http://www.bz55.com/uploads/allimg/150701/140-150F1142638.jpg";
            }
            Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
    }

    public static void loadLocalImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            Glide.with(context).load(url).into(imageView);
        }
    }

    public static void loadCircleImages(Context context, ImageView imageView, String url) {
        if (context != null) {
            if (url == null) {
                url = "http://www.bz55.com/uploads/allimg/150701/140-150F1142638.jpg";
            }
            Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(imageView);
//            .placeholder(R.mipmap.photo_update_nomal)
        }
    }

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer,Base64.DEFAULT);
    }
}
