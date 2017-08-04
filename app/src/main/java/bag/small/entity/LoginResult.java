package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class LoginResult {

    /**
     * login_id : 21
     * role : [{"role_id":"12","name":"阿三","target_type":"teacher","target_id":"23","school_id":"44","logo":"http://api.vshangsoft.com/images/teacher/1501839674tea_logo"}]
     */

    private String login_id;
    private List<RoleBean> role;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public List<RoleBean> getRole() {
        return role;
    }

    public void setRole(List<RoleBean> role) {
        this.role = role;
    }

    public static class RoleBean {
        /**
         * role_id : 12
         * name : 阿三
         * target_type : teacher
         * target_id : 23
         * school_id : 44
         * logo : http://api.vshangsoft.com/images/teacher/1501839674tea_logo
         */

        private String role_id;
        private String name;
        private String target_type;
        private String target_id;
        private String school_id;
        private String logo;

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTarget_type() {
            return target_type;
        }

        public void setTarget_type(String target_type) {
            this.target_type = target_type;
        }

        public String getTarget_id() {
            return target_id;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        @Override
        public String toString() {
            return "RoleBean{" +
                    "role_id='" + role_id + '\'' +
                    ", name='" + name + '\'' +
                    ", target_type='" + target_type + '\'' +
                    ", target_id='" + target_id + '\'' +
                    ", school_id='" + school_id + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "login_id='" + login_id + '\'' +
                ", role=" + role +
                '}';
    }
}
