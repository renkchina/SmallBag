package bag.small.entity;

/**
 * Created by Administrator on 2017/12/1.
 */

public  class WeeklyBean {
    /**
     * label : 周一
     * date_label : 27号
     * true_date : 2017-11-27
     * day_of_week : 1
     */

    private String label;
    private String date_label;
    private String true_date;
    private int day_of_week;

    public WeeklyBean(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate_label() {
        return date_label;
    }

    public void setDate_label(String date_label) {
        this.date_label = date_label;
    }

    public String getTrue_date() {
        return true_date;
    }

    public void setTrue_date(String true_date) {
        this.true_date = true_date;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(int day_of_week) {
        this.day_of_week = day_of_week;
    }
}
