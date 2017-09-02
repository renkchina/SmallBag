package bag.small.entity;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MainLeftBean {
    int id;
    int resId;
    int titleRes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public MainLeftBean(int id, int resId, int titleRes) {
        this.id = id;
        this.resId = resId;
        this.titleRes = titleRes;
    }

    public MainLeftBean() {
    }

    @Override
    public String toString() {
        return "MainLeftBean{" +
                "id=" + id +
                ", resId=" + resId +
                ", titleRes=" + titleRes +
                '}';
    }
}
