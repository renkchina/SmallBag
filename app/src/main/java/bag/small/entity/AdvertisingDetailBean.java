package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AdvertisingDetailBean {

    /**
     * title : 运动会|校园运动会报名开始啦
     * content : 在这个五月，通过选拔的运动员将会接受训练，训练时间为周一到周五早上6:30到7:20
     * images : ["http://api.vshangsoft.com/images/1.jpeg","http://api.vshangsoft.com/images/4.jpeg"]
     */

    private String title;
    private String content;
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
