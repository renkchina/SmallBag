package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.china.rxbus.RxBus;

import bag.small.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/9/22.
 */
public class RoleAddBeanViewBinder extends ItemViewBinder<String, RoleAddBeanViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_main_left_add_role_btn, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String roleAddBean) {
        holder.addRole.setOnClickListener(v -> RxBus.get().send(301));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.main_drawer_left_add_account_btn)
        Button addRole;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
