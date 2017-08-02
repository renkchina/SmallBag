package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import bag.small.entity.LoginBean;
import bag.small.entity.RegisterInfoBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {


    @FormUrlEncoded
    @POST("commons/registerinfo")
    Observable<BaseBean<RegisterInfoBean>> getRegisterInfo();

    @FormUrlEncoded
    @POST("commons/register")
    Observable<BaseBean<LoginBean>> goRegister(@Query("phone") String phone,
                                               @Query("pwd") String password,
                                               @Query("verify") String code);

    @FormUrlEncoded
    @POST("commons/registerteacher")
    Observable<BaseBean<String>> goRegisterAsTeacher(@Query("login_id") int loginId,
//                                                     @Query("sex") int sex,//0man
//                                                     @Query("birth") String birth,//2016-03-03、
                                                     @Query("name") String name,
                                                     @Query("phone") String phone,
                                                     @Query("school_id") String schoolId,
                                                     @Query("is_master") int isMaster,//
                                                     @Query("jieci") String jieci,
                                                     @Query("nianji") int nianji,
                                                     @Query("banji") String banji,
                                                     @Query("logo") String logo,
                                                     @Query("jiaoxue") String[][] jiaoxue);

    @POST("commons/registerstudent")
    Observable<BaseBean<String>> goRegisterAsStudent(@Query("login_id") int loginId,
//                                                     @Query("sex") int sex,//0man
//                                                     @Query("birth") String birth,//2016-03-03
                                                     @Query("name") String name,
                                                     @Query("phone") String phone,
                                                     @Query("school_id") String schoolId,
                                                     @Query("jieci") String jieci,
                                                     @Query("nianji") int nianji,
                                                     @Query("banji") String banji,
                                                     @Query("logo") String logo,
                                                     @Query("verify") String verify,
                                                     @Query("jianhuren") String jianhuren,
                                                     @Query("jianhuren_name") String jianhuren_name
    );


}
