package life.weiwang.community.model;

import lombok.Data;

@Data

public class User {
    private Integer id;
    private String name;
    private String account_id;
    private String token;

    private Long gmt_Create;
    private Long gmt_Modified;
    private String avatarUrl;

}