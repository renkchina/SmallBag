package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bag.small.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/7/31.
 */
public class TeachClassViewBinder extends ItemViewBinder<TeachClass, TeachClassViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_teacher_subject, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeachClass teachClass) {

    }


    class ViewHolder extends RecyclerView.ViewHolder  {
        @Bind(R.id.ac_teacher_number_tv)
        TextView acTeacherNumberTv;
        @Bind(R.id.ac_teacher_grade_tv)
        TextView acTeacherGradeTv;
        @Bind(R.id.ac_teacher_class_tv)
        TextView acTeacherClassTv;
        @Bind(R.id.activity_subject_tv)
        TextView acSubjectTv;
        @Bind(R.id.activity_subject_add_iv)
        ImageView acSubjectAddIv;
        @Bind(R.id.activity_subject_ll)
        LinearLayout acSubjectLl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
