package bag.small.utils;

import java.util.Collection;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ListUtil {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() < 1;
    }
    public static boolean unEmpty(Collection collection) {
        return !(collection == null || collection.size() < 1);
    }
}
