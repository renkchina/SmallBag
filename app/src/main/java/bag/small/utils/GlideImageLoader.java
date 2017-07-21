package bag.small.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;

/**
 * Created by Administrator on 2017/6/14.
 */

public class GlideImageLoader implements RxPickerImageLoader {

    @Override public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext())
                .load(path)
                .centerCrop()
                .override(width, height)
                .into(imageView);
    }
}