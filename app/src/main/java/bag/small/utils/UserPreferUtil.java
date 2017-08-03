package bag.small.utils;


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

//    public void setUserInfomation(LoginBean loginBean) {
//        setInfomation("address", loginBean.getAddress());
//        setInfomation("birthday", loginBean.getBirthday());
//        setInfomation("height", loginBean.getHeight());
//        setInfomation("image", loginBean.getHeadImage());
//        setInfomation("interest", loginBean.getInterest());
//        setInfomation("job", loginBean.getJob());
//        setInfomation("username", loginBean.getUserName());
//        setInfomation("background", loginBean.getBackground());
//
//        setInfomation("phone", loginBean.getPhone());
//        setInfomation("role", loginBean.getRole());
//        setInfomation("user_id", loginBean.getUid());
//        setInfomation("salary", loginBean.getSalary());
//        setInfomation("sex", loginBean.getSex());
//        setInfomation("signature", loginBean.getSignature());
//        setInfomation("weight", loginBean.getWeight());
//        setInfomation("constellation", loginBean.getXingzuo());
//    }

    public void setUserPassword(String password) {
        setInfomation("password", password);
    }

    public void setUserHeadImage(String image) {
        setInfomation("image", image);
    }

    public void setUseId(String id) {
        setInfomation("login_id", id);
    }

    public void setUserBackgroundImage(String image) {
        setInfomation("background", image);
    }

    public String getHeadImagePath() {
        return getInfomationS("image");
    }

    public String getBackgroundImagePath() {
        return getInfomationS("background");
    }

    public String getUserName() {
        return getInfomationS("username");
    }

    public String getPassword() {
        return getInfomationS("password");
    }

    public String getUserId() {
        return getInfomationS("login_id");
    }

    public String getPhone() {
        return getInfomationS("phone");
    }

    public String getSingnature() {
        return getInfomationS("signature");
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
