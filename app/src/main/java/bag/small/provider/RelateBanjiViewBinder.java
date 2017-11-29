package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.RelateBanjiBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/29.
 */
public class RelateBanjiViewBinder extends ItemViewBinder<RelateBanjiBean, RelateBanjiViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_relate_banji, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RelateBanjiBean bean) {
        holder.view.setOnClickListener(v -> {
            if (bean.isChecked()) {
                holder.itemCheckBtn.setBackgroundResource(R.color.white);
                bean.setChecked(false);
            } else {
                holder.itemCheckBtn.setBackgroundResource(R.mipmap.choice);
                bean.setChecked(true);
            }
        });
        holder.itemCheckTv.setText(bean.getNianji() + "年级" + bean.getBanci() + "班");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_check_tv)
        TextView itemCheckTv;
        @BindView(R.id.item_check_btn)
        Button itemCheckBtn;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
