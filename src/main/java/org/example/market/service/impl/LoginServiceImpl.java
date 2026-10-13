package org.example.market.service.impl;

import jakarta.annotation.Resource;
import org.example.market.mapper.LoginMapper;
import org.example.market.pojo.User;
import org.example.market.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private LoginMapper loginMapper;

    //根据用户名查询用户
    @Override
    public User selectByUsername(String username) {
        return loginMapper.selectByUsername(username);
    }

    //注册用户
    @Override
    public void insert(User user) {
        loginMapper.insert(user);
    }
}
