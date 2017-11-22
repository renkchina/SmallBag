package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maning.imagebrowserlibrary.MNImageBrowser;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.utils.ImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/6.
 */

public class InnerMsgProviderImage extends ItemViewBinder<String, InnerMsgProviderImage.MViewHolder> {

    @NonNull
    @Override
    protected MViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_inner_fg_msg_image, parent, false);
        return new MViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MViewHolder holder, @NonNull String item) {
        ImageUtil.loadImages(holder.imageView.getContext(), holder.imageView, item);
        holder.imageView.setOnClickListener(v -> {
            ArrayList<String> images = (ArrayList<String>) getAdapter().getItems();
            MNImageBrowser.showImageBrowser(v.getContext(), v, getPosition(holder), images);
        });
    }

    static class MViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_inner_life_provider_iv)
        ImageView imageView;

        MViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}