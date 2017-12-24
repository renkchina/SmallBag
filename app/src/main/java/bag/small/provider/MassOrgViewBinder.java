package bag.small.provider;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.MassOrgBean;
import bag.small.entity.TeacherMemorandumBean;
import bag.small.ui.activity.ClassChatActivity;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class MassOrgViewBinder extends ItemViewBinder<MassOrgBean, MassOrgViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_big_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MassOrgBean bean) {
        Context context = holder.root.getContext();
        holder.itemRoundTxt.setVisibility(View.GONE);
        holder.itemText.setText("社团");
        StringUtil.setTextView(holder.itemText, bean.getName());

        holder.root.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("banjiId",bean.getBanji_id());
            intent.putExtra("groupId",bean.getGroup_id());
            intent.putExtra("name",bean.getName());
            intent.setClass(context, ClassChatActivity.class);
            context.startActivity(intent);
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
        TextView itemRoundTxt;
        @BindView(R.id.item_memo_start_v)
        View start;
        View root;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }
}
