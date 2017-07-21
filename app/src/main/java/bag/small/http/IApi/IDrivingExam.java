package bag.small.http.IApi;


import java.util.List;

import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/22.
 */

public interface IDrivingExam {
    @POST("school/find_all_school")
    Observable<BaseBean<List<BaseBean>>> getAllSchool(@Query("userId") int userId,
                                                      @Query("lon") double lon,
                                                      @Query("lat") double lat,
                                                      @Query("pageStart") int pageStart,
                                                      @Query("pageEnd") int pageEnd);
}
