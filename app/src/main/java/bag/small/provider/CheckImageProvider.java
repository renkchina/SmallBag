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
import com.maning.imagebrowserlibrary.MNImageBrowser;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.ui.activity.PublishMsgActivity;
import bag.small.utils.ImageUtil;
import butterknife.BindView;
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
            holder.itemAcCheckContentIv.setPadding(50, 50, 50, 50);
            holder.itemAcCheckDel.setVisibility(View.GONE);
        } else {
            holder.itemAcCheckContentIv.setPadding(0, 0, 0, 0);
            holder.itemAcCheckDel.setVisibility(View.VISIBLE);
            holder.itemAcCheckDel.setOnClickListener(v -> {
                if (getAdapter().getItems().size() >= 9) {
                    ((List<Object>)getAdapter().getItems()).add("");
                }
                getAdapter().getItems().remove(item);
                getAdapter().notifyDataSetChanged();
            });
        }
        ImageUtil.loadLocalImageForChoice(context, holder.itemAcCheckContentIv, item);
        if (item.equals("")) {
            holder.itemAcCheckContentIv.setOnClickListener(
                    v -> RxPicker.of()
                            .single(false)
                            .limit(1, 10 - getAdapter().getItems().size())
                            .camera(true)
                            .start(activity)
                            .subscribe(this::setImages));
        } else {
            holder.itemAcCheckContentIv.setOnClickListener(v -> {
                ArrayList<String> images = (ArrayList<String>) getAdapter().getItems();
                MNImageBrowser.showImageBrowser(v.getContext(), v, getPosition(holder), removeImage(images));
            });
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

    private ArrayList<String> removeImage(List<String> images) {
        ArrayList<String> lists = new ArrayList<>(9);
        for (String image : images) {
            if (!TextUtils.isEmpty(image)) {
                lists.add(image);
            }
        }
        return lists;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_ac_check_content_iv)
        ImageView itemAcCheckContentIv;
        @BindView(R.id.item_ac_check_del)
        View itemAcCheckDel;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
