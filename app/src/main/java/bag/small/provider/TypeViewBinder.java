package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.entity.GradeClass;
import bag.small.entity.RelateBanjiBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/29.
 */
public class TypeViewBinder extends ItemViewBinder<GradeClass, TypeViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_relate_banji, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GradeClass bean) {
        holder.view.setOnClickListener(v -> {
            setAllUnchecked();
            bean.setChecked(true);
            getAdapter().notifyDataSetChanged();
        });
        if (!bean.isChecked()) {
            holder.itemCheckBtn.setBackground(null);
        } else {
            holder.itemCheckBtn.setBackgroundResource(R.mipmap.choice);
        }
        holder.itemCheckTv.setText(bean.getKemuName());
    }

    private void setAllUnchecked() {
        List<GradeClass> lists = (List<GradeClass>) getAdapter().getItems();
        for (GradeClass grade : lists) {
            grade.setChecked(false);
        }
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
