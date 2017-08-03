package bag.small.http.IApi;

import java.util.Map;

import bag.small.entity.BaseBean;
import bag.small.entity.LoginBean;
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
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {

    @POST("commons/registerinfo")
    Observable<BaseBean<RegisterInfoBean>> getRegisterInfo();

    @FormUrlEncoded
    @POST("commons/register")
    Observable<BaseBean<LoginBean>> goRegister(@Field("phone") String phone,
                                               @Field("pwd") String password,
                                               @Field("verify") String code);

    @Multipart
    @POST("commons/registerteacher")
    Observable<BaseBean<String>> goRegisterAsTeacher(
//                                                     @Part("login_id") String loginId,
//                                                     @Query("sex") int sex,//0man
//                                                     @Query("birth") String birth,//2016-03-03、
//                                                     @Part("name") String name,
//                                                     @Part("phone") String phone,
//                                                     @Part("school_id") String schoolId,
//                                                     @Part("is_master") int isMaster,//
//                                                     @Part("jieci") String jieci,
//                                                     @Part("nianji") int nianji,
//                                                     @Part("banji") String banji,
                                                     @PartMap Map<String, String> partMap,
//                                                     @Part("jiaoxue") String jiaoxue,
                                                     @Part MultipartBody.Part logo);

    @FormUrlEncoded
    @POST("commons/registerstudent")
    Observable<BaseBean<String>> goRegisterAsStudent(@Field("login_id") String loginId,
//                                                     @Query("sex") int sex,//0man
//                                                     @Query("birth") String birth,//2016-03-03
                                                     @Field("name") String name,
                                                     @Field("phone") String phone,
                                                     @Field("school_id") String schoolId,
                                                     @Field("jieci") String jieci,
                                                     @Field("nianji") int nianji,
                                                     @Field("banji") String banji,
                                                     @Part MultipartBody.Part logo,
                                                     @Field("verify") String verify,
                                                     @Field("jianhuren") String jianhuren,
                                                     @Field("jianhuren_name") String jianhuren_name);


}
