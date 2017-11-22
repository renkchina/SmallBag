package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import bag.small.R;
import bag.small.entity.ConnectionBinder;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class ConnectionViewBinder2 extends ItemViewBinder<ConnectionBinder, ConnectionViewBinder2.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_connection_grid_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ConnectionBinder bean) {
        holder.iCountIv.setVisibility(View.GONE);
        StringUtil.setTextView(holder.iTitleIv, bean.getTitle());
        holder.iIconIv.setImageResource(bean.getResImage());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_connection_icon_iv)
        ImageView iIconIv;
        @BindView(R.id.item_connection_title_iv)
        TextView iTitleIv;
        @BindView(R.id.item_connection_count_iv)
        TextView iCountIv;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }


}
