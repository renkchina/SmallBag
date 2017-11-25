package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.MemorandumItemBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class MemorandumTxtViewBinder extends ItemViewBinder<MemorandumItemBean, MemorandumTxtViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_memorandum_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MemorandumItemBean memorandum) {
        if (memorandum.isHasnew()) {
            holder.itemRound.setVisibility(View.VISIBLE);
        } else {
            holder.itemRound.setVisibility(View.GONE);
        }
        holder.itemText.setText(memorandum.getName());
    }

    //0学科//1,时间
    public void setSortType(int sortType) {
        if (sortType > 0) {

        } else {

        }
        getAdapter().notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_memorandum_text)
        TextView itemText;
        @BindView(R.id.item_memorandum_round_txt)
        TextView itemRound;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
