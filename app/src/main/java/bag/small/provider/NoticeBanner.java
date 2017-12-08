package bag.small.provider;

import java.util.List;

import bag.small.entity.AdvertisingBean;

/**
 * Created by Administrator on 2017/8/12.
 */
public class NoticeBanner {

    private String headImage;

    private List<String> bannerImages;
    private int bannerImage;

    List<AdvertisingBean> advertisingBeans;

    public List<AdvertisingBean> getAdvertisingBeans() {
        return advertisingBeans;
    }

    public void setAdvertisingBeans(List<AdvertisingBean> advertisingBeans) {
        this.advertisingBeans = advertisingBeans;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public List<String> getBannerImages() {
        return bannerImages;
    }

    public void setBannerImages(List<String> bannerImages) {
        this.bannerImages = bannerImages;
    }

    @Override
    public String toString() {
        return "NoticeBanner{" +
                "headImage='" + headImage + '\'' +
                ", bannerImages=" + bannerImages +
                '}';
    }

    public void setBannerImages(int bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getBannerImage() {
        return bannerImage;
    }
}