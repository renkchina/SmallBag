package bag.small.provider;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubjectClass {
    private boolean isAdd;

    public TeachSubjectClass(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public TeachSubjectClass() {
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    private String kemu;
    private int jieci;
    private int nianji;
    private String banji;

    public String getKemu() {
        return kemu;
    }

    public void setKemu(String kemu) {
        this.kemu = kemu;
    }

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

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }
}