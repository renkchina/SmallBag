package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.ChoiceClassLists;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/8.
 */
public class ChoiceClassListsBinder extends ItemViewBinder<ChoiceClassLists, ChoiceClassListsBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_student_choice_class_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChoiceClassLists choiceClassLists) {
        if (getPosition(holder) == 0) {
            holder.iClassTv.setText("兴趣课名");
            holder.iTeacherTv.setText("授课老师");
            holder.iClassroomTv.setText("上课教室");
            holder.iTimeTv.setText("上课时间");
            holder.iClassTv.getPaint().setFakeBoldText(true);
            holder.iTeacherTv.getPaint().setFakeBoldText(true);
            holder.iClassroomTv.getPaint().setFakeBoldText(true);
            holder.iTimeTv.getPaint().setFakeBoldText(true);
        } else {
            holder.iClassTv.getPaint().setFakeBoldText(false);
            holder.iTeacherTv.getPaint().setFakeBoldText(false);
            holder.iClassroomTv.getPaint().setFakeBoldText(false);
            holder.iTimeTv.getPaint().setFakeBoldText(false);
            holder.iClassTv.setText("舞蹈课");
            holder.iTeacherTv.setText("王老师");
            holder.iClassroomTv.setText("302室");
            holder.iTimeTv.setText("周五15:00-16:00");
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_choice_class_tv)
        TextView iClassTv;
        @Bind(R.id.item_choice_teacher_tv)
        TextView iTeacherTv;
        @Bind(R.id.item_choice_classroom_tv)
        TextView iClassroomTv;
        @Bind(R.id.item_choice_time_tv)
        TextView iTimeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
