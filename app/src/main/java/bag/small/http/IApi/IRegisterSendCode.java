package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/9.
 */

public interface IRegisterSendCode {
    @POST("commons/sendverifycode")
    Observable<BaseBean<String>> sendCodeRequest(@Query("phone") String phone);
}
