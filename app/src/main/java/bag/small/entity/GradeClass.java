package bag.small.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class GradeClass {


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

    public String getKemuId() {
        return kemu_id;
    }

    public void setKemu_id(String kemu_id) {
        this.kemu_id = kemu_id;
    }

    public String getKemuName() {
        return kemu_name;
    }

    public void setKemu_name(String kemu_name) {
        this.kemu_name = kemu_name;
    }

    public List<RelateBanjiBean> getRelate() {
        return relate_banji;
    }

    public void setRelate_banji(List<RelateBanjiBean> relate_banji) {
        this.relate_banji = relate_banji;
    }


}
