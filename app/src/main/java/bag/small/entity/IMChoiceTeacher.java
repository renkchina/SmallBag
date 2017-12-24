package bag.small.entity;

/**
 * Created by Administrator on 2017/12/18.
 */
public class IMChoiceTeacher {


    private boolean isChecked;
    /**
     * logo : http://api.vshangsoft.com/images/all/20170928/1506578145.jpg
     * item_id : 112
     * uiid : bb9e7cb0-dfff-11e7-bc1e-ad292b813422
     * name : 燕玲
     * im_login_user : 3fc4410bb8771115e03015d948e55086
     * type : teacher
     */

    private String logo;
    private String item_id;
    private String uiid;
    private String name;
    private String im_login_user;
    private String type;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIm_login_user() {
        return im_login_user;
    }

    public void setIm_login_user(String im_login_user) {
        this.im_login_user = im_login_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IMChoiceTeacher{" +
                "isChecked=" + isChecked +
                ", logo='" + logo + '\'' +
                ", item_id='" + item_id + '\'' +
                ", uiid='" + uiid + '\'' +
                ", name='" + name + '\'' +
                ", im_login_user='" + im_login_user + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}