package bag.small.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class TeacherClass {


    /**
     * date : 2017-08-10
     * role : teacher
     * class : [{"name":"手工航模","class_time":"每周4 下午4点-5点","class_room":"第一教学楼301室","students":[{"name":"刘二莽","xuehao":"2017080614","banji":"1年级1班"}]}]
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
         * name : 手工航模
         * class_time : 每周4 下午4点-5点
         * class_room : 第一教学楼301室
         * students : [{"name":"刘二莽","xuehao":"2017080614","banji":"1年级1班"}]
         */

        private String name;
        private String class_time;
        private String class_room;
        private List<StudentsBean> students;

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
             * name : 刘二莽
             * xuehao : 2017080614
             * banji : 1年级1班
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