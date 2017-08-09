package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
public class MomentsBean {

    /**
     * id : 3
     * content : <p>测试日记<br/></p>
     * create_at : 2017-08-06 22:12:35
     * creater_id : 1
     * images : []
     * dianzan_count : 1
     * dianzan_list_names : ["刘二莽"]
     * can_dianzan : true
     * can_delete : false
     * repay : [{"review_id":"10","content":"123123123123123","create_at":"2017-08-09 01:18:24","title":"0.21","can_delete":false},{"review_id":"9","content":"123123","create_at":"2017-08-09 01:18:09","title":"0.21","can_delete":false},{"review_id":"8","content":"123213","create_at":"2017-08-09 01:15:02","title":"0.21","can_delete":false},{"review_id":"7","content":"123123","create_at":"2017-08-09 01:14:57","title":"0.21","can_delete":false},{"review_id":"4","content":"这是一条回复信息","create_at":"2017-08-09 01:13:43","title":"刘二莽回复刘二莽","can_delete":false},{"review_id":"3","content":"这是一条回复信息","create_at":"2017-08-09 01:13:14","title":"刘二莽","can_delete":false},{"review_id":"1","content":"这是一条回复信息","create_at":"2017-08-09 00:03:06","title":"刘二莽","can_delete":false}]
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
    private List<String> images;
    private List<String> dianzan_list_names;
    private List<RepayBean> repay;

    private String review;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
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
         * review_id : 10
         * content : 123123123123123
         * create_at : 2017-08-09 01:18:24
         * title : 0.21
         * can_delete : false
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