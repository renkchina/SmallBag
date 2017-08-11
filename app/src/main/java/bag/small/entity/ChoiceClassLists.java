package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public class ChoiceClassLists {

    /**
     * date : 2017-08-10
     * role : student
     * can_xuan_ke : true
     * xuanke : {"first":{"course_id":"1","course_name":"手工航模"},"secend":{"course_id":"2","course_name":"绘画"},"third":{"course_id":"3","course_name":"绘画"}}
     * kechen : [{"id":"1","name":"手工航模","class_room":"第一教学楼301室","class_time":"每周4 下午4点-5点","teacher":"尚达达"},{"id":"2","name":"绘画","class_room":"教一501","class_time":"周三15:00-16:30","teacher":"Maggie"}]
     */

    private String date;
    private String role;
    private boolean can_xuan_ke;
    private XuankeBean xuanke;
    private List<KechenBean> kechen;
    private boolean is_complete;
    private List<?> result;

    public boolean is_complete() {
        return is_complete;
    }

    public void setIs_complete(boolean is_complete) {
        this.is_complete = is_complete;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

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

    public boolean isCan_xuan_ke() {
        return can_xuan_ke;
    }

    public void setCan_xuan_ke(boolean can_xuan_ke) {
        this.can_xuan_ke = can_xuan_ke;
    }

    public XuankeBean getXuanke() {
        return xuanke;
    }

    public void setXuanke(XuankeBean xuanke) {
        this.xuanke = xuanke;
    }

    public List<KechenBean> getKechen() {
        return kechen;
    }

    public void setKechen(List<KechenBean> kechen) {
        this.kechen = kechen;
    }

    public static class XuankeBean {
        /**
         * first : {"course_id":"1","course_name":"手工航模"}
         * secend : {"course_id":"2","course_name":"绘画"}
         * third : {"course_id":"3","course_name":"绘画"}
         */

        private ChoiceBean first;
        private ChoiceBean secend;
        private ChoiceBean third;

        public ChoiceBean getFirst() {
            return first;
        }

        public void setFirst(ChoiceBean first) {
            this.first = first;
        }

        public ChoiceBean getSecend() {
            return secend;
        }

        public void setSecend(ChoiceBean secend) {
            this.secend = secend;
        }

        public ChoiceBean getThird() {
            return third;
        }

        public void setThird(ChoiceBean third) {
            this.third = third;
        }

        public static class ChoiceBean {
            /**
             * course_id : 3
             * course_name : 绘画
             */

            private String course_id;
            private String course_name;

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCourse_name() {
                return course_name;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }
        }
    }

    public static class KechenBean {
        /**
         * id : 1
         * name : 手工航模
         * class_room : 第一教学楼301室
         * class_time : 每周4 下午4点-5点
         * teacher : 尚达达
         */

        private String id;
        private String name;
        private String class_room;
        private String class_time;
        private String teacher;

        public KechenBean() {
        }

        public KechenBean(String id, String name, String class_room, String class_time, String teacher) {
            this.id = id;
            this.name = name;
            this.class_room = class_room;
            this.class_time = class_time;
            this.teacher = teacher;
        }

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

        public String getClass_room() {
            return class_room;
        }

        public void setClass_room(String class_room) {
            this.class_room = class_room;
        }

        public String getClass_time() {
            return class_time;
        }

        public void setClass_time(String class_time) {
            this.class_time = class_time;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }
    }
}