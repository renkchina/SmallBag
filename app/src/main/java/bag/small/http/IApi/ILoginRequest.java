package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import bag.small.entity.LoginResult;
import bag.small.utils.StringUtil;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface ILoginRequest {

    @FormUrlEncoded
    @POST("commons/login")
    Observable<BaseBean<LoginResult>> appLogin(@Field("phone") String name,
                                               @Field("pwd") String password);

    @FormUrlEncoded
    @POST("commons/update")
    Observable<BaseBean<LoginResult>> forgetPwd(@Field("phone") String name,
                                                @Field("verify") String verify,
                                                @Field("pwd") String password);

    @FormUrlEncoded
    @POST("commons/getroles")
    Observable<BaseBean<LoginResult>> getAllRole(@Field("login_id") String loginId);

    @FormUrlEncoded
    @POST("commons/updeceive")
    Observable<BaseBean<String>> updateToken(@Field("login_id") String loginId,
                                             @Field("name") String name,
                                             @Field("device_token") String token,
                                             @Field("key") String key,
                                             @Field("device_type") String type);

}
