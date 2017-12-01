package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.MemorandumItemBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/24.
 */

public interface IMemorandum {

    @FormUrlEncoded
    @POST("beiwanglus/classlist")
    Observable<BaseBean<List<MemorandumItemBean>>> getSubjectMemorandum(@Field("role_id") String roleId,
                                                                 @Field("login_id") String loginId,
                                                                 @Field("school_id") String schoolId);
    @FormUrlEncoded
    @POST("beiwanglus/beiwanglutimelist")
    Observable<BaseBean<List<MemorandumItemBean>>> getTimeMemorandum(@Field("role_id") String roleId,
                                                                 @Field("login_id") String loginId,
                                                                 @Field("school_id") String schoolId);
}
