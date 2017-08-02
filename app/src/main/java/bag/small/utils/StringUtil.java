package bag.small.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/5/10.
 */

public class StringUtil {

    private static SimpleDateFormat sd = (SimpleDateFormat) DateFormat
            .getDateInstance();// 获取时间格式对象

    private StringUtil() {
    }

    public static String EditGetString(TextView editText) {
        if (editText != null) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean equals(String str1, String str2) {
        return (str1 == null && str2 == null) ||
                (isNotEmpty(str1) && str1.equals(str2));
    }

    public static boolean isWebSite(String string) {
        return isNotEmpty(string) && string.matches(Patterns.WEB_URL.pattern());
    }

    public static boolean isPhone(String string) {
        return isNotEmpty(string) && string.matches(Patterns.PHONE.pattern());
    }

    public static boolean isEmail(String string) {
        return isNotEmpty(string) && string.matches(Patterns.EMAIL_ADDRESS.pattern());
    }


    /**
     * "yyyy-MM-dd HH:mm"
     */
    public static String getTimeFormatYMDHM(Object time) {
        sd.applyPattern("yyyy-MM-dd HH:mm");
        return sd.format(time);
    }

    /**
     * "yyyy-MM-dd"
     */
    public static String getTimeFormatYMD(Object time) {
        sd.applyPattern("yyyy-MM-dd");
        return sd.format(time);
    }

    /**
     * "将时间戳格式化yyyy年MM年dd"
     */
    public static String getTimeFormatYMDZn(Long time) {
        sd.applyPattern("yyyy年MM月dd日");
        return sd.format(time);
    }

    public static String getTimeFormatMS(Long time) {
        sd.applyPattern("mm分ss秒");
        return sd.format(time);
    }

    public static void setTextView(TextView tv, String content) {
        if (TextUtils.isEmpty(content)) {
            tv.setText("未设置");
        } else {
            tv.setText(content);
        }
    }
    public static void setTextView(TextView tv, String content,String defaults) {
        if (TextUtils.isEmpty(content)) {
            tv.setText(defaults);
        } else {
            tv.setText(content);
        }
    }

    /**
     * 去除小数点
     *
     * @param value
     * @return
     */
    private static String changeDone(double value) {
        String s;
        try {
            s = Double.toString(value);
            if (s.indexOf(".") > 0) {
                s = s.replaceAll("0+?$", "");
                s = s.replaceAll("[.]$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            s = "0";
        }
        return s;
    }
}
