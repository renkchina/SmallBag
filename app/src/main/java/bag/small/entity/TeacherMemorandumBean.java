package bag.small.entity;

/**
 * Created by Administrator on 2017/11/24.
 */

public class TeacherMemorandumBean {

    /**
     * banji_id : 339
     * name : 一年级
     * banci : 二
     * unread : 0
     * kemu : 思想品德
     */

    private String banji_id;
    private String name;
    private String banci;
    private int unread;
    private String kemu;

    public String getBanji_id() {
        return banji_id;
    }

    public void setBanji_id(String banji_id) {
        this.banji_id = banji_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanci() {
        return banci;
    }

    public void setBanci(String banci) {
        this.banci = banci;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public String getKemu() {
        return kemu;
    }

    public void setKemu(String kemu) {
        this.kemu = kemu;
    }
}
