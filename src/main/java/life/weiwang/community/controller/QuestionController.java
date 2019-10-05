package life.weiwang.community.controller;

import life.weiwang.community.dto.QuestionDTO;
import life.weiwang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Long id,
                           Model model) {

        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("question", questionDTO);
        //增加阅读数
        questionService.increaseView(id);
        return "question";
    }

}
