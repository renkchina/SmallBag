package bag.small.provider;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public class NoticeBanner {

    private String headImage;

    private List<Object> bannerImages;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public List<Object> getBannerImages() {
        return bannerImages;
    }

    public void setBannerImages(List<Object> bannerImages) {
        this.bannerImages = bannerImages;
    }

    @Override
    public String toString() {
        return "NoticeBanner{" +
                "headImage='" + headImage + '\'' +
                ", bannerImages=" + bannerImages +
                '}';
    }
}