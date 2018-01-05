package bag.small.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.china.rxbus.RxBus;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ChoiceTeacherDialog;
import bag.small.download.DownloadManager;
import bag.small.entity.TeacherChatBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IMChats;
import bag.small.interfaze.IDialog;
import bag.small.runtimepermissions.PermissionsManager;
import bag.small.rx.RxUtil;
import bag.small.ui.fragment.ChatFragment;
import bag.small.ui.fragment.FragmentTeacherHistoryChatList;
import bag.small.utils.ListUtil;
import bag.small.utils.PermissionUtils;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class ClassChatActivity extends BaseActivity implements IDialog {

    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ChatFragment chatFragment;
    private FragmentTeacherHistoryChatList teacherChatFragment;
    ChoiceTeacherDialog choiceTeacherDialog;
    IMChats imChats;
    private String banjiId;
    private int pageIndex = 1;
    private Fragment mCurrentFragment;
    private String groupId;

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

    @Override
    public void onFirst() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        banjiId = intent.getStringExtra("banjiId");
        String name = intent.getStringExtra("name");
        if (intent == null || TextUtils.isEmpty(groupId) || TextUtils.isEmpty(banjiId)) {
            toast("进入失败");
            finish();
        }
        chatFragment = new ChatFragment();
        teacherChatFragment = new FragmentTeacherHistoryChatList();
        if (UserPreferUtil.getInstanse().isTeacher()) {
            toolbarRightTv.setVisibility(View.INVISIBLE);
        } else {
            toolbarRightTv.setVisibility(View.VISIBLE);
            toolbarRightTv.setText("老师信息");
        }
        choiceTeacherDialog = new ChoiceTeacherDialog(this);
        StringUtil.setTextView(toolbarTitle, name);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        imChats = HttpUtil.getInstance().createApi(IMChats.class);
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, groupId);
        chatFragment.setArguments(args);
//        getSupportFragmentManager().beginTransaction().add(R.id.chat_container_frame, chatFragment).commit();
        changeFragment(R.id.chat_container_frame, chatFragment, groupId);
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
        if (object == null) {

        } else {
            if (object instanceof String) {
                String ids = (String) object;
                if (!TextUtils.isEmpty(ids)) {
                    progressDialog.setMessage("");
                    changeFragment(R.id.chat_container_frame, teacherChatFragment, groupId);
                    toolbarRightTv.setVisibility(View.INVISIBLE);
                    imChats.getTeachersMsgs(UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getUserId(),
                            UserPreferUtil.getInstanse().getSchoolId(), banjiId, ids, pageIndex)
                            .compose(RxLifecycleCompact.bind(this).withObservable())
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .subscribe(baseBean -> {
                                toast(baseBean.getMsg());
                                if (baseBean.isSuccess()) {
                                    if (ListUtil.unEmpty(baseBean.getData())) {
                                        teacherChatFragment.setChatListDatas(createEMMessage(baseBean.getData()));
                                    }
                                }
                            }, new HttpError());
                } else {

                }
            }
        }

    }

    private List<EMMessage> createEMMessage(List<TeacherChatBean> chats) {
        List<EMMessage> list = new ArrayList<>(8);
        for (TeacherChatBean chat : chats) {
            EMMessage message = null;
            switch (chat.getMessage_type()) {
                case "image":
                    File file = new File(DownloadManager.getInstance().videoPath, chat.getFile_name());
                    message = EMMessage.createReceiveMessage(EMMessage.Type.IMAGE);
                    EMImageMessageBody body = new EMImageMessageBody(file);
                    body.setRemoteUrl(chat.getFile_link());
                    body.setThumbnailUrl(chat.getFile_link());
                    body.setFileName(chat.getFile_name());
                    body.setSendOriginalImage(false);
                    message.addBody(body);
                    message.setTo(groupId);
                    break;
                case "text":
                    message = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
                    EMTextMessageBody body1 = new EMTextMessageBody(chat.getMessage());
                    message.addBody(body1);
                    message.setTo(groupId);
                    break;
            }
            if (message != null) {
                message.setMsgTime(StringUtil.stringToLong(chat.getCreate_at(), "yyyy-MM-dd HH:mm"));
                message.setLocalTime(new Date().getTime());
                message.setListened(false);
                message.setUnread(false);
                message.setAttribute("isteacher", 1);
                message.setAttribute("ChatUserNick", chat.getUser_name());
                message.setAttribute("ChatUserPic", chat.getLogo());
                list.add(message);
            }
        }
        return list;
    }

    @Override
    public void callBackMiddleMethod() {
//        toolbarRightTv.setVisibility(View.VISIBLE);
//        changeFragment(R.id.chat_container_frame, chatFragment, groupId);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment.equals(chatFragment)) {
            finish();
        } else {
            changeFragment(R.id.chat_container_frame, chatFragment, groupId);
            toolbarRightTv.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
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

    private PermissionUtils.PermissionGrant mPermissionGrant = requestCode -> {
        switch (requestCode) {
//            case PermissionUtils.CODE_RECORD_AUDIO:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_GET_ACCOUNTS:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_READ_PHONE_STATE:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_CALL_PHONE:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_CAMERA:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
//                break;
//            case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                Toast.makeText(ClassChatActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    };

    public void changeFragment(int resView, @NonNull Fragment targetFragment, String groupId) {
        if (targetFragment.equals(mCurrentFragment)) {
            return;
        }
        FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            Bundle args = new Bundle();
            args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
            args.putString(EaseConstant.EXTRA_USER_ID, groupId);
            targetFragment.setArguments(args);

            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
            transaction.hide(mCurrentFragment);
        }
        mCurrentFragment = targetFragment;
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

}
