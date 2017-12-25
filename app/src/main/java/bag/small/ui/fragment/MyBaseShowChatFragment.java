package bag.small.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseChatRoomListener;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bag.small.utils.UserPreferUtil;

/**
 * Created by Administrator on 2017/12/18.
 */

public class MyBaseShowChatFragment extends EaseBaseFragment implements EMMessageListener {
    protected static final String TAG = "MyBaseAllChatFragment";
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;

    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    /**
     * 聊天列表控件
     */
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected EMConversation conversation;

    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    //    protected Handler handler = new Handler();
    protected File cameraFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected MyBaseShowChatFragment.GroupListener groupListener;
    protected MyBaseShowChatFragment.ChatRoomListener chatRoomListener;
    protected EMMessage contextMenuMessage;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;

    protected int[] itemStrings = {com.hyphenate.easeui.R.string.attach_take_pic, com.hyphenate.easeui.R.string.attach_picture/*, R.string.attach_location */};
    protected int[] itemdrawables = {com.hyphenate.easeui.R.drawable.ease_chat_takepic_selector, com.hyphenate.easeui.R.drawable.ease_chat_image_selector/*, R.drawable.ease_chat_location_selector*/};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE/*, ITEM_LOCATION*/};
    private boolean isMessageListInited;
    protected boolean isRoaming = false;
    private ExecutorService fetchQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.hyphenate.easeui.R.layout.ease_fragment_chat, container, false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, boolean roaming) {
//        isRoaming = roaming;
        return inflater.inflate(com.hyphenate.easeui.R.layout.ease_fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        fragmentArgs = getArguments();
        // check if single chat or group chat
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * init view
     */
    protected void initView() {
        // hold to record voice
        //noinspection ConstantConditions
        voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(com.hyphenate.easeui.R.id.voice_recorder);

        // message list layout
        messageList = (EaseChatMessageList) getView().findViewById(com.hyphenate.easeui.R.id.message_list);
        if (chatType != EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(true);
//        messageList.setAvatarShape(1);
        listView = messageList.getListView();

        inputMenu = (EaseChatInputMenu) getView().findViewById(com.hyphenate.easeui.R.id.input_menu);
        // init input menu
        inputMenu.init(null);
        //输入监听

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(com.hyphenate.easeui.R.color.holo_blue_bright, com.hyphenate.easeui.R.color.holo_green_light,
                com.hyphenate.easeui.R.color.holo_orange_light, com.hyphenate.easeui.R.color.holo_red_light);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    protected void setUpView() {

        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        setRefreshLayoutListener();

    }




    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager()
                .getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);

        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number

//        if (!isRoaming) {
//            final List<EMMessage> msgs = conversation.getAllMessages();
//            int msgCount = msgs != null ? msgs.size() : 0;
//            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
//                String msgId = null;
//                if (msgs != null && msgs.size() > 0) {
//                    msgId = msgs.get(0).getMsgId();
//                }
//                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
//            }
//        } else {
//            fetchQueue.execute(() -> {
//                try {
//                    EMClient.getInstance().chatManager().fetchHistoryMessages(
//                            toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize, "");
//                    final List<EMMessage> msgs = conversation.getAllMessages();
//                    int msgCount = msgs != null ? msgs.size() : 0;
//                    if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
//                        String msgId = null;
//                        if (msgs != null && msgs.size() > 0) {
//                            msgId = msgs.get(0).getMsgId();
//                        }
//                        conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
//                    }
//                    messageList.refreshSelectLast();
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }


    @SuppressLint("ClickableViewAccessibility")
    protected void onMessageListInit() {
//        messageList.init(toChatUsername, chatType, chatFragmentHelper != null ?
//                chatFragmentHelper.onSetCustomChatRowProvider() : null);
//        setListItemClickListener();


        isMessageListInited = true;
    }


    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            if (!isRoaming) {
                loadMoreLocalMessage();
            } else {
                loadMoreRoamingMessages();
            }
        }, 600));
    }

    private void loadMoreLocalMessage() {
        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
            List<EMMessage> messages;
            try {
                messages = conversation.loadMoreMsgFromDB(conversation.getAllMessages().size() == 0 ? "" : conversation.getAllMessages().get(0).getMsgId(),
                        pagesize);
            } catch (Exception e1) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            if (messages.size() > 0) {
                messageList.refreshSeekTo(messages.size() - 1);
                if (messages.size() != pagesize) {
                    haveMoreData = false;
                }
            } else {
                haveMoreData = false;
            }

            isloading = false;
        } else {
            Toast.makeText(getActivity(), getResources().getString(com.hyphenate.easeui.R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadMoreRoamingMessages() {
        if (!haveMoreData) {
            Toast.makeText(getActivity(), getResources().getString(com.hyphenate.easeui.R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        if (fetchQueue != null) {
            fetchQueue.execute(() -> {
                try {
                    List<EMMessage> messages = conversation.getAllMessages();
                    EMClient.getInstance().chatManager().fetchHistoryMessages(
                            toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize,
                            (messages != null && messages.size() > 0) ? messages.get(0).getMsgId() : "");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                } finally {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(() -> loadMoreLocalMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        EMClient.getInstance().chatManager().removeMessageListener(this);

        // remove activity from foreground activity list
        EaseUI.getInstance().popActivity(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
        }

        if (chatRoomListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomListener(chatRoomListener);
        }

        if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
        }
    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
            }
        }
    }


    // implement methods in EMMessageListener
    @Override
    public void onMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> messages) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object change) {

    }


    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * listen the group event
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            getActivity().runOnUiThread(() -> {
                if (toChatUsername.equals(groupId)) {
                    Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.you_are_group, Toast.LENGTH_LONG).show();
                    Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.the_current_group_destroyed, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }
    }

    /**
     * listen chat room event
     */
    class ChatRoomListener extends EaseChatRoomListener {

        @Override
        public void onChatRoomDestroyed(final String roomId, final String roomName) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (roomId.equals(toChatUsername)) {
                        Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.the_current_chat_room_destroyed, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onRemovedFromChatRoom(final String roomId, final String roomName, final String participant) {
            getActivity().runOnUiThread(() -> {
                if (roomId.equals(toChatUsername)) {
                    Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.quiting_the_chat_room,
                            Toast.LENGTH_LONG).show();
                    Activity activity = getActivity();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
        }


        @Override
        public void onMemberJoined(final String roomId, final String participant) {
            if (roomId.equals(toChatUsername)) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        "member join:" + participant, Toast.LENGTH_LONG).show());
            }
        }

        @Override
        public void onMemberExited(final String roomId, final String roomName, final String participant) {
            if (roomId.equals(toChatUsername)) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        "member exit:" + participant, Toast.LENGTH_LONG).show());
            }
        }


    }


}