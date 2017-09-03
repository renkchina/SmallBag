package bag.small.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bag.small.R;
import bag.small.entity.ConnectionBinder;
import bag.small.ui.activity.ChoiceInterestClassActivity;
import bag.small.ui.activity.EducationalNoticeActivity;
import bag.small.ui.activity.InterestClassByTeacherActivity;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/5.
 */
public class ConnectionViewBinder extends ItemViewBinder<ConnectionBinder, ConnectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_connection_grid_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ConnectionBinder bean) {
        Context context = holder.iIconIv.getContext();
        if (bean.getCount() < 1) {
            holder.iCountIv.setVisibility(View.GONE);
        } else {
            holder.iCountIv.setVisibility(View.VISIBLE);
            holder.iCountIv.setText(bean.getCount() + "");
        }
        StringUtil.setTextView(holder.iTitleIv, bean.getTitle());
        holder.iIconIv.setImageResource(bean.getResImage());
        holder.rootView.setOnClickListener(v -> {
            switch (bean.getId()) {
                case 2:
                goActivity(EducationalNoticeActivity.class, holder.iIconIv.getContext());
                break;
                case 1:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    Toast.makeText(context,"敬请期待",Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    if (UserPreferUtil.getInstanse().isTeacher()) {
                        goActivity(InterestClassByTeacherActivity.class, context);
                    } else {
                        goActivity(ChoiceInterestClassActivity.class, context);
                    }

                    break;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_connection_icon_iv)
        ImageView iIconIv;
        @Bind(R.id.item_connection_title_iv)
        TextView iTitleIv;
        @Bind(R.id.item_connection_count_iv)
        TextView iCountIv;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private void goActivity(Class<? extends Activity> cls,
                            Context context) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

    }

}
