package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/10.
 */

public interface IInterestClass {
    @FormUrlEncoded
    @POST("users/getinterest")
    Observable<BaseBean<String>> getInterests(@Field("role_id") String roleId,
                                                       @Field("login_id") String loginId,
                                                       @Field("school_id") String schoolId);
}
