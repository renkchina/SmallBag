package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
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
        List<WeekBean> weekbean;
        if (getPosition(holder) == 0) {
            holder.view.setBackgroundResource(R.color.data_day_gray);
            List<WeeklyBean> weekly = itemBean.getWeeklyBean();
            holder.TopDateTv.setTextSize(14);
            StringUtil.setTextView(holder.TopDateTv, weekly.get(0).getLabel() + "\n月");
            StringUtil.setTextView(holder.OneTv, weekly.get(1).getLabel() + "\n" + weekly.get(1).getDate_label());
            StringUtil.setTextView(holder.TwoTv, weekly.get(2).getLabel() + "\n" + weekly.get(2).getDate_label());
            StringUtil.setTextView(holder.ThreeTv, weekly.get(3).getLabel() + "\n" + weekly.get(3).getDate_label());
            StringUtil.setTextView(holder.FourTv, weekly.get(4).getLabel() + "\n" + weekly.get(4).getDate_label());
            StringUtil.setTextView(holder.RestTv, weekly.get(5).getLabel() + "\n" + weekly.get(5).getDate_label());
            StringUtil.setTextView(holder.FiveTv, weekly.get(6).getLabel() + "\n" + weekly.get(6).getDate_label());
            StringUtil.setTextView(holder.SixTv, weekly.get(7).getLabel() + "\n" + weekly.get(7).getDate_label());
            ClassScheduleItemBean bean = (ClassScheduleItemBean) getAdapter().getItems().get(1);
            weekbean = bean.getItem();
            int size = weekbean.size();
            for (int i = 0; i < size; i++) {
                if (weekbean.get(i).isIsnow()) {
                    switch (i) {
                        case 0:
                            setTvBg(holder, holder.OneTv);
                            break;
                        case 1:
                            setTvBg(holder, holder.TwoTv);
                            break;
                        case 2:
                            setTvBg(holder, holder.ThreeTv);
                            break;
                        case 3:
                            setTvBg(holder, holder.FourTv);
                            break;
                        case 4:
                            setTvBg(holder, holder.RestTv);
                            break;
                        case 5:
                            setTvBg(holder, holder.FiveTv);
                            break;
                        case 6:
                            setTvBg(holder, holder.SixTv);
                            break;
                    }
                }
            }
        } else {
            holder.view.setBackgroundResource(R.color.white);//data_month_gray
            weekbean = itemBean.getItem();
            holder.TopDateTv.setTextSize(12);
            if (itemBean.getSubject().isShowtitle()) {
                StringUtil.setTextView(holder.TopDateTv, itemBean.getSubject().getTitle());
            } else {
                StringUtil.setTextView(holder.TopDateTv,
                        itemBean.getSubject().getStart_at() + "\n" + itemBean.getSubject().getEnd_at());
            }
            setContent(holder.OneTv, weekbean.get(0), holder.view);
            setContent(holder.TwoTv, weekbean.get(1), holder.view);
            setContent(holder.ThreeTv, weekbean.get(2), holder.view);
            setContent(holder.FourTv, weekbean.get(3), holder.view);
            setContent(holder.RestTv, weekbean.get(4), holder.view);
            setContent(holder.FiveTv, weekbean.get(5), holder.view);
            setContent(holder.SixTv, weekbean.get(6), holder.view);
            setContent(holder, weekbean);
        }
//13880463990
    }

    private void setContent(ViewHolder holder, List<WeekBean> weekbean) {
        WeekBean bean = weekbean.get(0);
        if (bean.getColoums() > 1) {
            setTvVisiable(holder, holder.OneTv, bean.getColoums());
        } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            setAllVisiable(holder, holder.OneTv, lp);
        }
    }

    private void setTvBg(ViewHolder holder, View view) {
        holder.view.setBackgroundResource(R.color.data_day_gray);
        holder.OneTv.setBackgroundResource(R.color.data_day_gray);
        holder.TwoTv.setBackgroundResource(R.color.data_day_gray);
        holder.ThreeTv.setBackgroundResource(R.color.data_day_gray);
        holder.FourTv.setBackgroundResource(R.color.data_day_gray);
        holder.RestTv.setBackgroundResource(R.color.data_day_gray);
        holder.FiveTv.setBackgroundResource(R.color.data_day_gray);
        holder.SixTv.setBackgroundResource(R.color.data_day_gray);
        view.setBackgroundResource(R.color.item_schedule_bg);
    }

    private void setTvVisiable(ViewHolder holder, TextView view, int size) {
        holder.OneTv.setVisibility(View.GONE);
        holder.oneLine.setVisibility(View.GONE);
        holder.TwoTv.setVisibility(View.GONE);
        holder.twoLine.setVisibility(View.GONE);
        holder.ThreeTv.setVisibility(View.GONE);
        holder.threeLine.setVisibility(View.GONE);
        holder.FourTv.setVisibility(View.GONE);
        holder.fourLine.setVisibility(View.GONE);
        holder.RestTv.setVisibility(View.GONE);
        holder.fiveLine.setVisibility(View.VISIBLE);
        holder.FiveTv.setVisibility(View.VISIBLE);
        holder.sexLine.setVisibility(View.VISIBLE);
        holder.SixTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        switch (size){
            case 2:
                holder.twoLine.setVisibility(View.VISIBLE);
                holder.threeLine.setVisibility(View.VISIBLE);
                holder.fourLine.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.twoLine.setVisibility(View.VISIBLE);
                holder.threeLine.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.twoLine.setVisibility(View.VISIBLE);
                break;
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, size);
        view.setLayoutParams(lp);

    }

    private void setAllVisiable(ViewHolder holder, View view, LinearLayout.LayoutParams lp) {
        holder.OneTv.setVisibility(View.VISIBLE);
        holder.oneLine.setVisibility(View.VISIBLE);
        holder.TwoTv.setVisibility(View.VISIBLE);
        holder.twoLine.setVisibility(View.VISIBLE);
        holder.ThreeTv.setVisibility(View.VISIBLE);
        holder.threeLine.setVisibility(View.VISIBLE);
        holder.FourTv.setVisibility(View.VISIBLE);
        holder.fourLine.setVisibility(View.VISIBLE);
        holder.RestTv.setVisibility(View.VISIBLE);
        holder.fiveLine.setVisibility(View.VISIBLE);
        holder.FiveTv.setVisibility(View.VISIBLE);
        holder.SixTv.setVisibility(View.VISIBLE);
        view.setLayoutParams(lp);
    }


    private void setContent(TextView textView, WeekBean bean, View view) {
        StringUtil.setTextView(textView, bean.getName());
        if (bean.isIsnow()) {
            textView.setBackgroundResource(R.color.item_schedule_bg);
        } else {
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
        @BindView(R.id.one_line)
        View oneLine;
        @BindView(R.id.two_line)
        View twoLine;
        @BindView(R.id.three_line)
        View threeLine;
        @BindView(R.id.four_line)
        View fourLine;
        @BindView(R.id.five_line)
        View fiveLine;
        @BindView(R.id.sex_line)
        View sexLine;


        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
