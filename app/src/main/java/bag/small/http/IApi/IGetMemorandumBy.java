package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.ClassMemorandumBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface IGetMemorandumBy {

    @FormUrlEncoded
    @POST("beiwanglus/studentclassinfo")
    Observable<BaseBean<List<ClassMemorandumBean>>> getMemorandumBySubject(@Field("role_id") String roleId,
                                                                           @Field("login_id") String loginId,
                                                                           @Field("school_id") String schoolId,
                                                                           @Field("kemu_id") String kemu_id,
                                                                           @Field("page") int page);

    @FormUrlEncoded
    @POST("beiwanglus/beiwanglutimeinfo")
    Observable<BaseBean<List<ClassMemorandumBean>>> getMemorandumByTime(@Field("role_id") String roleId,
                                                                        @Field("login_id") String loginId,
                                                                        @Field("school_id") String schoolId,
                                                                        @Field("pubdate") String pubdate,
                                                                        @Field("page") int page);

    @FormUrlEncoded
    @POST("beiwanglus/readbeiwanglu")
    Observable<BaseBean<String>> setMemorandumRead(@Field("role_id") String roleId,
                                                                      @Field("login_id") String loginId,
                                                                      @Field("school_id") String schoolId,
                                                                      @Field("bwid") String bwid);

}
