package bag.small.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import bag.small.R;
import bag.small.base.BaseActivity;

public class ClassChatActivity extends BaseActivity {

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

    @MySubscribe(code = 520, threadMode = ThreadMode.MAIN)
    public void showConnectedState(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, "zw123");
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_container_frame, chatFragment).commit();
    }

}
