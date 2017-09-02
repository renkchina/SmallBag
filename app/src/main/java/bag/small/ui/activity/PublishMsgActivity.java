package bag.small.ui.activity;

import android.app.ProgressDialog;
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
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
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
    List<Object> mDatas;
    IUpdateImage iUpdateImage;
    private ProgressDialog progressDialog;

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
        String content = StringUtil.EditGetString(acPublishEdt);
        sendMessage(content);
    }

    private void sendMessage(String content) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("role_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getRoleId()));
        map.put("school_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getSchoolId()));
        map.put("login_id", RxUtil.toRequestBodyTxt(UserPreferUtil.getInstanse().getUserId()));
        map.put("page", RxUtil.toRequestBodyTxt("1"));
        map.put("content", RxUtil.toRequestBodyTxt(content));


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在上传，请等待...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

//        lubanImage(map);
        iUpdateImage.updateImage(map, getParts())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(PublishMsgActivity.this).withObservable())
                .subscribe(bean -> {
                    progressDialog.dismiss();
                    if (bean.isSuccess()) {
                        finish();
                    }
                    toast(bean.getMsg());
                }, new HttpError(progressDialog));

    }

    private MultipartBody.Part[] getParts() {
        if (ListUtil.unEmpty(mDatas)) {
            int size = mDatas.size();
            MultipartBody.Part[] parts = new MultipartBody.Part[size];
            for (int i = 0; i < size; i++) {
                String string = mDatas.get(i).toString();
                if (!TextUtils.isEmpty(string)) {
                    String key = "file" + (i + 1);
                    String fileName = System.currentTimeMillis() + ".png";
//                    int finalI = i;
//                    Luban.with(this).load(new File(string))
//                            .setCompressListener(new OnCompressListener() {
//                                @Override
//                                public void onStart() {
//
//                                }
//                                @Override
//                                public void onSuccess(File file) {
//                                    MultipartBody.Part part = RxUtil.convertImage(key, fileName, file);
//                                    parts[finalI] = part;
//                                }
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//                            }).launch();
                    MultipartBody.Part part = RxUtil.convertImage(key, fileName, new File(string));
                    parts[i] = part;
                }

            }
            return parts;
        } else {
            return null;
        }
    }

    private void lubanImage(HashMap<String, RequestBody> map) {
        if (ListUtil.unEmpty(mDatas)) {
            int size = mDatas.size();
            MultipartBody.Part[] parts = new MultipartBody.Part[size];
            for (int i = 0; i < size; i++) {
                String string = mDatas.get(i).toString();
                if (!TextUtils.isEmpty(string)) {
                    String key = "file" + (i + 1);
                    String fileName = System.currentTimeMillis() + ".png";
                    int finalI = i;
                    final int[] count = {0};
                    Luban.with(this).load(new File(string))
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    MultipartBody.Part part = RxUtil.convertImage(key, fileName, file);
                                    parts[finalI] = part;
                                    count[0]++;
                                    if (count[0] == size) {
                                        iUpdateImage.updateImage(map, parts)
                                                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                                                .compose(RxLifecycleCompact.bind(PublishMsgActivity.this).withObservable())
                                                .subscribe(bean -> {
                                                    progressDialog.dismiss();
                                                    if (bean.isSuccess()) {
                                                        finish();
                                                    }
                                                    toast(bean.getMsg());
                                                }, new HttpError(progressDialog));
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }).launch();
                }

            }
        }
    }

    private MultipartBody getBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (ListUtil.unEmpty(mDatas)) {
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                String string = mDatas.get(i).toString();
                if (!TextUtils.isEmpty(string)) {
                    String key = "file" + (i + 1);
                    String fileName = System.currentTimeMillis() + ".png";
                    builder.addFormDataPart(key, fileName,
                            RequestBody.create(MediaType.parse("image/png;charset=utf-8"), new File(string)));
                }
            }
        }
        return builder.build();
    }


}
