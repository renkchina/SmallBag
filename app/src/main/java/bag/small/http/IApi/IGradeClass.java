package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.GradeClass;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/29.
 *
 */

public interface IGradeClass {

    @FormUrlEncoded
    @POST("beiwanglus/getbanjiinfo")
    Observable<BaseBean<List<GradeClass>>> getPublishGradeClass(@Field("role_id") String roleId,
                                                                @Field("login_id") String loginId,
                                                                @Field("school_id") String schoolId);
}
