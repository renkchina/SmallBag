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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;

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
        map.put("page", RxUtil.toRequestBodyTxt(1 + ""));
        map.put("content", RxUtil.toRequestBodyTxt(content));
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在上传，请等待...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        iUpdateImage.updateImage(map, getParts())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .compose(RxLifecycleCompact.bind(this).withObservable())
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
                    MultipartBody.Part part = RxUtil.convertImage("files[]", "file" + i, new File(string));
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
