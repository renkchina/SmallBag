package bag.small.utils;

import android.content.Context;
import android.content.SharedPreferences;

import bag.small.app.MyApplication;


/**
 * Created by Administrator on 2017/5/11.
 */

class PreferenceUtil {

    private SharedPreferences sp;

    PreferenceUtil() {
        init();
    }

    /**
     * 获取偏好执行器
     */
    private void init() {
        sp = MyApplication.getContext().getSharedPreferences(MyApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
    }

    /**
     * 添加偏好内容：异步，不会引起长时间等待
     */
    public void put(String key, Object value) {
        if (StringUtil.isEmpty(key) || value == null) return;
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String ||
                value instanceof StringBuilder ||
                value instanceof StringBuffer) {
            editor.putString(key, String.valueOf(value));
            editor.apply();
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
            editor.apply();
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
            editor.apply();
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
            editor.apply();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
            editor.apply();
        }
    }

    /**
     * 添加偏好内容：同步，可能会引起长时间等待
     *
     * @return 添加是否成功
     */
    public boolean putSync(String key, Object value) {
        if (StringUtil.isEmpty(key) || value == null)
            return false;
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String ||
                value instanceof StringBuilder ||
                value instanceof StringBuffer) {
            editor.putString(key, String.valueOf(value));
            return editor.commit();
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
            return editor.commit();
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
            return editor.commit();
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
            return editor.commit();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
            return editor.commit();
        }
        return false;
    }

    /**
     * 获取字符串
     */
    String getString(String key) {
        return getString(key, "");
    }

    /**
     * 获取字符串
     */
    private String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * 获取整型
     */
    int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * 获取整型
     */
    private int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * 获取长整型
     */
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    /**
     * 获取长整型
     */
    private long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * 获取浮点型
     */
    public float getFloat(String key) {
        return getFloat(key, 0F);
    }

    /**
     * 获取浮点型
     */
    private float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 获取布尔型
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 获取布尔型
     */
    private boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 移除偏好内容：异步，不会引起长时间等待
     */
    public void remove(String key) {
        if (StringUtil.isEmpty(key)) return;
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 移除偏好内容：同步，可能会引起长时间等待
     *
     * @param key
     * @return
     */
    public boolean removeSync(String key) {
        if (StringUtil.isEmpty(key)) return false;
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    public void clear() {
        sp.edit().clear().apply();
    }

}
