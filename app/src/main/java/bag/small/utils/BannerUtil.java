package bag.small.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.ImageString;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.rx.RxUtil;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

/**
 * Created by Administrator on 2017/12/1.
 */

public class BannerUtil {

    private Context context;
    IAdvertising iAdvertising;
    private List<AdvertisingBean> advertisingBeen;

    public BannerUtil(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        advertisingBeen = new ArrayList<>(5);
    }

//    private void getTopBannerImage() {
//        iAdvertising.getAdvertisings(UserPreferUtil.getInstanse().getRoleId(),
//                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
//                .compose(RxLifecycleCompact.bind(this).withObservable())
//                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
//                .subscribe(bean -> {
//                    List<AdvertisingBean> list = bean.getData();
//                    if (bean.isSuccess() && ListUtil.unEmpty(list)) {
//                        advertisingBeen.clear();
//                        bannerImages.clear();
//                        advertisingBeen.addAll(list);
//                        for (AdvertisingBean advertising : list) {
//                            bannerImages.add(advertising.getImg());
//                        }
//                    } else {
//                        bannerImages.add(R.mipmap.banner_icon1);
//                        bannerImages.add(R.mipmap.banner_icon2);
//                    }
//                    LayoutUtil.setBanner(banner, bannerImages);
//                }, new HttpError());
//    }
//
//    private void getBannerDetail(int absId, String comeFrom) {
//        iAdvertising.getAdvertisingsDetail(UserPreferUtil.getInstanse().getRoleId(),
//                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId(),
//                absId, comeFrom)
//                .compose(RxLifecycleCompact.bind(this).withObservable())
//                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
//                .subscribe(bean -> {
//                    if (bean.isSuccess()) {
//                        AdvertisingDetailBean detail = bean.getData();
//                        List list = new ArrayList();
//                        if (!TextUtils.isEmpty(detail.getContent())) {
//                            list.add(detail.getContent());
//                        }
//                        if (ListUtil.unEmpty(detail.getImages())) {
//                            for (String s : detail.getImages()) {
//                                ImageString imageString = new ImageString();
//                                imageString.setUrl(s);
//                                list.add(imageString);
//                            }
//                        }
//                        if (ListUtil.unEmpty(list)) {
//                            advertisingDialog.setListData(list);
//                            advertisingDialog.show(recyclerView);
//                        }
//                    }
//                }, new HttpError());
//    }

}
