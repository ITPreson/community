package life.weiwang.community.controller;

import life.weiwang.community.dto.CommentDTO;
import life.weiwang.community.dto.QuestionDTO;
import life.weiwang.community.service.CommentService;
import life.weiwang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Long id,
                           Model model) {

        QuestionDTO questionDTO = questionService.findById(id);

        List<CommentDTO> comments= commentService.listByQuestionId(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);

        //增加阅读数
        questionService.increaseView(id);
        return "question";
    }

}
