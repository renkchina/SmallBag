package bag.small.http.IApi;

import java.util.ArrayList;
import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.MomentsBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/8/9.
 */

public interface IMoments {

    @FormUrlEncoded
    @POST("users/getfriendmsg")
    Observable<BaseBean<List<MomentsBean>>> getMoments(@Field("role_id") String roleId,
                                                       @Field("login_id") String loginId,
                                                       @Field("school_id") String schoolId,
                                                       @Field("page") int page);

    @FormUrlEncoded
    @POST("users/friendrepay")
    Observable<BaseBean<List<MomentsBean.RepayBean>>> getEvaluateMsg(@Field("role_id") String roleId,
                                                                     @Field("login_id") String loginId,
                                                                     @Field("school_id") String schoolId,
                                                                     @Field("page") int page,
                                                                     @Field("content") String content,
                                                                     @Field("repay_id") String repay_id,
                                                                     @Field("parent_id") String msgId);

    @FormUrlEncoded
    @POST("users/deletemsg")
    Observable<BaseBean<List<MomentsBean>>> deleteEvaluateMsg(@Field("role_id") String roleId,
                                                              @Field("login_id") String loginId,
                                                              @Field("school_id") String schoolId,
                                                              @Field("page") int page,
                                                              @Field("id") String msgId);

    @FormUrlEncoded
    @POST("users/{method}")
    Observable<BaseBean<List<String>>> likeOrUnLikeEvaluateMsg(@Path("method") String method,
                                                               @Field("role_id") String roleId,
                                                               @Field("login_id") String loginId,
                                                               @Field("school_id") String schoolId,
                                                               @Field("parent_id") String msgId);

    @FormUrlEncoded
    @POST("users/deleterepaymsg")
    Observable<BaseBean<List<MomentsBean.RepayBean>>> deleteEvaluate(@Field("review_id") String reviewId,
                                                                     @Field("role_id") String roleId,
                                                                     @Field("login_id") String loginId,
                                                                     @Field("school_id") String schoolId,
                                                                     @Field("parent_id") String msgId);
}
