package bag.small.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class InterestTeacherClassTeach {

    /**
     * date : 2017-09-02
     * role : teacher
     * class : [{"id":"12","name":"足球","class_time":"每周3 下午4点-5点","class_room":"第二教学楼301室"},{"id":"13","name":"UI制作","class_time":"上午10:00 ～ 上午 11:00 每周五","class_room":"201"}]
     */

    private String date;
    private String role;
    @SerializedName("class")
    private List<ClassBean> classX;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ClassBean> getClassX() {
        return classX;
    }

    public void setClassX(List<ClassBean> classX) {
        this.classX = classX;
    }

    public static class ClassBean {
        /**
         * id : 12
         * name : 足球
         * class_time : 每周3 下午4点-5点
         * class_room : 第二教学楼301室
         */

        private String id;
        private String name;
        private String class_time;
        private String class_room;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClass_time() {
            return class_time;
        }

        public void setClass_time(String class_time) {
            this.class_time = class_time;
        }

        public String getClass_room() {
            return class_room;
        }

        public void setClass_room(String class_room) {
            this.class_room = class_room;
        }
    }
}
