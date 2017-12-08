package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import java.util.List;

import bag.small.R;
import bag.small.dialog.ListGridDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.BaseBean;
import bag.small.entity.ClassMemorandumBean;
import bag.small.entity.NameString;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IGetMemorandumBy;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/12/1.
 */
public class ClassMemorandumViewBinder extends ItemViewBinder<ClassMemorandumBean, ClassMemorandumViewBinder.ViewHolder> {
    private NoticeDialogSnap noticeDialogSnap;
    private IGetMemorandumBy iGetMemorandumBy;

    private boolean isTeacher;
    private String classId;

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

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

            iGetMemorandumBy = HttpUtil.getInstance().createApi(IGetMemorandumBy.class);
            if (isTeacher) {
                ListGridDialog listGridDialog = new ListGridDialog(v.getContext());
                getUnRead(bean, listGridDialog);
            } else {
                noticeDialogSnap = new NoticeDialogSnap(v.getContext());
                noticeDialogSnap.show();
                noticeDialogSnap.setShowContent(bean.getTitle(), bean.getContent());
                setReaded(bean);
            }
        });

        if (!TextUtils.isEmpty(bean.getKemu())) {
            holder.mSubject.setText(bean.getKemu());
        } else {
            holder.mSubject.setText("");
        }
        if (bean.isShowDel()) {
            holder.mRoundedDeleteTv.setVisibility(View.VISIBLE);
            holder.mRoundedDeleteTv.setOnClickListener(v -> {
                RxBus.get().send(911, bean.getBwid());
                getAdapter().getItems().remove(bean);
                getAdapter().notifyDataSetChanged();
            });
        } else {
            holder.mRoundedDeleteTv.setVisibility(View.GONE);
        }



    }

    private void getUnRead(ClassMemorandumBean bean, ListGridDialog listGridDialog) {
        iGetMemorandumBy.getClassUnReadMemorandum(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId(), classId, bean.getBwid())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess()) {
                        listGridDialog.show();
                        if (ListUtil.isEmpty(listBaseBean.getData())) {
                            listGridDialog.setShowContent("以下学生未阅读此信息", "");
                        } else {
                            listGridDialog.setShowContent("以下学生未阅读此信息", null);
                            listGridDialog.setList(listBaseBean.getData());
                        }
                    }
                }, new HttpError());
    }


    private void setReaded(@NonNull ClassMemorandumBean bean) {

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
        @BindView(R.id.item_class_memorandum_delete_rounded_tv)
        TextView mRoundedDeleteTv;

        View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
