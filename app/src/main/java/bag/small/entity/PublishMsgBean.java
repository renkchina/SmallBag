package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class PublishMsgBean {
        /**
         * id : 1
         * content : <p>测试日记<br/></p>
         * create_at : 2017-08-06 22:10:12
         * creater_id : 1
         * images : []
         * dianzan_count : 4
         * dianzan_list_names : ["杨海龙","0.21","阿三","继刚"]
         * can_dianzan : false
         * can_delete : false
         * repay : [{"review_id":"78","content":"接口","create_at":"2017-08-11 21:30:29","title":"阿三回复阿三","can_delete":true},{"review_id":"75","content":"Just","create_at":"2017-08-11 12:10:06","title":"0.21回复阿三","can_delete":false},{"review_id":"74","content":"Yyyy","create_at":"2017-08-11 12:10:01","title":"0.21回复0.21","can_delete":false},{"review_id":"73","content":"111","create_at":"2017-08-11 12:08:52","title":"0.21回复0.21","can_delete":false},{"review_id":"72","content":"Ggg","create_at":"2017-08-11 11:50:23","title":"0.21回复0.21","can_delete":false},{"review_id":"71","content":"好吧，520","create_at":"2017-08-11 11:19:39","title":"阿三","can_delete":true},{"review_id":"69","content":"二女儿","create_at":"2017-08-11 03:17:54","title":"杨海龙回复0.21","can_delete":false},{"review_id":"68","content":"yuh","create_at":"2017-08-11 00:31:01","title":"Meng回复0.21","can_delete":false},{"review_id":"67","content":"111","create_at":"2017-08-11 00:20:07","title":"0.21回复0.21","can_delete":false},{"review_id":"66","content":"11111","create_at":"2017-08-11 00:19:53","title":"0.21回复阿三","can_delete":false},{"review_id":"65","content":"111111","create_at":"2017-08-11 00:19:49","title":"0.21回复阿三","can_delete":false},{"review_id":"64","content":"1111hhhhhh","create_at":"2017-08-11 00:19:40","title":"0.21回复0.21","can_delete":false},{"review_id":"62","content":"2312312","create_at":"2017-08-11 00:19:11","title":"0.21回复0.21","can_delete":false},{"review_id":"61","content":"gggg","create_at":"2017-08-11 00:19:01","title":"0.21回复0.21","can_delete":false},{"review_id":"60","content":"F","create_at":"2017-08-10 23:57:32","title":"0.21回复阿三","can_delete":false},{"review_id":"59","content":"馍馍","create_at":"2017-08-10 23:38:58","title":"0.21回复阿三","can_delete":false},{"review_id":"48","content":"你好","create_at":"2017-08-10 22:48:35","title":"阿三","can_delete":true},{"review_id":"33","content":"大哥","create_at":"2017-08-09 23:44:41","title":"阿三回复0.21","can_delete":true},{"review_id":"31","content":"这是一条回复信息","create_at":"2017-08-09 23:16:55","title":"阿三回复0.21","can_delete":true},{"review_id":"30","content":"这是一条回复信息","create_at":"2017-08-09 23:15:12","title":"阿三回复0.21","can_delete":true},{"review_id":"28","content":"这是一条回复信息","create_at":"2017-08-09 23:10:35","title":"阿三回复0.21","can_delete":true},{"review_id":"27","content":"这是一条回复信息","create_at":"2017-08-09 23:10:25","title":"阿三回复0.21","can_delete":true},{"review_id":"24","content":"大道","create_at":"2017-08-09 19:16:13","title":"阿三回复刘二莽","can_delete":true},{"review_id":"23","content":"该喝喝","create_at":"2017-08-09 17:25:00","title":"阿三回复刘二莽","can_delete":true},{"review_id":"22","content":"发个","create_at":"2017-08-09 17:20:00","title":"阿三回复0.21","can_delete":true},{"review_id":"21","content":"吃vv不不不扭扭捏捏","create_at":"2017-08-09 17:15:33","title":"阿三回复刘二莽","can_delete":true},{"review_id":"20","content":"吃vv不不不扭扭捏捏","create_at":"2017-08-09 17:14:53","title":"阿三回复刘二莽","can_delete":true},{"review_id":"14","content":"gggg","create_at":"2017-08-09 01:25:01","title":"0.21","can_delete":false},{"review_id":"13","content":"213123","create_at":"2017-08-09 01:23:58","title":"0.21","can_delete":false},{"review_id":"12","content":"123123","create_at":"2017-08-09 01:21:20","title":"0.21","can_delete":false}]
         * title : 测试学校
         * icon : http://api.vshangsoft.com/images/school/inco5.png
         */

        private String id;
        private String content;
        private String create_at;
        private String creater_id;
        private String dianzan_count;
        private boolean can_dianzan;
        private boolean can_delete;
        private String title;
        private String icon;
        private List<?> images;
        private List<String> dianzan_list_names;
        private List<RepayBean> repay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getCreater_id() {
            return creater_id;
        }

        public void setCreater_id(String creater_id) {
            this.creater_id = creater_id;
        }

        public String getDianzan_count() {
            return dianzan_count;
        }

        public void setDianzan_count(String dianzan_count) {
            this.dianzan_count = dianzan_count;
        }

        public boolean isCan_dianzan() {
            return can_dianzan;
        }

        public void setCan_dianzan(boolean can_dianzan) {
            this.can_dianzan = can_dianzan;
        }

        public boolean isCan_delete() {
            return can_delete;
        }

        public void setCan_delete(boolean can_delete) {
            this.can_delete = can_delete;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<?> getImages() {
            return images;
        }

        public void setImages(List<?> images) {
            this.images = images;
        }

        public List<String> getDianzan_list_names() {
            return dianzan_list_names;
        }

        public void setDianzan_list_names(List<String> dianzan_list_names) {
            this.dianzan_list_names = dianzan_list_names;
        }

        public List<RepayBean> getRepay() {
            return repay;
        }

        public void setRepay(List<RepayBean> repay) {
            this.repay = repay;
        }

        public static class RepayBean {
            /**
             * review_id : 78
             * content : 接口
             * create_at : 2017-08-11 21:30:29
             * title : 阿三回复阿三
             * can_delete : true
             */

            private String review_id;
            private String content;
            private String create_at;
            private String title;
            private boolean can_delete;

            public String getReview_id() {
                return review_id;
            }

            public void setReview_id(String review_id) {
                this.review_id = review_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public boolean isCan_delete() {
                return can_delete;
            }

            public void setCan_delete(boolean can_delete) {
                this.can_delete = can_delete;
            }
        }

}
