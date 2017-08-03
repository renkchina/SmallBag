package bag.small.utils;

import java.io.IOException;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class AddInterceptord implements Interceptor {
    //这里可以添加公共参数
        @Override
        public Response intercept(Chain chain) throws IOException {
            String time = StringUtil.getSystemTimes();
            Request original = chain.request();

             String method = original.method();
            Request.Builder requestBuilder = original.newBuilder();
            if("GET".equals(method)){
                HttpUrl modifiedUrl = original.url().newBuilder().
                        addEncodedQueryParameter("timespan", time)
                        .addEncodedQueryParameter("singure", MD5Util.md5(time))
                        .build();
                requestBuilder = original.newBuilder().url(modifiedUrl);
            }else if("POST".equals(method)){
                //post参数
                //请求体定制：统一添加token参数
                if(original.body() instanceof FormBody){
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oidFormBody = (FormBody) original.body();
                    for (int i = 0;i<oidFormBody.size();i++){
                        newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                    }
                    newFormBody.add("timespan", time);
                    newFormBody.add("singure", MD5Util.md5(time));
                    requestBuilder.method(original.method(),newFormBody.build());
                }else if(original.body() instanceof MultipartBody){
                    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    multipartBuilder.addFormDataPart("timespan", time);
                    multipartBuilder.addFormDataPart("singure", MD5Util.md5(time));
                    List<MultipartBody.Part> oldParts = ((MultipartBody)original.body()).parts();
                    if (oldParts != null && oldParts.size() > 0) {
                        for (MultipartBody.Part part : oldParts) {
                            multipartBuilder.addPart(part);
                        }
                    }
                    requestBuilder.post(multipartBuilder.build());
                }else{
                    HttpUrl modifiedUrl = original.url().newBuilder().
                            addEncodedQueryParameter("timespan", time)
                            .addEncodedQueryParameter("singure", MD5Util.md5(time))
                            .build();
                    requestBuilder = original.newBuilder().url(modifiedUrl);
                }
            }

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }

}