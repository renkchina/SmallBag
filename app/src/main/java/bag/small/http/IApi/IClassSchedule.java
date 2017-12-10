package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import bag.small.entity.ClassScheduleBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface IClassSchedule {
    @FormUrlEncoded
    @POST("beiwanglus/kechens")
    Observable<BaseBean<ClassScheduleBean>> getClassSchedule(@Field("role_id") String roleId,
                                                             @Field("login_id") String loginId,
                                                             @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("beiwanglus/teacherkechens")
    Observable<BaseBean<ClassScheduleBean>> getClassScheduleTeacher(@Field("role_id") String roleId,
                                                                    @Field("login_id") String loginId,
                                                                    @Field("school_id") String schoolId,
                                                                    @Field("banji_id") String banjiId);
}
