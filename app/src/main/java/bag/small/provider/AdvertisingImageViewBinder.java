package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import bag.small.R;
import bag.small.entity.ImageString;
import bag.small.utils.ImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AdvertisingImageViewBinder extends ItemViewBinder<ImageString, AdvertisingImageViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_advertising_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ImageString item) {
        Context context = holder.advertisingImage.getContext();
        ImageUtil.loadImages(context,holder.advertisingImage,item.getUrl());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_advertising_image)
        ImageView advertisingImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
