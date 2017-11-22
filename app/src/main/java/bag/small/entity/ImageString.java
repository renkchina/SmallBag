package bag.small.entity;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ImageString  {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageString{" +
                "url='" + url + '\'' +
                '}';
    }
}
