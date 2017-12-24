package bag.small.http.IApi;


import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.IMChoiceTeacher;
import bag.small.entity.MassOrgBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/19.
 */

public interface IMChats {
    @FormUrlEncoded
    @POST("ims/getchartroom")
    Observable<BaseBean<List<MassOrgBean>>> getUserGroup(@Field("role_id") String roleId,
                                                         @Field("login_id") String loginId,
                                                         @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("ims/getmembers")
    Observable<BaseBean<String>> getAllUserDetail();

    @FormUrlEncoded
    @POST("ims/getbanjiteachers")
    Observable<BaseBean<List<IMChoiceTeacher>>> getClassTeachers(@Field("role_id") String roleId,
                                                                 @Field("login_id") String loginId,
                                                                 @Field("school_id") String schoolId,
                                                                 @Field("banji_id") String banjiId);

    @FormUrlEncoded
    @POST("ims/getteachersmsg")
    Observable<BaseBean<List<String>>> getTeachersMsgs(@Field("role_id") String roleId,
                                                       @Field("login_id") String loginId,
                                                       @Field("school_id") String schoolId,
                                                       @Field("banji_id") String banjiId,
                                                       @Field("teacher_id") String teachersId,
                                                       @Field("page") int page);

}
