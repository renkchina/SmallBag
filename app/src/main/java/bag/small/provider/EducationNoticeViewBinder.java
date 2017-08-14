package bag.small.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.EducationNoticeBean;
import bag.small.ui.activity.NoticeDetailActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/6.
 */
public class EducationNoticeViewBinder extends ItemViewBinder<EducationNoticeBean.ResultsBean, EducationNoticeViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_education_notice, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EducationNoticeBean.ResultsBean bean) {
        Context context = holder.root.getContext();
        holder.root.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(context, NoticeDetailActivity.class);
            bundle.putInt("notify_id", bean.getId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
        if (bean.isIs_readed()) {
            holder.iNoticeStateV.setVisibility(View.GONE);
        } else {
            holder.iNoticeStateV.setVisibility(View.VISIBLE);
        }
        holder.iNoticeContentTv.setText(bean.getTitle());
        holder.iNoticeTimeTv.setText(bean.getCreate_at());
        holder.iNoticeTitleTv.setText("教务通知");
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
