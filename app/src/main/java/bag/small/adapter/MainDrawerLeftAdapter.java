package bag.small.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.entity.MainLeftBean;
import bag.small.ui.activity.AccountStudentManagerActivity;
import bag.small.ui.activity.AccountTeacherManagerActivity;
import bag.small.ui.activity.LoginActivity;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MainDrawerLeftAdapter extends MyBaseListViewAdapter<MainLeftBean> {

    public MainDrawerLeftAdapter(List<MainLeftBean> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainLeftBean bean = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_drawer_left_list_ll, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemDrawerLeftV.setBackgroundResource(bean.getResId());
        holder.itemDrawerLeftTv.setText(bean.getTitleRes());
        holder.mainv.setOnClickListener(v -> {
            switch (bean.getId()) {
                case 1:
                    if (UserPreferUtil.getInstanse().isTeacher()) {
                        gotoActivity(AccountTeacherManagerActivity.class, false, null);
                    } else {
                        gotoActivity(AccountStudentManagerActivity.class, false, null);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    UserPreferUtil.getInstanse().clear();
                    skipActivity(LoginActivity.class);
                    break;
            }

        });
        return convertView;
    }

    public void skipActivity(@NonNull Class<?> cls) {
        context.startActivity(new Intent(context, cls));
        ((Activity) context).finish();
    }

    static class ViewHolder {
        @Bind(R.id.item_drawer_left_v)
        View itemDrawerLeftV;
        @Bind(R.id.item_drawer_left_tv)
        TextView itemDrawerLeftTv;
        @Bind(R.id.main_drawer_left_account_manager_ll)
        LinearLayout mainv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
