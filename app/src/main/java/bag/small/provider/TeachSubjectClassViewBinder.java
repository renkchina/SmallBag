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
import bag.small.entity.RegisterInfoBean;
import bag.small.interfaze.IListDialog;
import bag.small.interfaze.IViewBinder;
import bag.small.utils.ListUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubjectClassViewBinder extends ItemViewBinder<TeachSubjectClass, TeachSubjectClassViewBinder.ViewHolder> implements IListDialog {
    private IViewBinder iViewBinder;
    private ListDialog listDialog;
    private RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean jie;
    private List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean> course;
    private RegisterInfoBean.SchoolArea.SchoolBean school;

    public void setSchool(RegisterInfoBean.SchoolArea.SchoolBean school) {
        this.school = school;
    }

    public TeachSubjectClassViewBinder(IViewBinder iViewBinder) {
        this.iViewBinder = iViewBinder;
        listDialog = new ListDialog((Context) iViewBinder);
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
                if (bean.isAdd()) {
                    iViewBinder.delete(position);
                } else {
                    iViewBinder.add(0, position);
                }
            }
        });
        holder.itemSubjectTv.setOnClickListener(v -> {
            if (ListUtil.unEmpty(course)) {
                listDialog.setListData(getKeChen());
                listDialog.show(v);
                listDialog.setListDialog((position1, content) -> {
                    ((TextView) v).setText(content);
                    bean.setKemu(course.get(position1).getValue());
                });
            }
        });
        holder.itemTeacherNumberTv.setOnClickListener(v -> {
            if (school != null) {
                listDialog.setListData(getJieCi());
                listDialog.show(v);
                listDialog.setListDialog((position1, content) -> {
                    ((TextView) v).setText(content);
                    jie = school.getBase().getJie().get(position1);
                    course = jie.getKeche();
                    holder.itemTeacherGradeTv.setText(jie.getNianji_name());
                    bean.setJieci(jie.getValue());
                    bean.setNianji(jie.getNianji());
                    if (ListUtil.unEmpty(getBanji())) {
                        holder.itemTeacherClassTv.setText(getBanji().get(0));
//                        banji = getNumbers(getBanji().get(0));
                    } else {
                        holder.itemTeacherClassTv.setText("班级");
                    }
                });
            }
        });

        holder.itemTeacherClassTv.setOnClickListener(v -> {
            if (jie != null) {
                listDialog.setListData(getBanji());
                listDialog.show(v);
                listDialog.setListDialog((position1, content) -> {
                    ((TextView) v).setText(content);
                    bean.setBanji(jie.getBanji().get(position1).getValue());
                });
            }
        });
    }


    @Override
    public void callListener(int position, String content) {

    }


    private List<String> getJieCi() {
        List<String> lists = new ArrayList<>();
        if (school == null) {
            return lists;
        }
        List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean> list =
                school.getBase().getJie();
        if (ListUtil.unEmpty(list)) {
            for (RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean jieBean : list) {
                lists.add(jieBean.getName());
            }
        }
        return lists;
    }

    private List<String> getBanji() {
        List<String> lists = new ArrayList<>();
        if (jie == null) {
            return lists;
        }
        if (ListUtil.unEmpty(jie.getBanji())) {
            List<RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean> list = jie.getBanji();
            for (RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean banjiBean : list) {
                lists.add(banjiBean.getText());
            }
        }
        return lists;
    }

    private List<String> getKeChen() {
        List<String> lists = new ArrayList<>();
        if (ListUtil.unEmpty(course)) {
            for (RegisterInfoBean.SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean kecheBean : course) {
                lists.add(kecheBean.getText());
            }
        }
        return lists;
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
