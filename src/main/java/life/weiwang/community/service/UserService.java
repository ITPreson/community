package life.weiwang.community.service;

import life.weiwang.community.mapper.UserMapper;
import life.weiwang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {

        User dbuser = userMapper.findByAccountId(user.getAccount_id());

        if(dbuser == null){
            //为空，则插入
            // 更新的话就不需要创建时间了，所以这两句移到里面来
            user.setGmt_Create(System.currentTimeMillis());
            user.setGmt_Modified(user.getGmt_Create());
            System.out.println("走插入了");
            userMapper.insert(user);
        }else {
            //不为空，更新一下
            dbuser.setGmt_Modified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
        }
    }
}
