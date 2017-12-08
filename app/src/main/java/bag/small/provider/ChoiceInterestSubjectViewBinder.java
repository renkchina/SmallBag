package bag.small.provider;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.ChoiceClassLists;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/1.
 */
public class ChoiceInterestSubjectViewBinder extends ItemViewBinder<ChoiceClassLists.KechenBean, ChoiceInterestSubjectViewBinder.ViewHolder> {

    NoticeDialogSnap noticeDialogSnap;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChoiceClassLists.KechenBean bean) {
        holder.itemRound.setVisibility(View.GONE);
        holder.itemText.setText(bean.getName());
        holder.view.setOnClickListener(v -> {
            noticeDialogSnap = new NoticeDialogSnap(v.getContext());
            noticeDialogSnap.show();
            if (TextUtils.isEmpty(bean.getName())) {
                bean.setName("");
            }
            if (TextUtils.isEmpty(bean.getDescriptions())) {
                bean.setDescriptions("");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                noticeDialogSnap.setShowContent(bean.getName(), Html.fromHtml(bean.getDescriptions(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                noticeDialogSnap.setShowContent(bean.getName(), Html.fromHtml(bean.getDescriptions()));
            }
            noticeDialogSnap.setList(bean.getImages());
        });
        if (getPosition(holder) % 4 == 0) {
            holder.start.setBackgroundResource(R.mipmap.purple_start);
        } else if (getPosition(holder) % 3 == 0) {
            holder.start.setBackgroundResource(R.mipmap.blue_start);
        } else if (getPosition(holder) % 2 == 0) {
            holder.start.setBackgroundResource(R.mipmap.yellow_start);
        } else {
            holder.start.setBackgroundResource(R.mipmap.green_start);
        }


    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_memorandum_text)
        TextView itemText;
        @BindView(R.id.item_memorandum_round_txt)
        TextView itemRound;
        @BindView(R.id.item_memo_start_v)
        View start;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
