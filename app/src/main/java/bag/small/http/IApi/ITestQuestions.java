package bag.small.http.IApi;

import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/5/16.
 */

public interface ITestQuestions {
    @GET("query?appkey=2485934808878226&type=C1&subject=1&pagesize=99999&pagenum=1&sort=rand")
    Observable<BaseBean> getAllQuestions();
}
