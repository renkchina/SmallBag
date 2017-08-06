package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.MomentsBean;
import bag.small.view.MyListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/6.
 */
public class MomentsViewBinder extends ItemViewBinder<MomentsBean, MomentsViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_fragment_users_note, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MomentsBean bean) {

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
        @Bind(R.id.item_fragment_user_note_evaluation_listview)
        MyListView iNoteEvaluationListview;
        @Bind(R.id.item_fragment_user_note_evaluation_edt)
        EditText iNoteEvaluationEdt;
        @Bind(R.id.item_fragment_user_note_evluation_btn)
        Button iNoteEvluationBtn;
        @Bind(R.id.item_fragment_user_note_evaluation_ll)
        LinearLayout iNoteEvaluationLl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
