package bag.small.http;

import android.util.ArrayMap;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bag.small.BuildConfig;
import bag.small.utils.LogUtil;
import bag.small.utils.MD5Util;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/14.
 */

public class HttpUtil {

    private ArrayMap<Class, Object> apis = new ArrayMap<>();

    private Retrofit retrofit;

    private static volatile HttpUtil INSTANCE;

    public static HttpUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpUtil();
                }
            }
        }
        return INSTANCE;
    }

    private HttpUtil() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(90, TimeUnit.SECONDS);
        builder.readTimeout(90, TimeUnit.SECONDS);
        //
        //打印retrofit日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LogUtil::loge);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            String method = original.method();
            Request request = null;
            String time = getSystemTimes();
            if ("GET".equals(method)) {
                HttpUrl modifiedUrl = original.url().newBuilder()
                        .addEncodedQueryParameter("timespan", time)
                        .addEncodedQueryParameter("singure", MD5Util.string2MD5(time))
                        .build();
                request = original.newBuilder().url(modifiedUrl).build();
            } else if ("POST".equals(method)) {
                Request.Builder requestBuilder = original.newBuilder();
                if (original.body() instanceof FormBody) {
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oidFormBody = (FormBody) original.body();
                    for (int i = 0; i < oidFormBody.size(); i++) {
                        newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                    }
                    newFormBody.add("timespan", time);
                    newFormBody.add("singure", MD5Util.string2MD5(time));
                    requestBuilder.method(original.method(), newFormBody.build());
                }
                request = requestBuilder.build();
            }
            return chain.proceed(request);
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BaseApi)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private String getSystemTimes() {
        return System.currentTimeMillis() + "";
    }

    public <T> T createApi(Class<T> service) {
        if (!apis.containsKey(service)) {
            T instance = retrofit.create(service);
            apis.put(service, instance);
        }
        return (T) apis.get(service);
    }
}
