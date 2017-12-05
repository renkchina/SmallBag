package bag.small.entity;

/**
 * Created by Administrator on 2017/12/5.
 */

public class LabelBean {

    /**
     * title : 07:50 - 08:10
     * start_at : 07:50
     * end_at : 08:10
     * showtitle : false
     */

    private String title;
    private String start_at;
    private String end_at;
    private boolean showtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public boolean isShowtitle() {
        return showtitle;
    }

    public void setShowtitle(boolean showtitle) {
        this.showtitle = showtitle;
    }
}
