package bag.small.provider;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.entity.BaseBean;
import bag.small.entity.MomentsBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMoments;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/9.
 */
public class EvaluationListBinder extends ItemViewBinder<MomentsBean.RepayBean, EvaluationListBinder.ViewHolder> {

    private MomentsViewBinder parentBinder;

    public EvaluationListBinder(MomentsViewBinder momentsViewBinder) {
        parentBinder = momentsViewBinder;
    }

    IMoments iMoments;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_moments_evaluation_list_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MomentsBean.RepayBean bean) {

        StringUtil.setTextView(holder.eTitleTv, bean.getTitle() + ":");
        StringUtil.setTextView(holder.eContentTv, bean.getContent());
        holder.eTitleTv.setOnClickListener(v -> parentBinder.showEvaluationL(getPosition(holder), bean.getPosition()));
        holder.eContentTv.setOnClickListener(v -> parentBinder.showEvaluationL(getPosition(holder), bean.getPosition()));
        holder.rootView.setOnLongClickListener(v -> {
            //
            showDialog(v.getContext(), "温馨提醒", "你确定要删除该条评论消息", (dialog, which) -> {
                HttpUtil.getInstance().createApi(IMoments.class)
                        .deleteEvaluate(bean.getReview_id(), UserPreferUtil.getInstanse().getRoleId(),
                                UserPreferUtil.getInstanse().getUserId(),
                                UserPreferUtil.getInstanse().getSchoolId(), bean.getMsgId())
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .subscribe(listBaseBean -> {
                            if (listBaseBean.isSuccess()) {
                                if(ListUtil.unEmpty(listBaseBean.getData())){
                                    getAdapter().getItems().clear();
//                                    getAdapter().getItems().addAll(listBaseBean.getData());
                                }else{
                                    getAdapter().getItems().remove(bean);
                                }
                                getAdapter().notifyDataSetChanged();
                            }
                        }, new HttpError());
                dialog.dismiss();
            });

            return true;
        });
    }

    private void showDialog(Context context, String title, String message, DialogInterface.OnClickListener yes) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("确定", yes);
        builder.show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_evaluation_title_tv)
        TextView eTitleTv;
        @BindView(R.id.item_evaluation_content_tv)
        TextView eContentTv;
        View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
