package bag.small.ui.fragment;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.dialog.AdvertisingDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.AdvertisingBean;
import bag.small.entity.AdvertisingDetailBean;
import bag.small.entity.ConnectionBinder;
import bag.small.entity.ImageString;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IAdvertising;
import bag.small.http.IApi.INotification;
import bag.small.interfaze.IDialog;
import bag.small.provider.ConnectionViewBinder;
import bag.small.rx.RxUtil;
import bag.small.ui.activity.WebViewActivity;
import bag.small.utils.GlideImageLoader;
import bag.small.utils.LayoutUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags;

public class FamiliesSchoolConnectionFragment extends BaseFragment implements OnBannerListener {
    //    @BindView(R.id.banner_imageview)
//    ImageView bannerImage;
    @BindView(R.id.top_banner)
    Banner banner;
    @BindView(R.id.fragment_family_grid_view)
    RecyclerView recyclerView;
    MultiTypeAdapter mAdapter;
    List<Object> mItemBeans;
    INotification iNotification;
    private ConnectionBinder biner2;
    private ConnectionBinder biner8;
    private List bannerImages;
    private IAdvertising iAdvertising;
    private List<AdvertisingBean> advertisingBeen;
    private AdvertisingDialog advertisingDialog;
    NoticeDialogSnap noticeDialogSnap;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_family_school_connection;
    }

    @Override
    public void initData() {
        mItemBeans = new ArrayList<>(9);

        ConnectionBinder biner1 = new ConnectionBinder();
        biner1.setId(1);
        biner1.setTitle("备忘录");
        biner2 = new ConnectionBinder();
        biner2.setId(2);
        biner2.setTitle("教务通知");
        ConnectionBinder biner3 = new ConnectionBinder();
        biner3.setId(3);
        biner3.setTitle("事/病假");
        ConnectionBinder biner4 = new ConnectionBinder();
        biner4.setId(4);
        biner4.setTitle("课程表");
        ConnectionBinder biner5 = new ConnectionBinder();
        biner5.setId(5);
        biner5.setTitle("成长手册");
        ConnectionBinder biner6 = new ConnectionBinder();
        biner6.setId(6);
        biner6.setTitle("作业本");
        ConnectionBinder biner7 = new ConnectionBinder();
        biner7.setId(7);
        biner7.setTitle("社团");
        biner8 = new ConnectionBinder();
        biner8.setId(8);
        biner8.setTitle("兴趣课");
        ConnectionBinder biner9 = new ConnectionBinder();
        biner9.setId(9);
        biner9.setTitle("在线留言");
        if (UserPreferUtil.getInstanse().isTeacher()) {
            biner1.setResImage(R.mipmap.memo_green);
            biner2.setResImage(R.mipmap.notice_green);
            biner3.setResImage(R.mipmap.holiday_green);
            biner4.setResImage(R.mipmap.subject_green);
            biner5.setResImage(R.mipmap.grow_up_green);
            biner6.setResImage(R.mipmap.homework_green);
            biner7.setResImage(R.mipmap.club_green);
            biner8.setResImage(R.mipmap.interest_green);
            biner9.setResImage(R.mipmap.online_green);
        } else {
            biner1.setResImage(R.mipmap.memo_yellow);
            biner2.setResImage(R.mipmap.notice_yellow);
            biner3.setResImage(R.mipmap.holiday_yellow);
            biner4.setResImage(R.mipmap.subject_yellow);
            biner5.setResImage(R.mipmap.grow_up_yellow);
            biner6.setResImage(R.mipmap.homework_yellow);
            biner7.setResImage(R.mipmap.club_yellow);
            biner8.setResImage(R.mipmap.interest_yellow);
            biner9.setResImage(R.mipmap.online_yellow);
        }
        mItemBeans.add(biner1);
        mItemBeans.add(biner2);
        mItemBeans.add(biner3);
        mItemBeans.add(biner4);
        mItemBeans.add(biner5);
        mItemBeans.add(biner6);
        mItemBeans.add(biner7);
        mItemBeans.add(biner8);
        mItemBeans.add(biner9);
        mAdapter = new MultiTypeAdapter(mItemBeans);
        bannerImages = new ArrayList<>();
        advertisingBeen = new ArrayList<>();
        advertisingDialog = new AdvertisingDialog(getContext());
    }

    @Override
    public void initView() {
        mAdapter.register(ConnectionBinder.class, new ConnectionViewBinder());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        recyclerView.setAdapter(mAdapter);
        setTouch();
        iNotification = HttpUtil.getInstance().createApi(INotification.class);
        iAdvertising = HttpUtil.getInstance().createApi(IAdvertising.class);
        noticeDialogSnap = new NoticeDialogSnap(getContext());
        setNoticeCount();
        getTopBannerImage();
        banner.setOnBannerListener(this);
    }

    private void setTouch() {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，
             * 如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当前拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mItemBeans, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mItemBeans, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                myAdapter.notifyItemRemoved(position);
//                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundResource(R.color.white);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setNoticeCount() {
        iNotification.getNoticeCount(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        biner2.setCount(bean.getData().getTeach_notice());
                        biner8.setCount(bean.getData().getInterest_notice());
                        mAdapter.notifyDataSetChanged();
                    }
                }, new HttpError());
    }

    private void getTopBannerImage() {
        iAdvertising.getAdvertisings(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    List<AdvertisingBean> list = bean.getData();
                    if (bean.isSuccess() && ListUtil.unEmpty(list)) {
                        advertisingBeen.clear();
                        bannerImages.clear();
                        advertisingBeen.addAll(list);
                        for (AdvertisingBean advertising : list) {
                            bannerImages.add(advertising.getImg());
                        }
                    } else {
                        bannerImages.add(R.mipmap.banner_icon1);
                        bannerImages.add(R.mipmap.banner_icon2);
                    }
                    LayoutUtil.setBanner(banner, bannerImages);
                }, new HttpError());
    }

    private void getBannerDetail(int absId, String comeFrom) {
        iAdvertising.getAdvertisingsDetail(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId(),
                absId, comeFrom)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                    if (bean.isSuccess()) {
                        AdvertisingDetailBean detail = bean.getData();
                        noticeDialogSnap.show();
                        noticeDialogSnap.setShowContent(detail.getTitle(), detail.getContent());
                        noticeDialogSnap.setList(detail.getImages());
                    }
                }, new HttpError());
    }

    @Override
    public void OnBannerClick(int position) {
        LogUtil.show("position: " + position);
        AdvertisingBean bean = advertisingBeen.get(position);
        if (TextUtils.isEmpty(bean.getUrl())) {
            //弹框
            getBannerDetail(bean.getAds_id(), bean.getCame_from());
        } else {
            //网页
            Bundle bundle = new Bundle();
            bundle.putString("url", bean.getUrl());
            goActivity(WebViewActivity.class, bundle);
        }
    }

    @Override
    public void onFragmentShow() {
        setNoticeCount();
        banner.startAutoPlay();
        setItemImage();
    }

    private void setItemImage() {
        if (UserPreferUtil.getInstanse().isTeacher()) {
            ((ConnectionBinder) mItemBeans.get(0)).setResImage(R.mipmap.memo_green);
            biner2.setResImage(R.mipmap.notice_green);
            ((ConnectionBinder) mItemBeans.get(2)).setResImage(R.mipmap.holiday_green);
            ((ConnectionBinder) mItemBeans.get(3)).setResImage(R.mipmap.subject_green);
            ((ConnectionBinder) mItemBeans.get(4)).setResImage(R.mipmap.grow_up_green);
            ((ConnectionBinder) mItemBeans.get(5)).setResImage(R.mipmap.homework_green);
            ((ConnectionBinder) mItemBeans.get(6)).setResImage(R.mipmap.club_green);
            biner8.setResImage(R.mipmap.interest_green);
            ((ConnectionBinder) mItemBeans.get(8)).setResImage(R.mipmap.online_green);
        } else {
            ((ConnectionBinder) mItemBeans.get(0)).setResImage(R.mipmap.memo_yellow);
            biner2.setResImage(R.mipmap.notice_yellow);
            ((ConnectionBinder) mItemBeans.get(2)).setResImage(R.mipmap.holiday_yellow);
            ((ConnectionBinder) mItemBeans.get(3)).setResImage(R.mipmap.subject_yellow);
            ((ConnectionBinder) mItemBeans.get(4)).setResImage(R.mipmap.grow_up_yellow);
            ((ConnectionBinder) mItemBeans.get(5)).setResImage(R.mipmap.homework_yellow);
            ((ConnectionBinder) mItemBeans.get(6)).setResImage(R.mipmap.club_yellow);
            biner8.setResImage(R.mipmap.interest_yellow);
            ((ConnectionBinder) mItemBeans.get(8)).setResImage(R.mipmap.online_yellow);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentHide() {
        banner.stopAutoPlay();
    }


    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            int positon = parent.getChildLayoutPosition(view);
            int result = positon % 3;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (result == 0) {
                outRect.left = 0;
            } else if (result == 1) {
                outRect.left = 0;
            } else if (result == 2) {
                outRect.right = 0;
                outRect.left = 0;
            }
        }

    }
}
