package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bag.small.R;
import bag.small.entity.TeacherClass;
import bag.small.ui.activity.InterestClassByTeacherActivity;
import butterknife.Bind;
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
        View root = inflater.inflate(R.layout.item_interest_teacher_choice_btn, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeacherClass.ClassBean bean) {
        holder.itemInterestBtn.setText(bean.getName());
        holder.itemInterestBtn.setOnClickListener(v -> {
            if (activity != null)
                activity.setShowStudents(bean.getId());
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_interest_btn)
        Button itemInterestBtn;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
