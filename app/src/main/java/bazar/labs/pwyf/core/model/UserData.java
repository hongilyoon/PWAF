package bazar.labs.pwyf.core.model;

import lombok.Data;

/**
 * Created by hiyoon on 2017. 4. 9..
 */
@Data
public class UserData {
    private Long seq;
    private String id;
    private String name;
    private Long platform_seq;
    private Long region_seq;
    private String tag;

    public UserData() {
    }

    public UserData(String pId, String pName, Long pPlatformSeq, Long pRegionSeq, String pTag) {
        this.id = pId;
        this.name = pName;
        this.platform_seq = pPlatformSeq;
        this.region_seq = pRegionSeq;
        this.tag = pTag;
    }
}
