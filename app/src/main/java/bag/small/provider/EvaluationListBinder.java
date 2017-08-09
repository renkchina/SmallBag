package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.MomentsBean;
import bag.small.utils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/9.
 */
public class EvaluationListBinder extends ItemViewBinder<MomentsBean.RepayBean, EvaluationListBinder.ViewHolder> {

    private MomentsViewBinder parentBinder;

    public EvaluationListBinder(MomentsViewBinder momentsViewBinder) {
        parentBinder = momentsViewBinder;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_moments_evaluation_list_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MomentsBean.RepayBean bean) {

        StringUtil.setTextView(holder.eTitleTv, bean.getTitle());
        StringUtil.setTextView(holder.eContentTv, bean.getContent());
        holder.eTitleTv.setOnClickListener(v -> parentBinder.showEvaluationL(getPosition(holder)));
        holder.eContentTv.setOnClickListener(v -> parentBinder.showEvaluationL(getPosition(holder)));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_evaluation_title_tv)
        TextView eTitleTv;
        @Bind(R.id.item_evaluation_content_tv)
        TextView eContentTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
