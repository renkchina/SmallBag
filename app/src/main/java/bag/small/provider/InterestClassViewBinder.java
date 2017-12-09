package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.TeacherClass;
import bag.small.ui.activity.InterestClassByTeacherActivity;
import bag.small.utils.LayoutUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/9/2.
 */
public class InterestClassViewBinder extends ItemViewBinder<TeacherClass.ClassBean, InterestClassViewBinder.ViewHolder> {

    InterestClassByTeacherActivity activity;


    public InterestClassViewBinder(InterestClassByTeacherActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeacherClass.ClassBean bean) {
        holder.itemMemorandumText.setText(bean.getName());
        holder.mRoundTxt.setVisibility(View.GONE);
        holder.item.setOnClickListener(v -> {
            if (activity != null)
                activity.setShowStudents(bean.getId());
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
        Context context = holder.item.getContext();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LayoutUtil.dip2px(context, 43));
        holder.item.setLayoutParams(params);
//        LayoutUtil.setMargins(holder.item, LayoutUtil.dip2px(context, 30), LayoutUtil.dip2px(context, 10),
//                LayoutUtil.dip2px(context, 30), LayoutUtil.dip2px(context, 10));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_memorandum_text)
        TextView itemMemorandumText;
        @BindView(R.id.item_memorandum_round_txt)
        TextView mRoundTxt;
        View item;
        @BindView(R.id.item_memo_start_v)
        View start;

        ViewHolder(View view) {
            super(view);
            item = view;
            ButterKnife.bind(this, view);
        }
    }


}
