package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RegisterInfoBean {

    private List<SchoolBeanX> school;

    public List<SchoolBeanX> getSchool() {
        return school;
    }

    public void setSchool(List<SchoolBeanX> school) {
        this.school = school;
    }

    public static class SchoolBeanX {
        /**
         * id : 3
         * name : 长宁
         * school : {"id":"44","name":"测试学校","base":{"jie":[{"value":2017,"name":"2017届","nianji":1,"nianji_name":"1年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2016,"name":"2016届","nianji":2,"nianji_name":"2年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2015,"name":"2015届","nianji":3,"nianji_name":"3年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2014,"name":"2014届","nianji":4,"nianji_name":"4年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2013,"name":"2013届","nianji":5,"nianji_name":"5年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2012,"name":"2012届","nianji":6,"nianji_name":"6年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]}]}}
         */

        private String id;
        private String name;
        private SchoolBean school;

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

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public static class SchoolBean {
            /**
             * id : 44
             * name : 测试学校
             * base : {"jie":[{"value":2017,"name":"2017届","nianji":1,"nianji_name":"1年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2016,"name":"2016届","nianji":2,"nianji_name":"2年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2015,"name":"2015届","nianji":3,"nianji_name":"3年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2014,"name":"2014届","nianji":4,"nianji_name":"4年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2013,"name":"2013届","nianji":5,"nianji_name":"5年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]},{"value":2012,"name":"2012届","nianji":6,"nianji_name":"6年级","banji":[{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}],"keche":[{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]}]}
             */

            private String id;
            private String name;
            private BaseBean base;

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

            public BaseBean getBase() {
                return base;
            }

            public void setBase(BaseBean base) {
                this.base = base;
            }

            public static class BaseBean {
                private List<JieBean> jie;

                public List<JieBean> getJie() {
                    return jie;
                }

                public void setJie(List<JieBean> jie) {
                    this.jie = jie;
                }

                public static class JieBean {
                    /**
                     * value : 2017
                     * name : 2017届
                     * nianji : 1
                     * nianji_name : 1年级
                     * banji : [{"value":"1","text":"1班"},{"value":"2","text":"2班"},{"value":"3","text":"3班"}]
                     * keche : [{"value":"8","text":"语文"},{"value":"9","text":"数学"},{"value":"10","text":"外语"}]
                     */

                    private int value;
                    private String name;
                    private int nianji;
                    private String nianji_name;
                    private List<BanjiBean> banji;
                    private List<KecheBean> keche;

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

                    public List<BanjiBean> getBanji() {
                        return banji;
                    }

                    public void setBanji(List<BanjiBean> banji) {
                        this.banji = banji;
                    }

                    public List<KecheBean> getKeche() {
                        return keche;
                    }

                    public void setKeche(List<KecheBean> keche) {
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
                         * value : 8
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

                    @Override
                    public String toString() {
                        return "JieBean{" +
                                "value=" + value +
                                ", name='" + name + '\'' +
                                ", nianji=" + nianji +
                                ", nianji_name='" + nianji_name + '\'' +
                                ", banji=" + banji +
                                ", keche=" + keche +
                                '}';
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "RegisterInfoBean{" +
                "school=" + school +
                '}';
    }
}
