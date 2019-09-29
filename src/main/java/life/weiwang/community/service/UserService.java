package life.weiwang.community.service;

import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.User;
import life.weiwang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {

        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 0) {
            //为空，则插入
            // 更新的话就不需要创建时间了，所以这两句移到里面来
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println("走插入了");
            userMapper.insert(user);
        } else {
            //不为空，更新一下
            User dbuser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarurl(user.getAvatarurl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser, userExample);

        }
    }
}
