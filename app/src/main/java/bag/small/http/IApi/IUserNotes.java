package bag.small.http.IApi;


import java.util.List;
import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/30.
 */

public interface IUserNotes {
    @POST("message/get_all")
    Observable<BaseBean<List<BaseBean>>> getAllNotes(@Query("userId") int user_id,
                                                     @Query("pageStart") int start,
                                                     @Query("pageEnd") int end);
}
