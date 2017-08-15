package bag.small.http.IApi;

import java.util.HashMap;

import bag.small.entity.BaseBean;
import bag.small.entity.RegisterBean;
import bag.small.entity.RegisterInfoBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {

    @POST("commons/registerinfo")
    Observable<BaseBean<RegisterInfoBean>> getRegisterInfo();

    @FormUrlEncoded
    @POST("commons/register")
    Observable<BaseBean<RegisterBean>> goRegister(@Field("phone") String phone,
                                                  @Field("pwd") String password,
                                                  @Field("verify") String code);
    @FormUrlEncoded
    @POST("commons/update")
    Observable<BaseBean<String>> changePassword(@Field("phone") String phone,
                                                  @Field("pwd") String password,
                                                  @Field("verify") String code);

    @Multipart
    @POST("commons/registerteacher")
    Observable<BaseBean<String>> goRegisterAsTeacher(@PartMap HashMap<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part logo);

    @Multipart
    @POST("commons/registerstudent")
    Observable<BaseBean<String>> goRegisterAsStudent(@PartMap HashMap<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part logo);


}
