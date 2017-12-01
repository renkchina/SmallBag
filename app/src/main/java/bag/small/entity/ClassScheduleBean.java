package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class ClassScheduleBean {

    /**
     * month : 11
     * weekly : [{"label":"周一","date_label":"27号","true_date":"2017-11-27","day_of_week":1},{"label":"周二","date_label":"28号","true_date":"2017-11-28","day_of_week":2},{"label":"周三","date_label":"29号","true_date":"2017-11-29","day_of_week":3},{"label":"周四","date_label":"30号","true_date":"2017-11-30","day_of_week":4},{"label":"周五","date_label":"01号","true_date":"2017-12-01","day_of_week":5},{"label":"周六","date_label":"02号","true_date":"2017-12-02","day_of_week":6},{"label":"周日","date_label":"03号","true_date":"2017-12-03","day_of_week":7}]
     * kechens : [{"label":"07:50 - 08:10","week_1":{"coloums":5,"show":true,"name":"升旗仪式","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"升旗仪式","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"08:10 - 08:20","week_1":{"coloums":5,"show":true,"name":"升旗仪式","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"升旗仪式","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"08:20 - 09:00","week_1":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_2":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_3":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_6":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"09:10 - 09:50","week_1":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_2":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_3":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_6":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"10:00 - 10:40","week_1":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_2":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_3":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_6":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"10:50 - 11:30","week_1":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_2":{"coloums":1,"show":true,"name":"体育1","ischange":false},"week_3":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_6":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"11:30 - 12:40","week_1":{"coloums":5,"show":true,"name":"体育1","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"体育1","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"12:40 - 13:20","week_1":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_2":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_3":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_6":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"13:20 - 13:40","week_1":{"coloums":5,"show":true,"name":"升旗仪式","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"升旗仪式","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"13:40 - 14:20","week_1":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_2":{"coloums":1,"show":true,"name":"午餐","ischange":false},"week_3":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"早读","ischange":false},"week_6":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"14:30 - 15:10","week_1":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_2":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_3":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_4":{"coloums":1,"show":true,"name":"休","ischange":false},"week_5":{"coloums":1,"show":true,"name":"语文","ischange":false},"week_6":{"coloums":1,"show":true,"name":"英文","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"15:10 - 15:25","week_1":{"coloums":5,"show":true,"name":"早读","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"早读","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"15:25 - 15:30","week_1":{"coloums":5,"show":true,"name":"体育1","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"体育1","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"15:30 - 16:00","week_1":{"coloums":5,"show":true,"name":"体育1","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"体育1","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}},{"label":"16:00","week_1":{"coloums":5,"show":true,"name":"体育1","ischange":false},"week_2":{"coloums":1,"show":false,"name":"","ischange":false},"week_3":{"coloums":1,"show":false,"name":"","ischange":false},"week_4":{"coloums":1,"show":false,"name":"休","ischange":false},"week_5":{"coloums":1,"show":false,"name":"体育1","ischange":false},"week_6":{"coloums":1,"show":true,"name":"","ischange":false},"week_7":{"coloums":1,"show":true,"name":"","ischange":false}}]
     * exchanges : ["2017-11-30调整为休息","2017-12-01调整为周三课程","2017-12-02调整为周三课程"]
     */

    private String month;
    private List<WeeklyBean> weekly;
    private List<KechensBean> kechens;
    private List<String> exchanges;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<WeeklyBean> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<WeeklyBean> weekly) {
        this.weekly = weekly;
    }

    public List<KechensBean> getKechens() {
        return kechens;
    }

    public void setKechens(List<KechensBean> kechens) {
        this.kechens = kechens;
    }

    public List<String> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<String> exchanges) {
        this.exchanges = exchanges;
    }

    public static class KechensBean {
        /**
         * label : 07:50 - 08:10
         * week_1 : {"coloums":5,"show":true,"name":"升旗仪式","ischange":false}
         * week_2 : {"coloums":1,"show":false,"name":"","ischange":false}
         * week_3 : {"coloums":1,"show":false,"name":"","ischange":false}
         * week_4 : {"coloums":1,"show":false,"name":"休","ischange":false}
         * week_5 : {"coloums":1,"show":false,"name":"升旗仪式","ischange":false}
         * week_6 : {"coloums":1,"show":true,"name":"","ischange":false}
         * week_7 : {"coloums":1,"show":true,"name":"","ischange":false}
         */

        private String label;
        private WeekBean week_1;
        private WeekBean week_2;
        private WeekBean week_3;
        private WeekBean week_4;
        private WeekBean week_5;
        private WeekBean week_6;
        private WeekBean week_7;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public WeekBean getWeek_1() {
            return week_1;
        }

        public void setWeek_1(WeekBean week_1) {
            this.week_1 = week_1;
        }

        public WeekBean getWeek_2() {
            return week_2;
        }

        public void setWeek_2(WeekBean week_2) {
            this.week_2 = week_2;
        }

        public WeekBean getWeek_3() {
            return week_3;
        }

        public void setWeek_3(WeekBean week_3) {
            this.week_3 = week_3;
        }

        public WeekBean getWeek_4() {
            return week_4;
        }

        public void setWeek_4(WeekBean week_4) {
            this.week_4 = week_4;
        }

        public WeekBean getWeek_5() {
            return week_5;
        }

        public void setWeek_5(WeekBean week_5) {
            this.week_5 = week_5;
        }

        public WeekBean getWeek_6() {
            return week_6;
        }

        public void setWeek_6(WeekBean week_6) {
            this.week_6 = week_6;
        }

        public WeekBean getWeek_7() {
            return week_7;
        }

        public void setWeek_7(WeekBean week_7) {
            this.week_7 = week_7;
        }



    }
}
