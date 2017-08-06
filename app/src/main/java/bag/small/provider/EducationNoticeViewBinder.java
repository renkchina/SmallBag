package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.EducationNoticeBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/6.
 */
public class EducationNoticeViewBinder extends ItemViewBinder<EducationNoticeBean, EducationNoticeViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_education_notice, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EducationNoticeBean bean) {
        holder.root.setOnClickListener(v -> {

        });
        holder.iNoticeContentTv.setText("content");
        holder.iNoticeTimeTv.setText("1小时前");
        holder.iNoticeTitleTv.setText("Title");
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_education_notice_state_v)
        View iNoticeStateV;
        @Bind(R.id.item_education_notice_title_tv)
        TextView iNoticeTitleTv;
        @Bind(R.id.item_education_notice_time_tv)
        TextView iNoticeTimeTv;
        @Bind(R.id.item_education_notice_content_tv)
        TextView iNoticeContentTv;
        View root;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }
}
