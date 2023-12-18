package com.itheima.service.impl;

import com.itheima.dao.UserMapper;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*用户接口实现类*/
@Service
public class UserServiceImpl implements UserService {
    //注入UserMapper对象
    @Autowired
    private UserMapper userMapper;

    //通过User的用户账号和用户密码进行查询
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}
