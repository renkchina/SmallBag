package bag.small.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ClassScheduleItemBean {

    private List<WeeklyBean> weeklyBean = new ArrayList<>();

    private List<WeekBean> item = new ArrayList<>();
    private LabelBean subject;

    public LabelBean getSubject() {
        return subject;
    }

    public void setLabelBean(LabelBean subject) {
        this.subject = subject;
    }

    public List<WeeklyBean> getWeeklyBean() {
        return weeklyBean;
    }


    public List<WeekBean> getItem() {
        return item;
    }
}
