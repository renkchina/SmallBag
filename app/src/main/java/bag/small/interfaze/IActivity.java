package bag.small.interfaze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface IActivity {

    /**
     * 获取布局文件
     */
    int getLayoutResId();

    /**
     * 第一次启动会执行此方法
     */
    void onFirst();

    /**
     * 此方法会在setContentView之前调用
     */
    void initPre();

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

    /**
     * 跳转指定activity，并关闭当前activity
     */
    void skipActivity(@NonNull Class<?> cls);

    /**
     * 跳转activity
     */
    void goActivity(@NonNull Intent it);

    /**
     * 跳转activity
     */
    void goActivity(@NonNull Class<?> clazz);

    /**
     * 跳转activity
     */
    void goActivity(@NonNull Class<?> cls, @NonNull Bundle extras);

}
