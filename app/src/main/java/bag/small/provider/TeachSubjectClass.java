package bag.small.provider;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubjectClass {
    private boolean isAdd;

    public TeachSubjectClass(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

}