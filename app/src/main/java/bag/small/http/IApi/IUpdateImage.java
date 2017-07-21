package bag.small.http.IApi;


import java.util.List;

import bag.small.entity.BaseBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/6/14.
 */

public interface IUpdateImage {
    @Multipart
    @POST("image/update")
    Observable<BaseBean<String>> updateImage(@Part MultipartBody.Part file);
    @Multipart
    @POST("image/updates")
    Observable<BaseBean<List<String>>> updateImages(@Part MultipartBody.Part[] file);

}
