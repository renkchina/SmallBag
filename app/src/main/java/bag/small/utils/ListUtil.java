package bag.small.utils;

import java.util.Collection;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ListUtil {
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.size() < 1) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean unEmpty(Collection collection) {
        if (collection == null || collection.size() < 1) {
            return false;
        } else {
            return true;
        }
    }
}
