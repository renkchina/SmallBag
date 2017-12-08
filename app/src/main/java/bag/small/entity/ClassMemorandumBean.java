package bag.small.entity;

/**
 * Created by Administrator on 2017/12/1.
 */
public class ClassMemorandumBean {


    /**
     * title : 语文作为
     * content : 语文作为你诶荣
     * bwid : 35
     * publish_date : 2017-11-14 23:38:31
     * unreads : 6
     */

    private String title;
    private String content;
    private String bwid;
    private String publish_date;
    private int unreads;
    private boolean isread;
    private String kemu;
    private boolean showDel;

    public boolean isShowDel() {
        return showDel;
    }

    public void setShowDel(boolean showDel) {
        this.showDel = showDel;
    }

    public String getKemu() {
        return kemu;
    }

    public void setKemu(String kemu) {
        this.kemu = kemu;
    }

    public boolean isIsread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBwid() {
        return bwid;
    }

    public void setBwid(String bwid) {
        this.bwid = bwid;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public int getUnreads() {
        return unreads;
    }

    public void setUnreads(int unreads) {
        this.unreads = unreads;
    }
}