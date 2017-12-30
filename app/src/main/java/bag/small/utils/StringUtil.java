package bag.small.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return date.getTime();
        }
    }

    public static Date stringToDate(String strTime, String formatType) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void setTextView(TextView tv, String content) {
        if (tv == null) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            tv.setText("");
        } else {
            tv.setText(content);
        }
    }

    public static void setTextViewGone(TextView tv, String content) {
        if (tv == null) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(content);
        }
    }

    public static void setTextView(TextView tv, String content, String defaults) {
        if (tv == null) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            tv.setText(defaults);
        } else {
            tv.setText(content);
        }
    }

    public static String getSystemTimes() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 去除小数点
     *
     * @param value g
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
