package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.ClassMemorandumBean;
import bag.small.entity.TeacherMemorandumBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface ITeachClasses {
    @FormUrlEncoded
    @POST("beiwanglus/teacherbanji")
    Observable<BaseBean<List<TeacherMemorandumBean>>> getTeachClasses(@Field("role_id") String roleId,
                                                                      @Field("login_id") String loginId,
                                                                      @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("beiwanglus/banjilist")
    Observable<BaseBean<List<ClassMemorandumBean>>> getClassMemorandum(@Field("role_id") String roleId,
                                                                       @Field("login_id") String loginId,
                                                                       @Field("school_id") String schoolId,
                                                                       @Field("banji_id") String banjiId,
                                                                       @Field("page") int page);
}
