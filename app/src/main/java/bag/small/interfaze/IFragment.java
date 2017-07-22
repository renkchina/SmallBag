package bag.small.interfaze;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface IFragment {
    /**
     * 获取布局文件
     */
    int getLayoutResId();

    /**
     * Fragment被切换到前台时调用
     */
    void onFragmentShow();

    /**
     * Fragment被切换到后台时调用
     */
    void onFragmentHide();

    /**
     * 第一次启动会执行此方法
     */
    void onFirst();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();
}
