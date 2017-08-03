package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/5/9.
 */

public interface IRegisterSendCode {
    @FormUrlEncoded
    @POST("commons/sendverifycode")
    Observable<BaseBean<String>> sendCodeRequest(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("commons/sendcode")
    Observable<BaseBean<String>> sendCheckCodeRequest(@Field("phone") String phone);


}
