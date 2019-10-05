package life.weiwang.community.service;

import life.weiwang.community.dto.CommentDTO;
import life.weiwang.community.enums.CommentTypeEnum;
import life.weiwang.community.exception.CustomizeErrorCode;
import life.weiwang.community.exception.CustomizeException;
import life.weiwang.community.mapper.CommentMapper;
import life.weiwang.community.mapper.QuestionMapper;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    UserMapper userMapper;

    //事务，里面的方法要么全成功，要么全失败
    @Transactional
    public boolean insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_WRONG);

        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            return true;
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            commentService.increaseCommentCount(comment);
            return true;

        }


    }

    public void increaseCommentCount(Comment comment) {
        Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
        Integer commentCount = question.getCommentCount();
        if (commentCount == null) {
            commentCount = 0;
        }
        Question updateQuestion = new Question();
        updateQuestion.setCommentCount(commentCount + 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(comment.getParentId());

        questionMapper.updateByExampleSelective(updateQuestion, example);
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType())
        ;
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments == null) {
            return new ArrayList<>();

        }
        //java8语法 用lamda获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人，并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds)
        ;
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转化comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
