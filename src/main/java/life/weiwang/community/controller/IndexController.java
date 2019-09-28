package life.weiwang.community.controller;

import life.weiwang.community.dto.PaginationDTO;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "3") Integer size) {

        /**
         * 这里需要查询的QuestionDTO是封装了question和user模型的模型，所以需要一个中间
         * service把这两个查询包装起来
         */
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";

    }


}
