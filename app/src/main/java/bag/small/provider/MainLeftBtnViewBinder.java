package bag.small.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bag.small.R;
import bag.small.entity.MainLeftBean;
import bag.small.ui.activity.AccountStudentManagerActivity;
import bag.small.ui.activity.AccountTeacherManagerActivity;
import bag.small.ui.activity.LoginActivity;
import bag.small.ui.activity.WebViewActivity;
import bag.small.utils.LayoutUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/9/22.
 */
public class MainLeftBtnViewBinder extends ItemViewBinder<MainLeftBean, MainLeftBtnViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_drawer_left_list_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainLeftBean bean) {
        Context context = holder.mainv.getContext();
        ViewGroup.LayoutParams params;
        if (bean.getTitleRes() < 1) {
            holder.upLine.setVisibility(View.GONE);
            holder.downLine.setVisibility(View.GONE);
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LayoutUtil.dip2px(context, 20));
            holder.mainv.setVisibility(View.INVISIBLE);
        } else {
            holder.mainv.setVisibility(View.VISIBLE);
            holder.upLine.setVisibility(View.VISIBLE);
            holder.downLine.setVisibility(View.VISIBLE);
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LayoutUtil.dip2px(context, 45));
            if (bean.getResId() > 0) {
                String content = context.getString(bean.getTitleRes());
                holder.itemDrawerLeftV.setBackgroundResource(bean.getResId());
                holder.itemDrawerLeftV.setVisibility(View.VISIBLE);
                holder.itemDrawerLeftTv.setTextColor(ContextCompat.getColor(context, R.color.txt_gray));
                holder.itemDrawerLeftTv.setTextSize(16);
                holder.itemDrawerLeftTv.setText(content);
            } else {
                String content = context.getString(bean.getTitleRes());
                holder.itemDrawerLeftTv.setText(Html.fromHtml("<u>" + content + "</u>"));
                holder.itemDrawerLeftTv.setTextColor(ContextCompat.getColor(context, R.color.link_blue));
                holder.itemDrawerLeftV.setVisibility(View.GONE);
                holder.itemDrawerLeftTv.setTextSize(12);
            }

            holder.mainv.setOnClickListener(v -> {
                switch (bean.getId()) {
                    case 1:
                        if (UserPreferUtil.getInstanse().isTeacher()) {
                            gotoActivity(context, AccountTeacherManagerActivity.class);
                        } else {
                            gotoActivity(context, AccountStudentManagerActivity.class);
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                        Toast.makeText(context, "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        UserPreferUtil.getInstanse().clear();
                        skipActivity(context, LoginActivity.class);
                        break;
                    case 6:
                        gotoActivity(context, WebViewActivity.class);
                        break;
                }
            });
        }

        holder.mainv.setLayoutParams(params);
    }

    public void skipActivity(Context context, @NonNull Class<?> cls) {
        context.startActivity(new Intent(context, cls));
        ((Activity) context).finish();
    }

    private void gotoActivity(Context context, @NonNull Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_view_up_line)
        View upLine;
        @BindView(R.id.item_view_down_line)
        View downLine;
        @BindView(R.id.item_drawer_left_v)
        View itemDrawerLeftV;
        @BindView(R.id.item_drawer_left_tv)
        TextView itemDrawerLeftTv;
        @BindView(R.id.main_drawer_left_account_manager_ll)
        LinearLayout mainv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
