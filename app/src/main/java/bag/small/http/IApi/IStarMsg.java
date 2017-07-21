package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/6/22.
 */

public interface IStarMsg {

    @POST("star/like_or_cancel")
    Observable<BaseBean<Integer>> likeOrUnlike(@Query("userId") int userId,
                                               @Query("msgId") int msgId);
}
