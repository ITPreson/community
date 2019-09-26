package life.weiwang.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;


}
