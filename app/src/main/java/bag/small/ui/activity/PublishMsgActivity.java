package bag.small.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

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
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PublishMsgActivity extends BaseActivity {

    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @Bind(R.id.ac_publish_edt)
    EditText acPublishEdt;
    @Bind(R.id.ac_publish_recycler)
    RecyclerView acPublishRecycler;

    MultiTypeAdapter multiTypeAdapter;
    List<String> mDatas;
    IUpdateImage iUpdateImage;
    private ProgressDialog progressDialog;
    OnCompressListener onCompressListener;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_publish_msg;
    }

    @Override
    public void initData() {
        setToolTitle(true);
        toolbarRightTv.setText("发布");
        mDatas = new ArrayList<>();
        mDatas.add("");
        multiTypeAdapter = new MultiTypeAdapter(mDatas);
        multiTypeAdapter.register(String.class, new CheckImageProvider(this));
        acPublishRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        acPublishRecycler.setAdapter(multiTypeAdapter);
        iUpdateImage = HttpUtil.getInstance().createApi(IUpdateImage.class);
    }

    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在上传，请等待...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getImageCompress();
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

//    private MultipartBody.Part[] getParts() {
//        if (ListUtil.unEmpty(mDatas)) {
//            int size = mDatas.size();
//            MultipartBody.Part[] parts = new MultipartBody.Part[size];
//            for (int i = 0; i < size; i++) {
//                String string = mDatas.get(i);
//                if (!TextUtils.isEmpty(string)) {
//                    String key = "file" + (i + 1);
//                    String fileName = System.currentTimeMillis() + ".png";
//                    MultipartBody.Part part = RxUtil.convertImage(key, fileName, new File(string));
//                    parts[i] = part;
//                }
//            }
//            return parts;
//        } else {
//            return null;
//        }
//    }

//    private MultipartBody getBody() {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (ListUtil.unEmpty(mDatas)) {
//            int size = mDatas.size();
//            for (int i = 0; i < size; i++) {
//                String string = mDatas.get(i);
//                if (!TextUtils.isEmpty(string)) {
//                    String key = "file" + (i + 1);
//                    String fileName = System.currentTimeMillis() + ".png";
//                    builder.addFormDataPart(key, fileName,
//                            RequestBody.create(MediaType.parse("image/png;charset=utf-8"), new File(string)));
//                }
//            }
//        }
//        return builder.build();
//    }

    //压缩
    private void getImageCompress() {
        if (isEmptyImage()) {
            sendMessage(null);
        } else {
            List<MultipartBody.Part> parts = new ArrayList<>(9);
            final int[] count = {0};
            Observable.create((ObservableOnSubscribe<String>) e -> {
                for (String mData : mDatas) {
                    if (!TextUtils.isEmpty(mData))
                        e.onNext(mData);
                }
            }).subscribeOn(Schedulers.io())
                    .map(s -> Luban.with(PublishMsgActivity.this).load(new File(s)).get())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(file -> {
                        count[0]++;
                        String fileName = System.currentTimeMillis() + ".png";
                        parts.add(RxUtil.convertImage("file" + count[0], fileName, file));
                        if (count[0] == mDatas.size() - 1) {
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

    private boolean isEmptyImage() {
        for (String mData : mDatas) {
            if (!"".equals(mData)) {
                return false;
            }
        }
        return true;
    }
}
