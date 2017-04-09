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
}
