package bag.small.entity;

/**
 * Created by Administrator on 2017/7/23.
 */
public class ConnectionBinder {
    private int id;
    private int resImage;
    private String title;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getResImage() {
        return resImage;
    }

    public void setResImage(int resImage) {
        this.resImage = resImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AccountDrawerBinder{" +
                "id=" + id +
                ", resImage=" + resImage +
                ", title='" + title + '\'' +
                ", count=" + count +
                '}';
    }
}