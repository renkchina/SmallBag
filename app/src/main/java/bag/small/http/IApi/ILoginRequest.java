package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface ILoginRequest {

    //    @Multipart
//    @POST("facepp/v3/compare")
//    Observable<String> compare(@Part("api_key") RequestBody apiKey,
//                               @Part("api_secret") RequestBody apiSecret,
//                               @Part MultipartBody.Part... files);
//    @POST("account/login")
//    Observable<BaseBean<LoginBean>> appLogin(@Query("phone") String name,
//                                             @Query("password") String password);
    @POST("user/login")
    Observable<BaseBean<BaseBean>> appLogin(@Query("phone") String name,
                                            @Query("password") String password);


}
