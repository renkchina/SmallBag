package bag.small.entity;

/**
 * Created by Administrator on 2017/8/13.
 */

public class NoticeCountBean {

    /**
     * teach_notice : 3
     * student_notice : 0
     * interest_notice : 0
     */

    private int teach_notice;
    private int student_notice;
    private int interest_notice;

    public int getTeach_notice() {
        return teach_notice;
    }

    public void setTeach_notice(int teach_notice) {
        this.teach_notice = teach_notice;
    }

    public int getStudent_notice() {
        return student_notice;
    }

    public void setStudent_notice(int student_notice) {
        this.student_notice = student_notice;
    }

    public int getInterest_notice() {
        return interest_notice;
    }

    public void setInterest_notice(int interest_notice) {
        this.interest_notice = interest_notice;
    }

    @Override
    public String toString() {
        return "NoticeCountBean{" +
                "teach_notice=" + teach_notice +
                ", student_notice=" + student_notice +
                ", interest_notice=" + interest_notice +
                '}';
    }
}
