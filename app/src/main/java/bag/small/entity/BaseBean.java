package bag.small.entity;


/**
 * Created by Administrator on 2017/4/14.
 */

public class BaseBean<T> {
    private T data;
    private String message;
    private int state;

    public T getData() {
        return data;
    }

    public boolean HaveResult() {
        return state == 1;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getResult() {
        return data;
    }

    public void setResult(T data) {
        this.data = data;
    }

    public BaseBean() {
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "data=" + data +
                ", msg='" + message + '\'' +
                ", state=" + state +
                '}';
    }

}
