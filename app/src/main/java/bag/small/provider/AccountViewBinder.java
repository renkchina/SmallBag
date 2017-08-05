package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bag.small.R;
import bag.small.entity.LoginResult;
import bag.small.interfaze.IViewBinder;
import bag.small.utils.ImageUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;


/**
 * Created by Administrator on 2017/7/23.
 */
public class AccountViewBinder extends ItemViewBinder<LoginResult.RoleBean, AccountViewBinder.ViewHolder> {

    private IViewBinder iViewBinder;

    public AccountViewBinder(IViewBinder iViewBinder) {
        this.iViewBinder = iViewBinder;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_left_drawer_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoginResult.RoleBean bean) {
        ImageUtil.loadCircleImages(holder.rootView.getContext(), holder.drawerImageIv, bean.getLogo());
        holder.drawerNameTv.setText(bean.getName());
        holder.rootView.setOnClickListener(v -> {
            if (iViewBinder != null)
                iViewBinder.click(getPosition(holder));
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_main_drawer_image_iv)
        ImageView drawerImageIv;
        @Bind(R.id.item_main_drawer_name_tv)
        TextView drawerNameTv;
        View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
