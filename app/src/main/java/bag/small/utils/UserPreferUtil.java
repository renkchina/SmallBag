package bag.small.utils;


import bag.small.entity.LoginResult;

/**
 * Created by Administrator on 2017/5/31.
 */

public class UserPreferUtil {

    private static volatile UserPreferUtil userPreferUtil;
    private PreferenceUtil preferenceUtil;

    private UserPreferUtil() {
        preferenceUtil = new PreferenceUtil();
    }

    public static UserPreferUtil getInstanse() {
        if (userPreferUtil == null) {
            synchronized (UserPreferUtil.class) {
                if (userPreferUtil == null) {
                    userPreferUtil = new UserPreferUtil();
                }
            }
        }
        return userPreferUtil;
    }

    public void setUserInfomation(LoginResult.RoleBean loginBean) {
        setInfomation("name", loginBean.getName());
        setInfomation("role_id", loginBean.getRole_id());
        setInfomation("school_id", loginBean.getSchool_id());
        setInfomation("target_id", loginBean.getTarget_id());
        setInfomation("target_type", loginBean.getTarget_type());
        setInfomation("logo", loginBean.getLogo());
    }

    public void setUserHeadImage(String image) {
        setInfomation("logo", image);
    }

    public void setUseId(String id) {
        setInfomation("login_id", id);
    }

    public String getHeadImagePath() {
        return getInfomationS("logo");
    }

    public String getRoleId() {
        return getInfomationS("role_id");
    }

    public String getUserName() {
        return getInfomationS("name");
    }

    public String getSchoolId() {
        return getInfomationS("school_id");
    }

    public String getUserId() {
        return getInfomationS("login_id");
    }

    public String getPhone() {
        return getInfomationS("phone");
    }

    public void setPassword(String password) {
        setInfomation("password", password);
    }

    public void setPhone(String phone) {
        setInfomation("phone", phone);
    }

    public String getPassword() {
        return getInfomationS("password");
    }

    public String getTargetType() {
        return getInfomationS("target_type");
    }

    public boolean isTeacher() {
        if (getTargetType().equals("teacher")) {
            return true;
        } else {
            return false;
        }
    }

    private void setInfomation(String key, Object value) {
        preferenceUtil.put(key, value);
    }

    private String getInfomationS(String key) {
        return preferenceUtil.getString(key);
    }

    private int getInfomationI(String key) {
        return preferenceUtil.getInt(key);
    }
}
