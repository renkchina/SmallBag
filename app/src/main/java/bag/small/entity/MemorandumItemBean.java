package bag.small.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class MemorandumItemBean implements Parcelable {


    /**
     * kemu_id : 107
     * name : 社会
     * hasnew : false
     * {"publishdate":"2017-11-05","hasnew":true}
     */

    private String kemu_id;
    private String name;
    private String publishdate;
    private boolean hasnew;

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getKemu_id() {
        return kemu_id;
    }

    public void setKemu_id(String kemu_id) {
        this.kemu_id = kemu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasnew() {
        return hasnew;
    }

    public void setHasnew(boolean hasnew) {
        this.hasnew = hasnew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kemu_id);
        dest.writeString(this.name);
        dest.writeString(this.publishdate);
        dest.writeByte(this.hasnew ? (byte) 1 : (byte) 0);
    }

    public MemorandumItemBean() {
    }

    protected MemorandumItemBean(Parcel in) {
        this.kemu_id = in.readString();
        this.name = in.readString();
        this.publishdate = in.readString();
        this.hasnew = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MemorandumItemBean> CREATOR = new Parcelable.Creator<MemorandumItemBean>() {
        @Override
        public MemorandumItemBean createFromParcel(Parcel source) {
            return new MemorandumItemBean(source);
        }

        @Override
        public MemorandumItemBean[] newArray(int size) {
            return new MemorandumItemBean[size];
        }
    };
}
