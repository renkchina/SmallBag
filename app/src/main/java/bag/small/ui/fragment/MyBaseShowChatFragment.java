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
 * /
 */

public class MyBaseShowChatFragment extends EaseBaseFragment implements EMMessageListener {
    protected static final String TAG = "MyBaseShowChatFragment";

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
    protected MyBaseAllChatFragment.GroupListener groupListener;
    protected MyBaseAllChatFragment.ChatRoomListener chatRoomListener;
    protected EMMessage contextMenuMessage;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;

    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;
    protected boolean isRoaming = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        extendMenuItemClickListener = new MyBaseShowChatFragment.MyItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(com.hyphenate.easeui.R.id.input_menu);
//        registerExtendMenuItem();
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
        titleBar.setVisibility(View.GONE);
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
    }
    @SuppressLint("ClickableViewAccessibility")
    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatType, null);

        setListItemClickListener();

        messageList.getListView().setOnTouchListener((v, event) -> {
            hideKeyboard();
            inputMenu.hideExtendMenuContainer();
            return false;
        });

        isMessageListInited = true;
    }
    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatFragmentHelper == null) {
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);
            }

        });
    }
    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            if (!isRoaming) {
//                loadMoreLocalMessage();
            } else {
//                loadMoreRoamingMessages();
            }
            swipeRefreshLayout.setRefreshing(false);
        }, 600));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
       // EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
//         EMClient.getInstance().chatManager().addMessageListener(this);
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
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
     * handle the click event for extend menu
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {
        @Override
        public void onClick(int itemId, View view) {
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    break;
                case ITEM_PICTURE:
                    break;
                case ITEM_LOCATION:
                    break;

                default:
                    break;
            }
        }
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

    protected MyChatFragmentHelper chatFragmentHelper;

    public void setChatFragmentHelper(MyChatFragmentHelper chatFragmentHelper) {
        this.chatFragmentHelper = chatFragmentHelper;
    }

    public interface MyChatFragmentHelper {
        /**
         * set message attribute
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * enter to chat detail
         */
        void onEnterToChatDetails();

        /**
         * on avatar clicked
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * on avatar long pressed
         *
         * @param username
         */
        void onAvatarLongClick(String username);

        /**
         * on message bubble clicked
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * on message bubble long pressed
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * on extend menu item clicked, return true if you want to override
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * on set custom chat row provider
         *
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }

}