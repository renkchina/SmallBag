package bag.small.entity;

/**
 * Created by Administrator on 2017/12/18.
 */

public class IMLoginEntity {

    /**
     * username : 906cfc5128ac5469e0f8f7fd957d4d30
     * pwd : 9c6c1121d994eb442347e0435df122a4
     */

    private String username;
    private String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "IMLoginEntity{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
