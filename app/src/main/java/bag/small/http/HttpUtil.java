package bag.small.http;

import android.util.ArrayMap;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import bag.small.BuildConfig;
import bag.small.utils.AddInterceptord;
import bag.small.utils.LogUtil;
import okhttp3.OkHttpClient;
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
        builder.connectTimeout(99, TimeUnit.SECONDS);
        builder.readTimeout(99, TimeUnit.SECONDS);
        builder.writeTimeout(99, TimeUnit.SECONDS);
        //拦截统一参数
        builder.addInterceptor(new AddInterceptord());
        //打印retrofit日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LogUtil::loge);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BaseApi)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public <T> T createApi(Class<T> service) {
        if (!apis.containsKey(service)) {
            T instance = retrofit.create(service);
            apis.put(service, instance);
        }
        return (T) apis.get(service);
    }
}
