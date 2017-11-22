package bag.small.entity;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AdvertisingBean {

    /**
     * img : http://api.vshangsoft.com/images/ads_1.jpg
     * type : link
     * url : http://www.baidu.com
     * ads_id : 1
     * came_from : school
     */

    private String img;
    private String type;
    private String url;
    private int ads_id;
    private String came_from;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAds_id() {
        return ads_id;
    }

    public void setAds_id(int ads_id) {
        this.ads_id = ads_id;
    }

    public String getCame_from() {
        return came_from;
    }

    public void setCame_from(String came_from) {
        this.came_from = came_from;
    }
}
