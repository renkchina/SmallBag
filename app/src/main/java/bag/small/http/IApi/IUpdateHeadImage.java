package bag.small.http.IApi;


import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/6/15.
 */

public interface IUpdateHeadImage {
    @POST("user/update_image")
    Observable<BaseBean<BaseBean>> changeImage(@Query("user_id") int id, @Query("path") String path, @Query("type") int type);
}
