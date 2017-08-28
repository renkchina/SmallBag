package bag.small.entity;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MainLeftBean {
    int resId;
    int  titleRes;

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

    public MainLeftBean(int resId, int titleRes) {
        this.resId = resId;
        this.titleRes = titleRes;
    }

    public MainLeftBean() {
    }

    @Override
    public String toString() {
        return "MainLeftBean{" +
                "resId=" + resId +
                ", titleRes=" + titleRes +
                '}';
    }
}
