package life.weiwang.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    //自我描述
    private String bio;
    //头像地址
    private String avatar_url;


}
