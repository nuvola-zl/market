package org.example.market.service;

import org.example.market.pojo.User;

public interface LoginService {
    //根据用户名查询用户
    User selectByUsername(String username);

    //注册用户
    void insert(User user);
}
