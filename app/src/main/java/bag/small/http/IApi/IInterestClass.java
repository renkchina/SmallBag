package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import bag.small.entity.ChoiceClassLists;
import bag.small.entity.TeacherClass;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/10.
 */

public interface IInterestClass {
    @FormUrlEncoded
    @POST("users/getstudentinterest")
    Observable<BaseBean<ChoiceClassLists>> getInterestsForStudent(@Field("role_id") String roleId,
                                                                  @Field("login_id") String loginId,
                                                                  @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("users/getteacherinterest")
    Observable<BaseBean<TeacherClass>> getInterestsForTeacher(@Field("role_id") String roleId,
                                                              @Field("login_id") String loginId,
                                                              @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("users/submitchoose")
    Observable<BaseBean<TeacherClass>> getInterestsSubmit(@Field("role_id") String roleId,
                                                          @Field("login_id") String loginId,
                                                          @Field("school_id") String schoolId,
                                                          @Field("first") String first,
                                                          @Field("secend") String secend,
                                                          @Field("third") String third);
}
