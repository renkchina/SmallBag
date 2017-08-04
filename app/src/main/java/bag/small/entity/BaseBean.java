package bag.small.entity;


/**
 * Created by Administrator on 2017/4/14.
 */

public class BaseBean<T> {
    private T data;
    private String msg;
    private int code;

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public BaseBean() {
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
