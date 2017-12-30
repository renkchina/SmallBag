package bag.small.download;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载管理类
 */
public class DownloadManager {

    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient mClient;//OKHttpClient;

    // 文件下载的保存路径
    public String videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

    public static DownloadManager getInstance() {
        for (; ; ) {
            DownloadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownloadManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownloadManager() {
        downCalls = new HashMap<>();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(80, TimeUnit.SECONDS);
        builder.readTimeout(100, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        mClient = new OkHttpClient.Builder().build();
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     * @param downLoadObserver 用来回调的接口
     */
    public void download(String url, DownLoadObserver downLoadObserver) {
        Observable.just(url)
                .filter(s -> !downCalls.containsKey(s))//call的map已经有了,就证明正在下载,则这次不下载
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)//检测本地文件夹,生成新的文件名
                .flatMap(downloadInfo -> Observable
                        .create(new DownloadSubscribe(downloadInfo, downCalls, mClient))
                        .retryWhen(new RetryWithDelay(1, 5)))//下载
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
                .subscribe(downLoadObserver);//添加观察者
    }
    public void download(String url) {
        Observable.just(url)
                .filter(s -> !downCalls.containsKey(s))//call的map已经有了,就证明正在下载,则这次不下载
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)//检测本地文件夹,生成新的文件名
                .flatMap(downloadInfo -> Observable
                        .create(new DownloadSubscribe(downloadInfo, downCalls, mClient))
                        .retryWhen(new RetryWithDelay(1, 5)))//下载
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());//添加观察者
    }

    /**
     * 取消
     *
     * @param url
     */
    public void cancel(String url) {
        Call call = downCalls.get(url);

        if (call != null) {
            call.cancel();
        }
        downCalls.remove(url);
    }


    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private DownloadInfo createDownInfo(String url) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
        long contentLength = getContentLength(url);//获得文件大小
        downloadInfo.setTotal(contentLength);

        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        return downloadInfo;
    }


    private DownloadInfo getRealFileName(DownloadInfo downloadInfo) {
        String fileName = downloadInfo.getFileName();
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File file = new File(videoPath, fileName);

        if (file.exists()) {
            file = new File(videoPath, fileName + "1");
            if (file.exists()) {
                downloadLength = file.length();
                if (contentLength <= downloadLength) {
                    downloadInfo.setLocalPath(file.getAbsolutePath());
                    return downloadInfo;
                }
            }
        }
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        downloadInfo.setLocalPath(file.getAbsolutePath());
        return downloadInfo;
    }


    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();

            if (response != null && response.isSuccessful()) {
                if (response.body() == null) {
                    return 0;
                }
                assert response.body() != null;
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DownloadInfo.TOTAL_ERROR;
    }

}
