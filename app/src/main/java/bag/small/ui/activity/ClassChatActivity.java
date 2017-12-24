package bag.small.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.hyphenate.easeui.EaseConstant;

import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ChoiceTeacherDialog;
import bag.small.entity.BaseBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMChats;
import bag.small.interfaze.IDialog;
import bag.small.runtimepermissions.PermissionsManager;
import bag.small.rx.RxUtil;
import bag.small.ui.fragment.ChatFragment;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.functions.Consumer;

public class ClassChatActivity extends BaseActivity implements IDialog {

    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private ChatFragment chatFragment;
    ChoiceTeacherDialog choiceTeacherDialog;
    IMChats imChats;
    private String banjiId;
    private int pageIndex = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_class_chat;
    }

    @Override
    public void register() {
        RxBus.get().register(this);
    }

    @Override
    public void unRegister() {
        RxBus.get().unRegister(this);
    }

//    @MySubscribe(code = 520, threadMode = ThreadMode.MAIN)
//    public void showConnectedState(String content) {
//        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void initData() {
        toolbarRightTv.setText("老师信息");
        chatFragment = new ChatFragment();
        choiceTeacherDialog = new ChoiceTeacherDialog(this);
        Intent intent = getIntent();
        String groupId = intent.getStringExtra("groupId");
        banjiId = intent.getStringExtra("banjiId");
        String name = intent.getStringExtra("name");
        if (intent == null || TextUtils.isEmpty(groupId) || TextUtils.isEmpty(banjiId)) {
            toast("进入失败");
            finish();
        }
        setToolTitle(name, true);
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, groupId);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_container_frame, chatFragment).commit();
        imChats = HttpUtil.getInstance().createApi(IMChats.class);
    }

    @Override
    public void initView() {
        imChats.getClassTeachers(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId(), banjiId)
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(baseBean -> {
                    if (baseBean.isSuccess()) {
                        if (ListUtil.unEmpty(baseBean.getData())) {
                            choiceTeacherDialog.initData(baseBean.getData());
                        }
                    } else {
                        toast(baseBean.getMsg());
                    }
                }, new HttpError());
    }

    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        choiceTeacherDialog.show();
    }

    @Override
    public void callBackMethod(Object object, Object bean) {
        if (object instanceof String) {
            String ids = (String) object;
            if (!TextUtils.isEmpty(ids)) {
                imChats.getTeachersMsgs(UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getUserId(),
                        UserPreferUtil.getInstanse().getSchoolId(), banjiId, ids, pageIndex)
                        .compose(RxLifecycleCompact.bind(this).withObservable())
                        .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                        .subscribe(baseBean -> {
                            toast(baseBean.getMsg());
                            if (baseBean.isSuccess()) {

                            }
                        }, new HttpError());
            }

        }
    }

    @Override
    public void callBackMiddleMethod() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
