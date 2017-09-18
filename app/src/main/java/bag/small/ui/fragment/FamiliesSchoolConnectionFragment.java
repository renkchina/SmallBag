package bag.small.ui.fragment;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseFragment;
import bag.small.entity.ConnectionBinder;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.INotification;
import bag.small.provider.ConnectionViewBinder;
import bag.small.rx.RxUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.MultiTypeAdapter;


public class FamiliesSchoolConnectionFragment extends BaseFragment {
    @BindView(R.id.banner_imageview)
    ImageView bannerImage;
    @BindView(R.id.fragment_family_grid_view)
    RecyclerView recyclerView;
    MultiTypeAdapter mAdapter;
    List<Object> mItemBeans;
    INotification iNotification;
    private ConnectionBinder biner2;
    private ConnectionBinder biner8;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_family_school_connection;
    }

    @Override
    public void initData() {
        mItemBeans = new ArrayList<>(9);
        ConnectionBinder biner1 = new ConnectionBinder();
        biner1.setId(1);
        biner1.setResImage(R.mipmap.beiwanglu_b);
        biner1.setTitle("备忘录");
        biner2 = new ConnectionBinder();
        biner2.setId(2);
        biner2.setResImage(R.mipmap.tongzhi_b);
        biner2.setTitle("教务通知");
        ConnectionBinder biner3 = new ConnectionBinder();
        biner3.setId(3);
        biner3.setResImage(R.mipmap.bingjia_b);
        biner3.setTitle("事/病假");
        ConnectionBinder biner4 = new ConnectionBinder();
        biner4.setId(4);
        biner4.setResImage(R.mipmap.kechengbiao_b);
        biner4.setTitle("课程表");
        ConnectionBinder biner5 = new ConnectionBinder();
        biner5.setId(5);
        biner5.setResImage(R.mipmap.chengzhang_b);
        biner5.setTitle("成长手册");
        ConnectionBinder biner6 = new ConnectionBinder();
        biner6.setId(6);
        biner6.setResImage(R.mipmap.zuoyeben_b);
        biner6.setTitle("作业本");
        ConnectionBinder biner7 = new ConnectionBinder();
        biner7.setId(7);
        biner7.setResImage(R.mipmap.shetuan_b);
        biner7.setTitle("社团");
        biner8 = new ConnectionBinder();
        biner8.setId(8);
        biner8.setResImage(R.mipmap.xingquke_b);
        biner8.setTitle("兴趣课");
        ConnectionBinder biner9 = new ConnectionBinder();
        biner9.setId(9);
        biner9.setResImage(R.mipmap.liuyan_b);
        biner9.setTitle("在线留言");
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
    }

    @Override
    public void initView() {
        mAdapter.register(ConnectionBinder.class, new ConnectionViewBinder());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(mAdapter);


        iNotification = HttpUtil.getInstance().createApi(INotification.class);
        setNoticeCount();
        setImage();
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


    @Override
    public void onFragmentShow() {
        setNoticeCount();
        setImage();
    }

    public void setImage() {
        bannerImage.setBackgroundResource(MyApplication.bannerImage);
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
            } else if (result ==1){
                outRect.left = 0;
            }else if(result==2){
                outRect.right = 0;
                outRect.left = 0;
            }
        }

    }
}
