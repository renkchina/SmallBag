package bag.small.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/29.
 */
public  class RelateBanjiBean implements Parcelable {
    /**
     * nianji : 一
     * banci : 1
     * banjiid : 46
     */

    private String nianji;
    private String banci;
    private String banjiid;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getNianji() {
        return nianji;
    }

    public void setNianji(String nianji) {
        this.nianji = nianji;
    }

    public String getBanci() {
        return banci;
    }

    public void setBanci(String banci) {
        this.banci = banci;
    }

    public String getBanjiid() {
        return banjiid;
    }

    public void setBanjiid(String banjiid) {
        this.banjiid = banjiid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nianji);
        dest.writeString(this.banci);
        dest.writeString(this.banjiid);
    }

    public RelateBanjiBean() {
    }

    private RelateBanjiBean(Parcel in) {
        this.nianji = in.readString();
        this.banci = in.readString();
        this.banjiid = in.readString();
    }

    public static final Parcelable.Creator<RelateBanjiBean> CREATOR = new Parcelable.Creator<RelateBanjiBean>() {
        @Override
        public RelateBanjiBean createFromParcel(Parcel source) {
            return new RelateBanjiBean(source);
        }

        @Override
        public RelateBanjiBean[] newArray(int size) {
            return new RelateBanjiBean[size];
        }
    };
}