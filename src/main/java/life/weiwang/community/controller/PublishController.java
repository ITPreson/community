package life.weiwang.community.controller;

import life.weiwang.community.mapper.QuestionMapper;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.Question;
import life.weiwang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            HttpServletRequest request,
            Model model) {
        //用来回显到桌面上
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";

        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";

        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";

        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return publish();
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        questionMapper.create(question);





        return "redirect:/";
    }

}
