package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.dialog.ListDialog;
import bag.small.interfaze.IListDialog;
import bag.small.interfaze.IViewBinder;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubjectClassViewBinder extends ItemViewBinder<TeachSubjectClass, TeachSubjectClassViewBinder.ViewHolder> implements IListDialog {
    private IViewBinder iViewBinder;
    private ListDialog listDialog;

    public TeachSubjectClassViewBinder(IViewBinder iViewBinder) {
        this.iViewBinder = iViewBinder;
        listDialog = new ListDialog((Context) iViewBinder);
    }

    private List<String> getJieci() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(String.valueOf(i + 1990) + "届");
        }
        return lists;
    }

    private List<String> getNianJi() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            lists.add(String.valueOf(i + 1) + "年级");
        }
        return lists;
    }

    private List<String> getBanji() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(String.valueOf(i + 1) + "班");
        }
        return lists;
    }


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_teach_subject_class_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeachSubjectClass bean) {
        int position = getPosition(holder);
        if (bean.isAdd()) {
            holder.itemSubjectAddIv.setImageResource(R.mipmap.del_subject);
        } else {
            holder.itemSubjectAddIv.setImageResource(R.mipmap.add_line_circle);
        }
        holder.itemSubjectAddIv.setOnClickListener(v -> {
            if (iViewBinder != null) {
                if(bean.isAdd()){
                    iViewBinder.delete(position);
                }else {
                    iViewBinder.add(0, position);
                }
            }
        });
        holder.itemSubjectTv.setOnClickListener(v -> {
//            listDialog.setListData(get);
        });
        holder.itemTeacherNumberTv.setOnClickListener(v -> {
            listDialog.setListData(getJieci());
            listDialog.show(v);
            listDialog.setiListDialog(((TextView) v)::setText);
        });
        holder.itemTeacherGradeTv.setOnClickListener(v -> {
            listDialog.setListData(getNianJi());
            listDialog.show(v);
            listDialog.setiListDialog(((TextView) v)::setText);
        });
        holder.itemTeacherClassTv.setOnClickListener(v -> {
            listDialog.setListData(getBanji());
            listDialog.show(v);
            listDialog.setiListDialog(((TextView) v)::setText);
        });
    }

    @Override
    public void callListener(String content) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_subject_tv)
        TextView itemSubjectTv;
        @Bind(R.id.item_subject_add_iv)
        ImageView itemSubjectAddIv;
        @Bind(R.id.item_teacher_number_tv)
        TextView itemTeacherNumberTv;
        @Bind(R.id.item_teacher_grade_tv)
        TextView itemTeacherGradeTv;
        @Bind(R.id.item_teacher_class_tv)
        TextView itemTeacherClassTv;
        @Bind(R.id.item_class_add_num_iv)
        ImageView itemClassAddNumIv;
        View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
