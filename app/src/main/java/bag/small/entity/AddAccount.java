package bag.small.entity;

/**
 * Created by Administrator on 2017/9/5.
 */

public class AddAccount {

    /**
     * login_id : 50
     * type : teacher
     * school_id : 47
     * role_id : 187
     */

    private String login_id;
    private String type;
    private String school_id;
    private String role_id;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
