package bag.small.http.IApi;

import java.util.HashMap;
import java.util.List;

import bag.small.entity.AddAccount;
import bag.small.entity.BaseBean;
import bag.small.entity.ForgetPassword;
import bag.small.entity.LoginResult;
import bag.small.entity.NewRegisterStudentOrTeacherBean;
import bag.small.entity.NewRegisterTeacherBean;
import bag.small.entity.RegisterBean;
import bag.small.entity.RegisterInfoBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface IRegisterReq {

    @POST("commons/registerinfo")
    Observable<BaseBean<RegisterInfoBean>> getRegisterInfo();

    @FormUrlEncoded
    @POST("commons/register")
    Observable<BaseBean<RegisterBean>> goRegister(@Field("phone") String phone,
                                                  @Field("pwd") String password,
                                                  @Field("verify") String code);

    @FormUrlEncoded
    @POST("commons/update")
    Observable<BaseBean<ForgetPassword>> changePassword(@Field("phone") String phone,
                                                        @Field("pwd") String password,
                                                        @Field("verify") String code);

    @Multipart
    @POST("commons/registerteacher")
    Observable<BaseBean<String>> goRegisterAsTeacher(@PartMap HashMap<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part logo);

    @Multipart
    @POST("commons/reviseroleinfo")
    Observable<BaseBean<NewRegisterStudentOrTeacherBean>> changeRegisterAsTeacherOrStudent(@PartMap HashMap<String, RequestBody> partMap);

    @Multipart
    @POST("commons/registerstudent")
    Observable<BaseBean<String>> goRegisterAsStudent(@PartMap HashMap<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part logo);

    @FormUrlEncoded
    @POST("commons/getteacherinfo")
    Observable<BaseBean<List<NewRegisterTeacherBean>>> getNewTeacherRegisterInfo(@Field("login_id") String loginId,
                                                                                 @Field("phone") String phone);

    @FormUrlEncoded
    @POST("commons/getroleinfo")
    Observable<BaseBean<NewRegisterTeacherBean>> getNewTeacherRegisterInfo(@Field("login_id") String loginId,
                                                                           @Field("role_id") String role,
                                                                           @Field("school_id") String school);

    @FormUrlEncoded
    @POST("commons/getroleinfo")
    Observable<BaseBean<NewRegisterStudentOrTeacherBean>> getStudentInfo(@Field("login_id") String loginId,
                                                                         @Field("role_id") String role,
                                                                         @Field("school_id") String school);

    @FormUrlEncoded
    @POST("commons/getroleinfo")
    Observable<BaseBean<NewRegisterTeacherBean>> getTeacherInfo(@Field("login_id") String loginId,
                                                                @Field("role_id") String role,
                                                                @Field("school_id") String school);

    @FormUrlEncoded
    @POST("commons/addrole")
    Observable<BaseBean<AddAccount>> addTeacherInfo(@Field("login_id") String loginId,
                                                    @Field("type") String type);

    @FormUrlEncoded
    @POST("commons/addrole")
    Observable<BaseBean<AddAccount>> addStudentInfo(@Field("login_id") String loginId,
                                                                @Field("type") String type);

    @FormUrlEncoded
    @POST("commons/getstudentinfo")
    Observable<BaseBean<List<NewRegisterStudentOrTeacherBean>>> getNewStudentRegisterInfo(@Field("login_id") String loginId,
                                                                                          @Field("phone") String phone);

    @FormUrlEncoded
    @POST("commons/submitregister")
    Observable<BaseBean<LoginResult>> getNewRegisterInfo(@Field("login_id") String loginId,
                                                         @Field("phone") String phone);
}
