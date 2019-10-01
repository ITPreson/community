package life.weiwang.community.controller;

import life.weiwang.community.dto.CommentDTO;
import life.weiwang.community.mapper.CommentMapper;
import life.weiwang.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @PostMapping(value = "comment")
    public String post(@RequestBody CommentDTO commentDTO){

        Comment comment = new Comment();
        comment.setCommentator(1);
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());

        commentMapper.insert(comment);

        return null;
    }
}
