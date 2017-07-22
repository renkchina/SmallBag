package bag.small.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;

import bag.small.R;

/**
 * Created by Administrator on 2017/5/30.
 */

public class GlideImageLoader implements RxPickerImageLoader {

    @Override public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext())
                .load(path)
                .error(R.drawable.ic_preview_image)
                .centerCrop()
                .override(width, height)
                .into(imageView);
    }
}
