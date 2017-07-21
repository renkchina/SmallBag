package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {

    @POST("account/regist")
    Observable<BaseBean<String>> goRegister(@Query("phone") String phone,
                                            @Query("password") String password,
                                            @Query("verCode") String code,
                                            @Query("role") int type);
}
