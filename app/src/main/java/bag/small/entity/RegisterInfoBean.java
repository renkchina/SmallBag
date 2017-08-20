package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RegisterInfoBean {

    private List<SchoolArea> school;

    public List<SchoolArea> getSchool() {
        return school;
    }

    public void setSchool(List<SchoolArea> school) {
        this.school = school;
    }

    public static class SchoolArea {
        /**
         * id : 3
         * name : 长宁
         * school : [{"id":"46","name":"第三女子高中","base":{"jie":[{"value":2017,"name":"2017届","nianji":1,"nianji_name":"1年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2016,"name":"2016届","nianji":2,"nianji_name":"2年级","banji":[{"value":"3","text":"3班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2015,"name":"2015届","nianji":3,"nianji_name":"3年级","banji":[{"value":"4","text":"4班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2014,"name":"2014届","nianji":4,"nianji_name":"4年级","banji":[{"value":"5","text":"5班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2013,"name":"2013届","nianji":5,"nianji_name":"5年级","banji":[{"value":"1","text":"1班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2012,"name":"2012届","nianji":6,"nianji_name":"6年级","banji":[{"value":"1","text":"1班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]}]}}]
         */

        private String id;
        private String name;
        private List<SchoolArea.SchoolBean> school;

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

        public List<SchoolArea.SchoolBean> getSchool() {
            return school;
        }

        public void setSchool(List<SchoolArea.SchoolBean> school) {
            this.school = school;
        }

        public static class SchoolBean {
            /**
             * id : 46
             * name : 第三女子高中
             * base : {"jie":[{"value":2017,"name":"2017届","nianji":1,"nianji_name":"1年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2016,"name":"2016届","nianji":2,"nianji_name":"2年级","banji":[{"value":"3","text":"3班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2015,"name":"2015届","nianji":3,"nianji_name":"3年级","banji":[{"value":"4","text":"4班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2014,"name":"2014届","nianji":4,"nianji_name":"4年级","banji":[{"value":"5","text":"5班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2013,"name":"2013届","nianji":5,"nianji_name":"5年级","banji":[{"value":"1","text":"1班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]},{"value":2012,"name":"2012届","nianji":6,"nianji_name":"6年级","banji":[{"value":"1","text":"1班"}],"keche":[{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]}]}
             */

            private String id;
            private String name;
            private SchoolArea.SchoolBean.BaseBean base;

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

            public SchoolArea.SchoolBean.BaseBean getBase() {
                return base;
            }

            public void setBase(SchoolArea.SchoolBean.BaseBean base) {
                this.base = base;
            }

            public static class BaseBean {
                private List<SchoolArea.SchoolBean.BaseBean.JieBean> jie;

                public List<SchoolArea.SchoolBean.BaseBean.JieBean> getJie() {
                    return jie;
                }

                public void setJie(List<SchoolArea.SchoolBean.BaseBean.JieBean> jie) {
                    this.jie = jie;
                }

                public static class JieBean {
                    /**
                     * value : 2017
                     * name : 2017届
                     * nianji : 1
                     * nianji_name : 1年级
                     * banji : [{"value":"1","text":"1班"},{"value":"2","text":"2班"}]
                     * keche : [{"value":"19","text":"语文"},{"value":"20","text":"数学"},{"value":"21","text":"英语"},{"value":"22","text":"体育"},{"value":"23","text":"音乐"},{"value":"24","text":"美术"},{"value":"25","text":"科学"}]
                     */

                    private int value;
                    private String name;
                    private int nianji;
                    private String nianji_name;
                    private List<SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean> banji;
                    private List<SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean> keche;

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getNianji() {
                        return nianji;
                    }

                    public void setNianji(int nianji) {
                        this.nianji = nianji;
                    }

                    public String getNianji_name() {
                        return nianji_name;
                    }

                    public void setNianji_name(String nianji_name) {
                        this.nianji_name = nianji_name;
                    }

                    public List<SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean> getBanji() {
                        return banji;
                    }

                    public void setBanji(List<SchoolArea.SchoolBean.BaseBean.JieBean.BanjiBean> banji) {
                        this.banji = banji;
                    }

                    public List<SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean> getKeche() {
                        return keche;
                    }

                    public void setKeche(List<SchoolArea.SchoolBean.BaseBean.JieBean.KecheBean> keche) {
                        this.keche = keche;
                    }

                    public static class BanjiBean {
                        /**
                         * value : 1
                         * text : 1班
                         */

                        private String value;
                        private String text;

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }

                        public String getText() {
                            return text;
                        }

                        public void setText(String text) {
                            this.text = text;
                        }
                    }

                    public static class KecheBean {
                        /**
                         * value : 19
                         * text : 语文
                         */

                        private String value;
                        private String text;

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }

                        public String getText() {
                            return text;
                        }

                        public void setText(String text) {
                            this.text = text;
                        }
                    }
                }
            }
        }
    }
}
