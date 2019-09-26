package life.weiwang.community.controller;

import life.weiwang.community.dto.QuestionDTO;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.User;
import life.weiwang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;

                }

            }
        /**
         * 这里需要查询的QuestionDTO是封装了question和user模型的模型，所以需要一个中间
         * service把这两个查询包装起来
         */
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";

    }


}
