package bag.small.download;

/**
 * 下载信息实体类
 */
public class DownloadInfo {

    public static final long TOTAL_ERROR = -1;//获取进度失败
    private String url;
    private long total;
    private long progress;
    private String fileName;
    private String localPath;

    public static long getTotalError() {
        return TOTAL_ERROR;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public DownloadInfo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
