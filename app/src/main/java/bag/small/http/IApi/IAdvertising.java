package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/3.
 */

public interface IAdvertising {
    @FormUrlEncoded
    @POST("guanggaos/list")
    Observable<BaseBean<List<AdvertisingBean>>> getAdvertisings(@Field("role_id") String roleId,
                                                                @Field("login_id") String loginId,
                                                                @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("guanggaos/detail")
    Observable<BaseBean<AdvertisingDetailBean>> getAdvertisingsDetail(@Field("role_id") String roleId,
                                                                      @Field("login_id") String loginId,
                                                                      @Field("school_id") String schoolId,
                                                                      @Field("ads_id") int adsId,
                                                                      @Field("come_from") String comFrom);
}
