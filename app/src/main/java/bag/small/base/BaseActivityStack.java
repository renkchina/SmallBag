package bag.small.base;

import android.app.Activity;
import android.content.Context;


import java.util.Stack;

import bag.small.interfaze.IActivity;

/**
 * Created by Administrator on 2017/4/11.
 */

public class BaseActivityStack {
    private BaseActivityStack() {
        sActivityStack = new Stack<>();
    }

    private  Stack<IActivity> sActivityStack;

    private static volatile BaseActivityStack INSTANCE;

    public static BaseActivityStack getInstance() {
        if (INSTANCE == null) {
            synchronized (BaseActivityStack.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseActivityStack();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return sActivityStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(IActivity activity) {
        sActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (sActivityStack == null) {
            throw new NullPointerException("Activity stack is Null");
        }
        if (sActivityStack.isEmpty()) {
            return null;
        }
        IActivity activity = sActivityStack.lastElement();
        return (Activity) activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        IActivity activity = null;
        for (IActivity aty : sActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return null == activity ? null : (Activity) activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        IActivity activity = sActivityStack.lastElement();
        finishActivity((Activity) activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (IActivity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     */
    public void finishOthersActivity(Class<?> cls) {
        for (IActivity activity : sActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private void finishAllActivity() {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                ((Activity) sActivityStack.get(i)).finish();
            }
        }
        sActivityStack.clear();
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
