package bag.small.http.IApi;

import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.EducationNoticeBean;
import bag.small.entity.LoginResult;
import bag.small.entity.NoticeDetailBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/8.
 */

public interface INotification {

    @FormUrlEncoded
    @POST("commons/getnotifylist")
    Observable<BaseBean<List<EducationNoticeBean>>> getNotice(@Field("role_id") String roleId,
                                                              @Field("login_id") String loginId,
                                                              @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST("commons/getnotifybyid")
    Observable<BaseBean<NoticeDetailBean>> getNoticeDetail(@Field("role_id") String roleId,
                                                           @Field("login_id") String loginId,
                                                           @Field("school_id") String schoolId,
                                                           @Field("notify_id") int notifyId);
}
