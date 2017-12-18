package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.IMChoiceTeacher;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/18.
 */
public class IMChoiceTeacherViewBinder extends ItemViewBinder<IMChoiceTeacher, IMChoiceTeacherViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_dialog_choice_teacher_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IMChoiceTeacher iMChoiceTeacher) {
        if (iMChoiceTeacher.isChecked()) {
            holder.iNameTv.setBackgroundResource(R.color.colorPrimaryDark);
            holder.iCheckedTv.setVisibility(View.VISIBLE);
        } else {
            holder.iNameTv.setBackgroundResource(R.color.white);
            holder.iCheckedTv.setVisibility(View.GONE);
        }
        holder.root.setOnClickListener(v -> {
            if (iMChoiceTeacher.isChecked()) {
                iMChoiceTeacher.setChecked(false);
            } else {
                iMChoiceTeacher.setChecked(true);
            }
            getAdapter().notifyItemChanged(getPosition(holder));
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_teacher_name_tv)
        TextView iNameTv;
        @BindView(R.id.item_teacher_checked_tv)
        TextView iCheckedTv;
        View root;

        ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
