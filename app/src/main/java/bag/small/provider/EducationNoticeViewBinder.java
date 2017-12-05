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
import butterknife.BindView;
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
        View root = inflater.inflate(R.layout.item_class_memorandum, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EducationNoticeBean.ResultsBean bean) {
        Context context = holder.root.getContext();
        holder.mSubject.setText("");
        holder.root.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(context, NoticeDetailActivity.class);
            bundle.putInt("notify_id", bean.getId());
            intent.putExtras(bundle);
            context.startActivity(intent);
            bean.setIs_readed(true);
            getAdapter().notifyDataSetChanged();
        });
        if (bean.isIs_readed()) {
            holder.mDeleteTv.setVisibility(View.GONE);
        } else {
            holder.mDeleteTv.setVisibility(View.VISIBLE);
        }
        holder.mContentTv.setText(bean.getTitle());
        holder.mTimeTv.setText(bean.getCreate_at());
        holder.mTitleTv.setText("教务通知");
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_class_memorandum_title_tv)
        TextView mTitleTv;
        @BindView(R.id.item_class_memorandum_content_tv)
        TextView mContentTv;
        @BindView(R.id.item_class_memorandum_time_tv)
        TextView mTimeTv;
        @BindView(R.id.item_class_memorandum_subject_tv)
        TextView mSubject;
        @BindView(R.id.item_class_memorandum_delete_tv)
        TextView mDeleteTv;
        View root;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }
}
