package bag.small.provider;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubject {
        String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TeachSubject{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
