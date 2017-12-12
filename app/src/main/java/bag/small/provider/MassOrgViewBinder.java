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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MassOrgBean teacherMemorandum) {
        Context context = holder.root.getContext();
        holder.itemRoundTxt.setVisibility(View.GONE);
        holder.itemText.setText("社团");

        holder.root.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(context, ClassChatActivity.class);
            context.startActivity(intent);
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_memorandum_text)
        TextView itemText;
        @BindView(R.id.item_memorandum_round_txt)
        TextView itemRoundTxt;
        View root;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }
}
