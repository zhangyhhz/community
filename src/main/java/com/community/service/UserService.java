package com.community.service;

import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            // 更新
            User updateUser = users.get(0);
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setLogin(user.getLogin());
            updateUser.setToken(user.getToken());
            UserExample example = userExample;
            example.createCriteria().andAccountIdEqualTo(users.get(0).getAccountId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
