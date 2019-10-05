package life.weiwang.community.service;

import life.weiwang.community.enums.CommentTypeEnum;
import life.weiwang.community.exception.CustomizeErrorCode;
import life.weiwang.community.exception.CustomizeException;
import life.weiwang.community.mapper.CommentMapper;
import life.weiwang.community.mapper.QuestionMapper;
import life.weiwang.community.model.Comment;
import life.weiwang.community.model.Question;
import life.weiwang.community.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentService commentService;

    //事物，里面的方法要么全成功，要么全失败
    @Transactional
    public boolean insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_WRONG);

        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            return true;
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
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
        if (commentCount == null){
            commentCount = 0;
        }
        Question updateQuestion = new Question();
        updateQuestion.setCommentCount(commentCount+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(comment.getParentId());

        questionMapper.updateByExampleSelective(updateQuestion, example);
    }
}
