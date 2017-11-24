package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.ClassScheduleBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class ClassScheduleViewBinder extends ItemViewBinder<ClassScheduleBean, ClassScheduleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_class_schedule_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassScheduleBean classSchedule) {
        if (getPosition(holder) != 0) {
            holder.RestTv.setText(" ");
            holder.view.setBackgroundResource(R.color.white);
            holder.TopDateTv.setBackgroundResource(R.color.data_day_gray);
            holder.TopDateTv.setText("周一4号");
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.class_schedule_top_date_tv)
        TextView TopDateTv;
        @BindView(R.id.class_schedule_one_tv)
        TextView OneTv;
        @BindView(R.id.class_schedule_two_tv)
        TextView TwoTv;
        @BindView(R.id.class_schedule_three_tv)
        TextView ThreeTv;
        @BindView(R.id.class_schedule_four_tv)
        TextView FourTv;
        @BindView(R.id.class_schedule_rest_tv)
        TextView RestTv;
        @BindView(R.id.class_schedule_five_tv)
        TextView FiveTv;
        @BindView(R.id.class_schedule_six_tv)
        TextView SixTv;
        @BindView(R.id.class_schedule_seven_tv)
        TextView SevenTv;
        @BindView(R.id.class_schedule_eight_tv)
        TextView EightTv;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
