package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import bag.small.R;
import bag.small.utils.ImageUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/1.
 */
public class DialogSnapViewBinder extends ItemViewBinder<String, DialogSnapViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.dialog_snap_image_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String string) {
        ImageUtil.loadImages(holder.view.getContext(), holder.view, string);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView view;

        ViewHolder(View itemView) {
            super(itemView);
            view = (ImageView) itemView;
        }
    }
}
