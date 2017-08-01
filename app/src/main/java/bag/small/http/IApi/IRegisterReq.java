package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import bag.small.entity.LoginBean;
import bag.small.entity.RegisterInfoBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {



    @POST("commons/registerinfo")
    Observable<BaseBean<RegisterInfoBean>> getRegisterInfo();

    @POST("commons/register")
    Observable<BaseBean<LoginBean>> goRegister(@Query("phone") String phone,
                                               @Query("pwd") String password,
                                               @Query("verify") String code);
    @POST("commons/registerteacher")
    Observable<BaseBean<String>> goRegisterAsTeacher(@Query("login_id") String loginId,
                                                     @Query("sex") int sex,//0man
                                                     @Query("birth") String birth,//2016-03-03
                                                     @Query("name") String name,
                                                     @Query("school_id") String schoolId,
                                                     @Query("is_master") String isMaster,//
                                                     @Query("jieci") String jieci,
                                                     @Query("nianji") String nianji,
                                                     @Query("banji") String banji,
                                                     @Part MultipartBody.Part logo,
                                            @Query("phone") String phone);


}
