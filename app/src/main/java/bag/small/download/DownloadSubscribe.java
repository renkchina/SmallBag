package bag.small.download;

import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载订阅者
 */
public class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {

    private DownloadInfo downloadInfo;
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient mClient;//OKHttpClient;

    public DownloadSubscribe(DownloadInfo downloadInfo,
                             HashMap<String, Call> downCalls,
                             OkHttpClient mClient) {
        this.downloadInfo = downloadInfo;
        this.downCalls = downCalls;
        this.mClient = mClient;
    }

    @Override
    public void subscribe(ObservableEmitter<DownloadInfo> e) {
        String url = downloadInfo.getUrl();
        long downloadLength = downloadInfo.getProgress();//已经下载好的长度
        long contentLength = downloadInfo.getTotal();//文件的总长度

        Request request = new Request.Builder()
                //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        downCalls.put(url, call);
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        try {
            Response response = call.execute();
            File file = new File(DownloadManager.getInstance().videoPath, downloadInfo.getFileName());
            // 写文件到本地
            if(response.body()==null){
                return;
            }
            is = response.body().byteStream();
            fileOutputStream = new FileOutputStream(file, true);
            byte[] buffer = new byte[4000];//缓冲数组4kB
            int len;
            while ((len = is.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                downloadLength += len;
                downloadInfo.setProgress(downloadLength);
                e.onNext(downloadInfo);
                sendBroadcastProgress((int) (downloadLength * 100 / contentLength));
            }
            fileOutputStream.flush();
            downCalls.remove(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeAll(is, fileOutputStream);
        }
        //初始进度信息
        e.onNext(downloadInfo);
        e.onComplete();//完成
    }

    //发送正在下载
    private void sendBroadcastProgress(int progress) {
//        Intent intent = new Intent("renk").putExtra("progress", progress);
//        LocalBroadcastManager.getInstance(MyApplication.getInstance()).sendBroadcast(intent);
    }
    private void closeAll(InputStream is, FileOutputStream fileOutputStream) {
        try {
            if(is!=null){
                is.close();
            }
            if(fileOutputStream!=null){
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
