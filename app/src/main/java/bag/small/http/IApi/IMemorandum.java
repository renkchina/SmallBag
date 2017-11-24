package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import bag.small.entity.MemorandumBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/24.
 */

public interface IMemorandum {

    @FormUrlEncoded
    @POST("commons/getnotifycount")
    Observable<BaseBean<MemorandumBean>> getMemorandum(@Field("role_id") String roleId,
                                                       @Field("login_id") String loginId,
                                                       @Field("school_id") String schoolId);
}
