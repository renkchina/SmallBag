package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import bag.small.R;
import bag.small.entity.ChoiceClassLists;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/8.
 */
public class ChoiceClassListsBinder extends ItemViewBinder<ChoiceClassLists.KechenBean, ChoiceClassListsBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_student_choice_class_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChoiceClassLists.KechenBean kechen) {
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
            StringUtil.setTextView(holder.iClassTv, kechen.getName());
            StringUtil.setTextView(holder.iTeacherTv, kechen.getTeacher());
            StringUtil.setTextView(holder.iClassroomTv, kechen.getClass_room());
            StringUtil.setTextView(holder.iTimeTv, kechen.getClass_time());
            holder.rootView.setOnClickListener(v -> RxBus.get().send(9999,kechen));
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_choice_class_tv)
        TextView iClassTv;
        @BindView(R.id.item_choice_teacher_tv)
        TextView iTeacherTv;
        @BindView(R.id.item_choice_classroom_tv)
        TextView iClassroomTv;
        @BindView(R.id.item_choice_time_tv)
        TextView iTimeTv;
        View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
