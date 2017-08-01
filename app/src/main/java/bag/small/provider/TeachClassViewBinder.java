package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bag.small.R;
import bag.small.interfaze.IViewBinder;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/7/31.
 */
public class TeachClassViewBinder extends ItemViewBinder<TeachClass, TeachClassViewBinder.ViewHolder> {

    private IViewBinder iViewBinder;

    public TeachClassViewBinder(IViewBinder iViewBinder) {
        this.iViewBinder = iViewBinder;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_teacher_class_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeachClass teachClass) {
        holder.itemAddIv.setOnClickListener(v -> {
            if (iViewBinder != null) {
                iViewBinder.add(2, getPosition(holder));
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_teacher_number_tv)
        TextView itemTeacherNumberTv;
        @Bind(R.id.item_teacher_grade_tv)
        TextView itemTeacherGradeTv;
        @Bind(R.id.item_teacher_class_tv)
        TextView itemTeacherClassTv;
        @Bind(R.id.item_class_add_num_iv)
        ImageView itemAddIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}