package bag.small.provider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import java.util.List;

import bag.small.R;
import bag.small.ui.activity.PublishMsgActivity;
import bag.small.utils.ImageUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;


public class CheckImageProvider extends ItemViewBinder<String, CheckImageProvider.MyViewHolder> {
    private Activity activity;

    public CheckImageProvider(PublishMsgActivity publishMsgActivity) {
        activity = publishMsgActivity;
    }

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_ac_check_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull final String item) {
        final Context context = holder.rootView.getContext();
        if (TextUtils.isEmpty(item)) {
            holder.itemAcCheckDel.setVisibility(View.GONE);
            holder.itemAcCheckContentIv.setImageResource(R.mipmap.jiaolianduan_tianjia);

        } else {
            holder.itemAcCheckDel.setVisibility(View.VISIBLE);
            ImageUtil.loadLocalImages(context, holder.itemAcCheckContentIv, item);
            holder.itemAcCheckDel.setOnClickListener(v -> {
                getAdapter().getItems().remove(item);
                getAdapter().notifyDataSetChanged();
            });
        }
        if (item.equals("")) {
            holder.itemAcCheckContentIv.setOnClickListener(
                    v -> RxPicker.of()
                    .single(false)
                    .camera(true)
                    .start(activity)
                    .subscribe(this::setImages));
        }
    }


    private void setImages(List<ImageItem> images) {
        List<Object> mDatas = (List<Object>) getAdapter().getItems();
        if (mDatas.size() < 2 && TextUtils.isEmpty((String) mDatas.get(0))) {
            mDatas.clear();
        } else {
            mDatas.remove(mDatas.size() - 1);
        }
        for (ImageItem imageItem : images) {
            mDatas.add(imageItem.getPath());
        }
        if (mDatas.size() < 9) {
            mDatas.add("");
        }
        getAdapter().notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_ac_check_content_iv)
        ImageView itemAcCheckContentIv;
        @Bind(R.id.item_ac_check_del)
        View itemAcCheckDel;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
