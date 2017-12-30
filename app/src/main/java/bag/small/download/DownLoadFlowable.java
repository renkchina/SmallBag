package bag.small.download;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadFlowable implements FlowableOnSubscribe<DownloadInfo> {
    private DownloadInfo downloadInfo;
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient mClient;//OKHttpClient;

    public DownLoadFlowable(DownloadInfo downloadInfo,
                            HashMap<String, Call> downCalls,
                            OkHttpClient mClient) {
        this.downloadInfo = downloadInfo;
        this.downCalls = downCalls;
        this.mClient = mClient;
    }

    @Override
    public void subscribe(FlowableEmitter e) throws Exception {
        String url = downloadInfo.getUrl();
        long downloadLength = downloadInfo.getProgress();//已经下载好的长度
        long contentLength = downloadInfo.getTotal();//文件的总长度

        //初始进度信息
        e.onNext(downloadInfo);

        Request request = new Request.Builder()
                //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        downCalls.put(url, call);//把这个添加到call里,方便取消
        Response response = call.execute();

        File file = new File(DownloadManager.getInstance().videoPath, downloadInfo.getFileName());

        InputStream is = null;
        FileOutputStream fileOutputStream = null;

        // 写文件到本地
        try {
            is = response.body().byteStream();
            fileOutputStream = new FileOutputStream(file, true);
            byte[] buffer = new byte[2048*2];//缓冲数组

            int len;
            while ((len = is.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                downloadLength += len;

                downloadInfo.setProgress(downloadLength);
                e.onNext(downloadInfo);
            }

            fileOutputStream.flush();
            downCalls.remove(url);

        } finally {
            closeAll(is, fileOutputStream);
        }

        e.onComplete();//完成
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
