package bag.small.http.IApi;

import android.text.TextUtils;
import bag.small.entity.BaseBean;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HttpResultFilter<T> implements Predicate<BaseBean<T>> {

    @Override
    public boolean test(@NonNull BaseBean<T> bean) throws Exception {
        if (!TextUtils.isEmpty(bean.getMsg())) {
        }
        return bean.isSuccess();
    }
}
