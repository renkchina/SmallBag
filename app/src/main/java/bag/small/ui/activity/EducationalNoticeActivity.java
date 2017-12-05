package bag.small.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.entity.EducationNoticeBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.INotification;
import bag.small.provider.EducationNoticeViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;

public class EducationalNoticeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    //    @BindView(R.id.activity_education_banner)
//    Banner eBanner;
    @BindView(R.id.activity_education_tab_layout)
    TabLayout eTabLayout;
    @BindView(R.id.activity_education_recycler)
    RecyclerView eRecycler;

    private List<Object> bannerImages;
    private int currentPosition = -1;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> items;
    INotification iNotification;
    List<EducationNoticeBean> noticeList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_educational_notice;
    }

    @Override
    public void initData() {
        bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.banner_icon1);
        bannerImages.add(R.mipmap.banner_icon2);
        items = new ArrayList<>();
        noticeList = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(EducationNoticeBean.ResultsBean.class, new EducationNoticeViewBinder());
        iNotification = HttpUtil.getInstance().createApi(INotification.class);
    }

    @Override
    public void initView() {
        setToolTitle("教务通知", true);
        eTabLayout.addOnTabSelectedListener(this);
        eRecycler.setLayoutManager(new LinearLayoutManager(this));
        eRecycler.setAdapter(multiTypeAdapter);
    }

    private void requestHttp() {
        iNotification.getNotice(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(),
                UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        if (ListUtil.unEmpty(bean.getData())) {
                            noticeList.clear();
                            noticeList.addAll(bean.getData());
                            setTitles(bean.getData());
                        } else {
                            toast("没有通知消息！");
                        }
                    } else {
                        toast(bean.getMsg());
                    }
                }, new HttpError());
    }

    private void setTitles(List<EducationNoticeBean> data) {
        int position = 0;
        eTabLayout.removeAllTabs();
        position = currentPosition;
        currentPosition = -1;
        if (ListUtil.unEmpty(data)) {
            int size = data.size();
            if (size < 21) {
                for (EducationNoticeBean bean : data) {
                    eTabLayout.addTab(eTabLayout.newTab().setText(bean.getLabel()));
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    eTabLayout.addTab(eTabLayout.newTab().setText(data.get(i).getLabel()));
                }
            }
            if (eTabLayout.getTabAt(position) != null){
                eTabLayout.getTabAt(position).select();
            }

//            LinearLayout linearLayout = (LinearLayout) eTabLayout.getChildAt(0);
//            linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//            linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.divider_item_tablayout));
//            linearLayout.setDividerPadding(20);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始轮播
        requestHttp();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (currentPosition != tab.getPosition()) {
            getTab(tab.getPosition());
            currentPosition = tab.getPosition();
        }
    }

    //请求
    private void getTab(int position) {
        items.clear();
        items.addAll(noticeList.get(position).getResults());
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
