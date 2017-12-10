package bag.small.provider;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.ui.activity.ClassMemorandumActivity;
import bag.small.ui.activity.ClassScheduleActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class TeacherMemorandumViewBinder extends ItemViewBinder<TeacherMemorandumBean, TeacherMemorandumViewBinder.ViewHolder> {

    private boolean isClass;

    public boolean isClass() {
        return isClass;
    }

    public void setClass(boolean aClass) {
        isClass = aClass;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_big_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeacherMemorandumBean bean) {
        String className = bean.getName() + "（" + bean.getBanci() + "）班";
        holder.itemText.setText(className);
        holder.itemRoundTxt.setVisibility(View.GONE);
        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (isClass) {
                intent.putExtra("class", className);
                intent.putExtra("banji_id", bean.getBanji_id());
                intent.setClass(v.getContext(), ClassScheduleActivity.class);
            } else {
                intent.putExtra("class", className);
                intent.putExtra("banji_id", bean.getBanji_id());
                intent.setClass(v.getContext(), ClassMemorandumActivity.class);
            }
            v.getContext().startActivity(intent);
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
        TextView itemRoundTxt;
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
