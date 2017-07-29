package bag.small.entity;

/**
 * Created by Administrator on 2017/7/21.
 */

public class LoginBean {
    private String login_id;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "login_id='" + login_id + '\'' +
                '}';
    }
}
