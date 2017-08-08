package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
public class EducationNoticeBean {

    /**
     * date : 2017-08-07
     * label : 2017-08-07
     * results : [{"id":9,"title":"maggie test2","is_readed":false,"create_at":"2017-08-07 01:51:37"},{"id":8,"title":"maggie test","is_readed":false,"create_at":"2017-08-07 01:14:38"}]
     */

    private String date;
    private String label;
    private List<ResultsBean> results;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id : 9
         * title : maggie test2
         * is_readed : false
         * create_at : 2017-08-07 01:51:37
         */

        private int id;
        private String title;
        private boolean is_readed;
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

        public boolean isIs_readed() {
            return is_readed;
        }

        public void setIs_readed(boolean is_readed) {
            this.is_readed = is_readed;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }
    }
}