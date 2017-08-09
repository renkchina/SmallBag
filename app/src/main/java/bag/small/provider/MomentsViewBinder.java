package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bag.small.R;
import bag.small.dialog.EvluateDialog;
import bag.small.entity.MomentsBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMoments;
import bag.small.interfaze.IDialog;
import bag.small.rx.RxUtil;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/8/6.
 */
public class MomentsViewBinder extends ItemViewBinder<MomentsBean, MomentsViewBinder.ViewHolder> {
    private IMoments iMoments;
    private List<Object> mItems;
    private List<Object> mMsgs;
    private MultiTypeAdapter multiTypeAdapter;
    private MultiTypeAdapter msgAdapter;
    private ViewHolder mHolder;
    private int evalutePosition = -1;
    private EvluateDialog dialog;

    public MomentsViewBinder() {
        iMoments = HttpUtil.getInstance().createApi(IMoments.class);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_fragment_users_note, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MomentsBean bean) {
        mHolder = holder;
        Context context = holder.iLikeIv.getContext();
        StringUtil.setTextView(holder.iNoteNameTv, bean.getTitle());

        StringUtil.setTextView(holder.iNoteTxtContentTv, String.valueOf(Html.fromHtml(bean.getContent())));
        ImageUtil.loadImages(context, holder.iNoteHeadIv, bean.getIcon());
        if (ListUtil.isEmpty(mItems)) {
            mItems = new Items();
        }
        if (ListUtil.isEmpty(mMsgs)) {
            mMsgs = new Items();
        }
        if (multiTypeAdapter == null) {
            multiTypeAdapter = new MultiTypeAdapter(mItems);
        }
        if (msgAdapter == null) {
            msgAdapter = new MultiTypeAdapter(mMsgs);
        }
        if (ListUtil.isEmpty(bean.getImages())) {
            holder.iNoteImageRecycler.setVisibility(View.GONE);
        } else {
            holder.iNoteImageRecycler.setVisibility(View.VISIBLE);
        }
        mItems.clear();
        mItems.addAll(bean.getImages());
        mMsgs.clear();
        mMsgs.addAll(bean.getRepay());
        multiTypeAdapter.register(String.class, new InnerMsgProviderImage());
        msgAdapter.register(MomentsBean.RepayBean.class, new EvaluationListBinder(this));
        holder.iNoteImageRecycler.setLayoutManager(new GridLayoutManager(context, 3));
        holder.iNoteEvaluationRecycler.setLayoutManager(new LinearLayoutManager(context));
//        holder.iNoteImageRecycler.setOnTouchListener((v, event) -> true);
        holder.iNoteImageRecycler.setAdapter(multiTypeAdapter);
        holder.iNoteEvaluationRecycler.setAdapter(msgAdapter);

        StringUtil.setTextView(holder.iTimeTv, bean.getCreate_at());
        if (bean.isCan_delete()) {
            holder.iDeleteMessageV.setVisibility(View.VISIBLE);
            holder.iDeleteMessageV.setOnClickListener(v -> {
                getAdapter().getItems().remove(bean);
                getAdapter().notifyDataSetChanged();
            });
        } else {
            holder.iDeleteMessageV.setVisibility(View.GONE);
        }
        if (bean.isCan_dianzan()) {
            holder.iLikeIv.setOnClickListener(v -> {

            });
        }

        if (TextUtils.isEmpty(bean.getReview())) {
            holder.sendBodyTv.setText("");
        } else {
            holder.sendBodyTv.setText(bean.getReview());
            bean.setReview("");
        }

        holder.iNoteEvaluationBtn.setOnClickListener(v -> {
            String evaluate = StringUtil.EditGetString(holder.iNoteEvaluationEdt);
            String reviewId;
            if (evalutePosition < 1) {
                reviewId = "0";
                holder.sendBodyTv.setText("");
            } else {
                reviewId = bean.getRepay().get(evalutePosition).getReview_id();
                holder.sendBodyTv.setText("回复" + bean.getRepay().get(evalutePosition).getTitle());
            }
            iMoments.getEvaluateMsg(UserPreferUtil.getInstanse().getRoleId(),
                    UserPreferUtil.getInstanse().getUserId(),
                    UserPreferUtil.getInstanse().getSchoolId(), 1, evaluate,
                    reviewId, bean.getCreater_id())
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .subscribe(bean1 -> {
                        Toast.makeText(context, bean1.getMsg(), Toast.LENGTH_SHORT).show();
                    }, new HttpError());
        });
    }

    void showEvaluationL(int position) {
        evalutePosition = position;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_fragment_user_note_head_iv)
        ImageView iNoteHeadIv;
        @Bind(R.id.item_fragment_user_note_name_tv)
        TextView iNoteNameTv;
        @Bind(R.id.item_fragment_delete_message_v)
        View iDeleteMessageV;
        @Bind(R.id.item_fragment_user_note_txt_content_tv)
        TextView iNoteTxtContentTv;
        @Bind(R.id.item_fragment_user_note_image_content_recycler)
        RecyclerView iNoteImageRecycler;
        @Bind(R.id.item_fragment_note_time_tv)
        TextView iTimeTv;
        @Bind(R.id.item_fragment_note_like_iv)
        ImageView iLikeIv;
        @Bind(R.id.item_fragment_note_show_send_message_iv)
        ImageView iShowSendMessageIv;
        @Bind(R.id.item_fragment_user_note_evaluation_recycler)
        RecyclerView iNoteEvaluationRecycler;
        @Bind(R.id.item_fragment_user_note_evaluation_edt)
        EditText iNoteEvaluationEdt;
        @Bind(R.id.item_fragment_notice_send_body_tv)
        TextView sendBodyTv;
        @Bind(R.id.item_fragment_user_note_evluation_btn)
        Button iNoteEvaluationBtn;
        @Bind(R.id.item_fragment_user_note_evaluation_ll)
        LinearLayout iNoteEvaluationLl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
