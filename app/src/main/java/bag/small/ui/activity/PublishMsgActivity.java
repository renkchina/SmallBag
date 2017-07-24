package bag.small.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.http.HttpUtil;
import bag.small.provider.CheckImageProvider;
import bag.small.utils.ListUtil;
import bag.small.utils.StringUtil;
import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishMsgActivity extends BaseActivity {

    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @Bind(R.id.ac_publish_edt)
    EditText acPublishEdt;
    @Bind(R.id.ac_publish_recycler)
    RecyclerView acPublishRecycler;

    MultiTypeAdapter multiTypeAdapter;
    List<Object> mDatas;
//    ISendMsg iSendMsg;
//    IUpdateImage iUpdateImage;
//    CommonProgressDialog progressDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_publish_msg;
    }

    @Override
    public void initData() {
        setToolTitle(true);
        toolbarRightTv.setText("发送");
//        progressDialog = new CommonProgressDialog(this);
        mDatas = new ArrayList<>();
        mDatas.add("");
        multiTypeAdapter = new MultiTypeAdapter(mDatas);
        multiTypeAdapter.register(String.class, new CheckImageProvider(this));
        acPublishRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        acPublishRecycler.setAdapter(multiTypeAdapter);
//        iSendMsg = HttpUtil.getInstance().createApi(ISendMsg.class);
//        iUpdateImage = HttpUtil.getInstance().createApi(IUpdateImage.class);
    }


    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        String content = StringUtil.EditGetString(acPublishEdt);
//        sendMessage(content);
    }

//    private void sendMessage(String content) {
//        Disposable request;
//        if (ListUtil.unEmpty(mDatas)) {
//            progressDialog.setTips("正在上传，请等待...");
//            request = iUpdateImage.updateImages(getParts())
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .doOnSubscribe(disposable -> progressDialog.show())
//                    .flatMap(new Function<BaseBean<List<String>>, ObservableSource<BaseBean<String>>>() {
//                        @Override
//                        public ObservableSource<BaseBean<String>> apply(@NonNull BaseBean<List<String>> listBaseBean) throws Exception {
//                            if (ListUtil.unEmpty(listBaseBean.getData()))
//                                return iSendMsg.compares(content,
//                                        UserPreferUtil.getInstanse().getUserId(),
//                                        getPaths(listBaseBean.getData()));
//                            else {
//                                return iSendMsg.compare(content,
//                                        UserPreferUtil.getInstanse().getUserId());
//                            }
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(bean -> {
//                        toast(bean.getMsg());
//                        PublishMsgActivity.this.finish();
//                    }, new HttpError(progressDialog));
//
//        } else {
//            request = iSendMsg.compare(content,
//                    UserPreferUtil.getInstanse().getUserId())
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .doOnSubscribe(disposable -> progressDialog.show())
//                    .subscribe(bean -> {
//                        toast(bean.getMsg());
//                        PublishMsgActivity.this.finish();
//                    }, new HttpError(progressDialog));
//        }
//        recycle(request);
//    }


    private MultipartBody.Part convert(String key, File file) {
        return MultipartBody.Part.createFormData(key, key,
                RequestBody.create(MultipartBody.FORM, file));
    }

    private MultipartBody.Part[] getParts() {
        if (ListUtil.unEmpty(mDatas)) {
            int size = mDatas.size();
            MultipartBody.Part[] parts = new MultipartBody.Part[size];
            for (int i = 0; i < size; i++) {
                String string = mDatas.get(i).toString();
                if (!TextUtils.isEmpty(string)) {
                    MultipartBody.Part part = convert("files", new File(string));
                    parts[i] = part;
                }
            }
            return parts;
        } else {
            return null;
        }
    }

    private String[] getPaths(List<String> lists) {
        if (ListUtil.unEmpty(lists)) {
            int size = lists.size();
            String[] paths = new String[size];
            for (int i = 0; i < size; i++) {
                paths[i] = lists.get(i);
            }
            return paths;
        }
        return null;
    }

}
