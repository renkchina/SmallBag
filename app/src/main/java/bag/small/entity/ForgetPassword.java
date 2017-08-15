package bag.small.entity;

/**
 * Created by Administrator on 2017/8/15.
 */

public class ForgetPassword {
    private String login_id;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    @Override
    public String toString() {
        return "ForgetPassword{" +
                "login_id='" + login_id + '\'' +
                '}';
    }
}
