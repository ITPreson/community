package life.weiwang.community.service;

import life.weiwang.community.dto.QuestionDTO;
import life.weiwang.community.mapper.QuestionMapper;
import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.Question;
import life.weiwang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> list = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //这是springboot内置的一个工具类，可以把属性快速复制到另一个model
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        return questionDTOList;
    }
}
