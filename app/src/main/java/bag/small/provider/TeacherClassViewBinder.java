package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.TeacherClass;
import bag.small.utils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/10.
 */
public class TeacherClassViewBinder extends ItemViewBinder<TeacherClass.ClassBean.StudentsBean, TeacherClassViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_teacher_interest_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeacherClass.ClassBean.StudentsBean bean) {
        if (getPosition(holder) == 0) {
            setTxtSizeColor(holder.itemTextOne, true, "班级");
            setTxtSizeColor(holder.itemTextTwo, true, "姓名");
            setTxtSizeColor(holder.itemTextThree, true, "学号");
        } else {
            setTxtSizeColor(holder.itemTextOne, false, bean.getBanji());
            setTxtSizeColor(holder.itemTextTwo, false, bean.getName());
            setTxtSizeColor(holder.itemTextThree, false, bean.getXuehao());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_text_one)
        TextView itemTextOne;
        @Bind(R.id.item_text_two)
        TextView itemTextTwo;
        @Bind(R.id.item_text_three)
        TextView itemTextThree;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setTxtSizeColor(TextView tv, boolean isBold, String content) {
        StringUtil.setTextView(tv, content);
        if (isBold) {
            tv.setTextSize(15);
        } else {
            tv.setTextSize(12);
        }
        tv.getPaint().setFakeBoldText(isBold);
    }
}
