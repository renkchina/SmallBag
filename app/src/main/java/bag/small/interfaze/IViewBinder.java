package bag.small.interfaze;

/**
 * Created by Administrator on 2017/8/1.
 */

public interface IViewBinder {
    void add(int type, int position);

    void delete(int position);

    void click(int position);
}
