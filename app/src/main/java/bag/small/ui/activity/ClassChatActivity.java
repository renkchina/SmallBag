package bag.small.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.hyphenate.easeui.EaseConstant;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ChoiceTeacherDialog;
import bag.small.interfaze.IDialog;
import bag.small.ui.fragment.MyChatFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassChatActivity extends BaseActivity  implements IDialog{

    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private MyChatFragment chatFragment;
        ChoiceTeacherDialog choiceTeacherDialog;
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
        toolbarRightTv.setText("老师信息");
        chatFragment = new MyChatFragment();
        choiceTeacherDialog = new ChoiceTeacherDialog(this);
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, "zw123");
        chatFragment.setArguments(args);

    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.chat_container_frame, chatFragment).commit();
    }

    @OnClick(R.id.toolbar_right_tv)
    public void onViewClicked() {
        choiceTeacherDialog.show();
    }

    @Override
    public void callBackMethod(Object object, Object bean) {

    }

    @Override
    public void callBackMiddleMethod() {

    }
}
