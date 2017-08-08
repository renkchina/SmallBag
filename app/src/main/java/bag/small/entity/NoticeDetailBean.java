package bag.small.entity;

/**
 * Created by Administrator on 2017/8/8.
 */

public class NoticeDetailBean {

    /**
     * notice : {"id":7,"title":"20170807放假通知","image":"http://api.vshangsoft.com/images/testpic2.jpg","content":"<p>因为学校电源整修。20170807全天不上课。<\/p><p>请知晓。<br/><\/p><br/>","create_at":"2017-08-06 21:24:42"}
     */

    private NoticeBean notice;

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public static class NoticeBean {
        /**
         * id : 7
         * title : 20170807放假通知
         * image : http://api.vshangsoft.com/images/testpic2.jpg
         * content : <p>因为学校电源整修。20170807全天不上课。</p><p>请知晓。<br/></p><br/>
         * create_at : 2017-08-06 21:24:42
         */

        private int id;
        private String title;
        private String image;
        private String content;
        private String create_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
    }
}
