package bag.small.ui.activity;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.caimuhao.rxpicker.ui.fragment.mvp.PickerFragmentContract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IUpdateImage;
import bag.small.provider.CheckImageProvider;
import bag.small.rx.RxUtil;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

public class PublishMsgActivity extends BaseActivity {

    @BindView(R.id.create_memorandum_send_tv)
    TextView toolbarRightTv;
    @BindView(R.id.ac_publish_edt)
    EditText acPublishEdt;
    @BindView(R.id.ac_publish_recycler)
    RecyclerView acPublishRecycler;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.create_memorandum_cancel_tv)
    TextView cancel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ProgressDialog progressDialog;
    List<String> mDatas;

    IUpdateImage iUpdateImage;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_publish_msg;
    }

    @Override
    public void initData() {
        toolbar.setNavigationIcon(null);
        toolbarTitleTv.setText("新建成长日记");
        toolbarRightTv.setText("发布");
        mDatas = new ArrayList<>();
        mDatas.add("");
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(mDatas);
        multiTypeAdapter.register(String.class, new CheckImageProvider(this));
        acPublishRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        acPublishRecycler.setAdapter(multiTypeAdapter);
        iUpdateImage = HttpUtil.getInstance().createApi(IUpdateImage.class);
    }

    @OnClick({R.id.create_memorandum_send_tv, R.id.create_memorandum_cancel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_memorandum_send_tv:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("正在上传，请等待...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                getImageCompress();
                break;
            case R.id.create_memorandum_cancel_tv:
                finish();
                break;
        }

    }

    private void sendMessage(List<MultipartBody.Part> parts) {
        String content = StringUtil.EditGetString(acPublishEdt);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("role_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getRoleId()));
        map.put("school_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getSchoolId()));
        map.put("login_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getUserId()));
        map.put("page", RxUtil.toRequestBodyTxt("1"));
        map.put("content", RxUtil.toRequestBodyTxt(content));
        if (ListUtil.unEmpty(parts)) {
            iUpdateImage.updateImage(map, parts)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(PublishMsgActivity.this).withObservable())
                    .doFinally(() -> {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    })
                    .subscribe(bean -> {
                        if (bean.isSuccess()) {
                            finish();
                        }
                        toast(bean.getMsg());
                    }, new HttpError());
        } else {
            iUpdateImage.updateImage(map)
                    .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                    .compose(RxLifecycleCompact.bind(PublishMsgActivity.this).withObservable())
                    .doFinally(() -> {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    })
                    .subscribe(bean -> {
                        if (bean.isSuccess()) {
                            finish();
                        }
                        toast(bean.getMsg());
                    }, new HttpError());
        }

    }


    //压缩
    private void getImageCompress() {
        if (isEmptyImage()) {
            sendMessage(null);
        } else {
            List<MultipartBody.Part> parts = new ArrayList<>(9);
            final int[] count = {0};
            int size = getImageSize();
            Observable.create((ObservableOnSubscribe<String>) e -> {
                for (String mData : mDatas) {
                    if (!TextUtils.isEmpty(mData)) {
                        e.onNext(mData);
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .map(s -> Luban.with(PublishMsgActivity.this).load(new File(s)).get())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(file -> {
                        count[0]++;
                        String fileName = System.currentTimeMillis() + ".png";
                        parts.add(RxUtil.convertImage("file" + count[0], fileName, file));
                        if (count[0] == size) {
                            sendMessage(parts);
                        }
                        LogUtil.show("onNext");
                    }, throwable -> {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        LogUtil.show(throwable.getMessage());
                    }, () -> {
                        LogUtil.show("complete");
                    });
        }
    }

    private List<String> getImage() {
        List<String> images = new ArrayList<>(9);
        for (String data : mDatas) {
            if (!TextUtils.isEmpty(data)) {
                images.add(data);
            }
        }
        return images;
    }

    private int getImageSize() {
        int size = 0;
        for (String data : mDatas) {
            if (!TextUtils.isEmpty(data)) {
                size++;
            }
        }
        return size;
    }

    private boolean isEmptyImage() {
        for (String mData : mDatas) {
            if (!"".equals(mData)) {
                return false;
            }
        }
        return true;
    }

}
