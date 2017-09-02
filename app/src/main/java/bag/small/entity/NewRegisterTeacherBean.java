package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public  class NewRegisterTeacherBean {

    /**
     * name : 刘老师
     * sex : 男
     * work_no : lect
     * level : 高级教师
     * email : 000000@qq.com
     * birth : 1988-10-26
     * school_name : 第三女子高中
     * is_banzhuren : 是(1年级1班)
     * kemuinfo : ["数学(1年级1班,4年级5班,3年级4班,2年级1班,1年级2班)"]
     */

    private String name;
    private String sex;
    private String work_no;
    private String level;
    private String email;
    private String birth;
    private String school_name;
    private String is_banzhuren;
    private List<String> kemuinfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getIs_banzhuren() {
        return is_banzhuren;
    }

    public void setIs_banzhuren(String is_banzhuren) {
        this.is_banzhuren = is_banzhuren;
    }

    public List<String> getKemuinfo() {
        return kemuinfo;
    }

    public void setKemuinfo(List<String> kemuinfo) {
        this.kemuinfo = kemuinfo;
    }
}
