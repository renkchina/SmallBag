package bag.small.provider;

/**
 * Created by Administrator on 2017/7/31.
 */
public class TeachClass {
    private int jieci;
    private int nianji;
    private int banji;

    public int getJieci() {
        return jieci;
    }

    public void setJieci(int jieci) {
        this.jieci = jieci;
    }

    public int getNianji() {
        return nianji;
    }

    public void setNianji(int nianji) {
        this.nianji = nianji;
    }

    public int getBanji() {
        return banji;
    }

    public void setBanji(int banji) {
        this.banji = banji;
    }

    @Override
    public String toString() {
        return "TeachClass{" +
                "jieci=" + jieci +
                ", nianji=" + nianji +
                ", banji=" + banji +
                '}';
    }
}