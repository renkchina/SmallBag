package bag.small.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/6/14.
 */

public class GlideImageLoader extends ImageLoader implements RxPickerImageLoader {

    @Override public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext())
                .load(path)
                .centerCrop()
                .override(width, height)
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(imageView.getContext()).load(path).into(imageView);
    }
}