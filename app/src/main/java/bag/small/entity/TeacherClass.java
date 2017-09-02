package bag.small.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class TeacherClass {
    /**
     * date : 2017-09-01
     * role : teacher
     * class : [{"name":"足球","class_time":"每周3 下午4点-5点","class_room":"第二教学楼301室","students":[{"name":"谭琪升","xuehao":"10002","banji":"6年级6班"},{"name":"谭淇生","xuehao":"10002","banji":"5年级5班"}]},{"name":"UI制作","class_time":"上午10:00 ～ 上午 11:00 每周五","class_room":"201","students":[{"name":"杨齐齐","xuehao":"10006","banji":"1年级1班"}]}]
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
         * name : 足球
         * class_time : 每周3 下午4点-5点
         * class_room : 第二教学楼301室
         * students : [{"name":"谭琪升","xuehao":"10002","banji":"6年级6班"},{"name":"谭淇生","xuehao":"10002","banji":"5年级5班"}]
         */

        private String id;
        private String name;
        private String class_time;
        private String class_room;
        private List<StudentsBean> students;

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

        public List<StudentsBean> getStudents() {
            return students;
        }

        public void setStudents(List<StudentsBean> students) {
            this.students = students;
        }

        public static class StudentsBean {
            /**
             * name : 谭琪升
             * xuehao : 10002
             * banji : 6年级6班
             */

            private String name;
            private String xuehao;
            private String banji;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getXuehao() {
                return xuehao;
            }

            public void setXuehao(String xuehao) {
                this.xuehao = xuehao;
            }

            public String getBanji() {
                return banji;
            }

            public void setBanji(String banji) {
                this.banji = banji;
            }
        }
    }
}