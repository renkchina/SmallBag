package bag.small.http.IApi;


import java.util.HashMap;
import java.util.List;

import bag.small.entity.BaseBean;
import bag.small.entity.PublishMsgBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2017/6/14.
 */

public interface IUpdateImage {
    @Multipart
    @POST("users/submitinfo")
    Observable<BaseBean<String>> updateImage(@Field("role_id") String roleId,
                                             @Field("login_id") String loginId,
                                             @Field("school_id") String schoolId,
                                             @Field("page") int page,
                                             @Field("content") String content,
                                             @Part MultipartBody.Part file1,
                                             @Part MultipartBody.Part file2,
                                             @Part MultipartBody.Part file3,
                                             @Part MultipartBody.Part file4,
                                             @Part MultipartBody.Part file5,
                                             @Part MultipartBody.Part file6,
                                             @Part MultipartBody.Part file7,
                                             @Part MultipartBody.Part file8,
                                             @Part MultipartBody.Part file9);
    @Multipart
    @POST("users/submitinfo")
    Observable<BaseBean<List<PublishMsgBean>>> updateImage(@PartMap HashMap<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part[] file);


}
