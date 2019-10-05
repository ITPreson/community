package life.weiwang.community.controller;

import life.weiwang.community.dto.CommentDTO;
import life.weiwang.community.dto.ResponseDTO;
import life.weiwang.community.exception.CustomizeErrorCode;
import life.weiwang.community.model.Comment;
import life.weiwang.community.model.User;
import life.weiwang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {



    @Autowired
    CommentService commentService;

    //@ResponseBody可以把对象序列化为json返回给前端
    @ResponseBody
    @PostMapping(value = "/comment")
    //@RequestBody可以接受json格式的数据，把json反序列化放入CommentDTO
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
           return ResponseDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);

        }

        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());

        commentService.insert(comment);



        return ResponseDTO.okOf();
    }
}
