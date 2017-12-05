package bag.small.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class GradeClass implements Parcelable {


    /**
     * kemu_id : 28
     * kemu_name : 英文
     * relate_banji : [{"nianji":"一","banci":"1","banjiid":"46"}
     * ,{"nianji":"一","banci":"2","banjiid":"339"},
     * {"nianji":"一","banci":"3","banjiid":"436"},
     * {"nianji":"三","banci":"1","banjiid":"48"}]
     */

    private String kemu_id;
    private String kemu_name;
    private List<RelateBanjiBean> relate_banji;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean check) {
        isChecked = check;
    }

    public String getKemuName() {
        return kemu_name;
    }

    public List<RelateBanjiBean> getRelate() {
        return relate_banji;
    }

    public void setRelate_banji(List<RelateBanjiBean> relate_banji) {
        this.relate_banji = relate_banji;
    }

    public String getKemu_id() {
        return kemu_id;
    }

    public void setKemu_id(String kemu_id) {
        this.kemu_id = kemu_id;
    }

    public String getKemu_name() {
        return kemu_name;
    }

    public void setKemu_name(String kemu_name) {
        this.kemu_name = kemu_name;
    }

    public List<RelateBanjiBean> getRelate_banji() {
        return relate_banji;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kemu_id);
        dest.writeString(this.kemu_name);
        dest.writeTypedList(this.relate_banji);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public GradeClass() {
    }

    protected GradeClass(Parcel in) {
        this.kemu_id = in.readString();
        this.kemu_name = in.readString();
        this.relate_banji = in.createTypedArrayList(RelateBanjiBean.CREATOR);
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GradeClass> CREATOR = new Parcelable.Creator<GradeClass>() {
        @Override
        public GradeClass createFromParcel(Parcel source) {
            return new GradeClass(source);
        }

        @Override
        public GradeClass[] newArray(int size) {
            return new GradeClass[size];
        }
    };
}
