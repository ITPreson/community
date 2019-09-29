package life.weiwang.community.controller;

import life.weiwang.community.dto.AccessTokenDTO;
import life.weiwang.community.dto.GithubUser;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.User;
import life.weiwang.community.provider.GitHubProvider;
import life.weiwang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.url}")
    private String redirect_url;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state,
                           //HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_url(redirect_url);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatarurl(githubUser.getAvatar_url());
            System.out.println(user.getAccountId());
            userService.createOrUpdate(user);
            //写入cookie
            response.addCookie(new Cookie("token",token));


            /*初始的做法：
                //登录成功，写入session，自动分配cookie
                request.getSession().setAttribute("githubUser",githubUser);*/

            return "redirect:/";

        }else {
            //登录失败    redirect前缀：重定向。后面要跟全路径
            return "redirect:/";
        }


    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
