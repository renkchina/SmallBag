package bag.small.provider;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.MemorandumItemBean;
import bag.small.ui.activity.StudentMemorandumSubjectOrTimeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class MemorandumTxtViewBinder extends ItemViewBinder<MemorandumItemBean, MemorandumTxtViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MemorandumItemBean memorandum) {
        if (memorandum.isHasnew()) {
            holder.itemRound.setVisibility(View.VISIBLE);
        } else {
            holder.itemRound.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(memorandum.getName())) {
            holder.itemText.setText(memorandum.getPublishdate());
        } else {
            holder.itemText.setText(memorandum.getName());
        }
        holder.view.setOnClickListener(v -> {
            Intent it = new Intent();
            it.setClass(v.getContext(), StudentMemorandumSubjectOrTimeActivity.class);
            it.putExtra("bean", memorandum);
            v.getContext().startActivity(it);
        });

        if (getPosition(holder) % 4 == 0) {
            holder.start.setBackgroundResource(R.mipmap.purple_start);
        } else if (getPosition(holder) % 3 == 0) {
            holder.start.setBackgroundResource(R.mipmap.blue_start);
        } else if (getPosition(holder) % 2 == 0) {
            holder.start.setBackgroundResource(R.mipmap.yellow_start);
        } else {
            holder.start.setBackgroundResource(R.mipmap.green_start);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_memorandum_text)
        TextView itemText;
        @BindView(R.id.item_memorandum_round_txt)
        TextView itemRound;
        @BindView(R.id.item_memo_start_v)
        View start;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
