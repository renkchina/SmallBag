package bag.small.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.entity.LoginResult;
import bag.small.interfaze.IViewBinder;
import bag.small.ui.activity.MainActivity;
import bag.small.utils.ImageUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;


/**
 * Created by Administrator on 2017/7/23.
 */
public class AccountViewBinder extends ItemViewBinder<LoginResult.RoleBean, AccountViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_left_drawer_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoginResult.RoleBean bean) {
        Context context = holder.rootView.getContext();
        if (TextUtils.isEmpty(bean.getName())) {
            holder.drawerImageIv.setImageResource(R.mipmap.add_role);
            holder.drawerNameTv.setText("添加角色");
            holder.rootView.setOnClickListener(v -> RxBus.get().send(301));
        } else {
            ImageUtil.loadCircleImages(context, holder.drawerImageIv, bean.getLogo());
            holder.drawerNameTv.setText(bean.getName());
            holder.rootView.setOnClickListener(v -> {
                if (!bean.isSelected()) {
                    setAllUnSelect();
                    bean.setSelected(true);
                    UserPreferUtil.getInstanse().setUserInfomation(bean);
                    RxBus.get().send(300);
                    getAdapter().notifyDataSetChanged();
                }
            });
            if (bean.isSelected()) {
                holder.rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else {
                holder.rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }

    }

    private void setAllUnSelect() {
        List<?> items = getAdapter().getItems();
        if (ListUtil.isEmpty(items)) {
            return;
        }
        List<LoginResult.RoleBean> lists = new ArrayList<>();
        for (Object o : items) {
            if (o instanceof LoginResult.RoleBean) {
                lists.add((LoginResult.RoleBean) o);
            }
        }
        if (ListUtil.unEmpty(lists)) {
            for (LoginResult.RoleBean bean : lists) {
                bean.setSelected(false);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_main_drawer_image_iv)
        ImageView drawerImageIv;
        @BindView(R.id.item_main_drawer_name_tv)
        TextView drawerNameTv;
        View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
