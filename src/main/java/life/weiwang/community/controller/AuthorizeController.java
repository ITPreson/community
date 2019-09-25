package life.weiwang.community.controller;

import life.weiwang.community.dto.AccessTokenDTO;
import life.weiwang.community.dto.GithubUser;
import life.weiwang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;


    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("eb7d8d8fa230ed6e1a12");
        accessTokenDTO.setClient_secret("030b6d6de83e4860aaa1388f8818b1b9c815e35e");
        accessTokenDTO.setRedirect_uri("http://localhost:8886/callback");
        accessTokenDTO.setCode(code);

        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }

}
