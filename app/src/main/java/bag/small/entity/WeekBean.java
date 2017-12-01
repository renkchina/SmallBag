package bag.small.entity;

/**
 * Created by Administrator on 2017/12/1.
 */

public class WeekBean {
    /**
     * coloums : 5
     * show : true
     * name : 升旗仪式
     * ischange : false
     */

    private int coloums;
    private boolean show;
    private String name;
    private boolean ischange;

    public int getColoums() {
        return coloums;
    }

    public void setColoums(int coloums) {
        this.coloums = coloums;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIschange() {
        return ischange;
    }

    public void setIschange(boolean ischange) {
        this.ischange = ischange;
    }

    public WeekBean(String name) {
        this.name = name;
    }
}