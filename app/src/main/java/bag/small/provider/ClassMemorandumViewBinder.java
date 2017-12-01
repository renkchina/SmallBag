package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.BaseBean;
import bag.small.entity.ClassMemorandumBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IGetMemorandumBy;
import bag.small.rx.RxUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/1.
 */
public class ClassMemorandumViewBinder extends ItemViewBinder<ClassMemorandumBean, ClassMemorandumViewBinder.ViewHolder> {
    private NoticeDialogSnap noticeDialogSnap;
    IGetMemorandumBy iGetMemorandumBy;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_class_memorandum, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassMemorandumBean bean) {
        holder.mTitleTv.setText(bean.getTitle());
        holder.mContentTv.setText(bean.getContent());
        holder.mTimeTv.setText(bean.getPublish_date());
        holder.view.setOnClickListener(v -> {
            noticeDialogSnap = new NoticeDialogSnap(v.getContext());
            noticeDialogSnap.show();
            noticeDialogSnap.setShowContent(bean.getTitle(), bean.getContent());
            setReaded(bean);
        });

        if (!TextUtils.isEmpty(bean.getKemu())) {
            holder.mSubject.setText(bean.getKemu());
        } else {
            holder.mSubject.setText("");
        }

        holder.mDeleteTv.setOnClickListener(v -> {

        });
    }

    private void setReaded(@NonNull ClassMemorandumBean bean) {
        iGetMemorandumBy = HttpUtil.getInstance().createApi(IGetMemorandumBy.class);
        iGetMemorandumBy.setMemorandumRead(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), bean.getBwid())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(stringBaseBean -> {
                    if (stringBaseBean.isSuccess()) {
                        bean.setIsread(true);
                    }
                }, new HttpError());
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

        View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
