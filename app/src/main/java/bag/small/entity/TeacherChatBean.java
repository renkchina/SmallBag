package bag.small.entity;

/**
 * Created by Administrator on 2017/12/26.
 */

public class TeacherChatBean {

    /**
     * message :
     * message_type : image
     * file_link : https://a1.easemob.com/1148171022178006/im/chatfiles/63272180-e96f-11e7-ad16-136f877511bb
     * logo : http://api.vshangsoft.com/images/all/20171222/1513875857.jpg
     * user_name : 毛老师
     * create_at : 2017-12-25 20:30:51
     * file_name : image
     */


    private String message;
    private String message_type;
    private String file_link;
    private String logo;
    private String user_name;
    private String create_at;
    private String file_name;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getFile_link() {
        return file_link;
    }

    public void setFile_link(String file_link) {
        this.file_link = file_link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
