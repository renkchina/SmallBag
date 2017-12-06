package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.entity.ClassScheduleBean;
import bag.small.entity.ClassScheduleItemBean;
import bag.small.entity.WeekBean;
import bag.small.entity.WeeklyBean;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/11/24.
 */
public class ClassScheduleViewBinder extends ItemViewBinder<ClassScheduleItemBean, ClassScheduleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_class_schedule_layout_horizontal, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassScheduleItemBean itemBean) {

        if (getPosition(holder) == 0) {
            holder.view.setBackgroundResource(R.color.data_day_gray);
            List<WeeklyBean> weekly = itemBean.getWeeklyBean();
            holder.TopDateTv.setTextSize(14);
            StringUtil.setTextView(holder.TopDateTv, weekly.get(0).getLabel() + "\n月");
            StringUtil.setTextView(holder.OneTv, weekly.get(1).getLabel() + weekly.get(1).getDate_label());
            StringUtil.setTextView(holder.TwoTv, weekly.get(2).getLabel() + weekly.get(2).getDate_label());
            StringUtil.setTextView(holder.ThreeTv, weekly.get(3).getLabel() + weekly.get(3).getDate_label());
            StringUtil.setTextView(holder.FourTv, weekly.get(4).getLabel() + weekly.get(4).getDate_label());
            StringUtil.setTextView(holder.RestTv, weekly.get(5).getLabel() + weekly.get(5).getDate_label());
            StringUtil.setTextView(holder.FiveTv, weekly.get(6).getLabel() + weekly.get(6).getDate_label());
            StringUtil.setTextView(holder.SixTv, weekly.get(7).getLabel() + weekly.get(7).getDate_label());
        } else {
            holder.view.setBackgroundResource(R.color.white);//data_month_gray
            List<WeekBean> weekbean = itemBean.getItem();
            holder.TopDateTv.setTextSize(12);
            if(itemBean.getSubject().isShowtitle()){
                StringUtil.setTextView(holder.TopDateTv, itemBean.getSubject().getTitle());
            }else{
                StringUtil.setTextView(holder.TopDateTv,
                        itemBean.getSubject().getStart_at()+"\n"+itemBean.getSubject().getEnd_at());
            }
            setContent(holder.OneTv, weekbean.get(0));
            setContent(holder.TwoTv, weekbean.get(1));
            setContent(holder.ThreeTv, weekbean.get(2));
            setContent(holder.FourTv, weekbean.get(3));
            setContent(holder.RestTv, weekbean.get(4));
            setContent(holder.FiveTv, weekbean.get(5));
            setContent(holder.SixTv, weekbean.get(6));
        }
//13880463990
    }

    private String lineFeed(String string) {
        return string.replace("-", "\n");
    }

    private void setContent(TextView textView,WeekBean bean) {
        StringUtil.setTextView(textView, bean.getName());
        if (bean.isIsnow()){
            textView.setBackgroundResource(R.color.item_schedule_bg);
        }else{
            textView.setBackgroundResource(R.color.white);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.class_schedule_top_date_tv)
        TextView TopDateTv;
        @BindView(R.id.class_schedule_one_tv)
        TextView OneTv;
        @BindView(R.id.class_schedule_two_tv)
        TextView TwoTv;
        @BindView(R.id.class_schedule_three_tv)
        TextView ThreeTv;
        @BindView(R.id.class_schedule_four_tv)
        TextView FourTv;
        @BindView(R.id.class_schedule_rest_tv)
        TextView RestTv;
        @BindView(R.id.class_schedule_five_tv)
        TextView FiveTv;
        @BindView(R.id.class_schedule_six_tv)
        TextView SixTv;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
