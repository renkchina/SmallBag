package bag.small.listener;

import com.china.rxbus.RxBus;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import bag.small.app.MyApplication;

/**
 * Created by Administrator on 2017/12/11.
 *
 */

public class MyConnectionListener implements EMConnectionListener {
    @Override
    public void onConnected() {
    }

    @Override
    public void onDisconnected(final int error) {
        if (error == EMError.USER_REMOVED) {
            // 显示帐号已经被移除
            RxBus.get().send(520,"帐号已经被移除");
        } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
            // 显示帐号在其他设备登录
            RxBus.get().send(520,"帐号在其他设备登录");
        } else {
            if (NetUtils.hasNetwork(MyApplication.getContext())) {
                //连接不到聊天服务器
                RxBus.get().send(520,"连接不到聊天服务器");
            } else {
                //当前网络不可用，请检查网络设置
                RxBus.get().send(520,"当前网络不可用");

            }
        }
    }

}