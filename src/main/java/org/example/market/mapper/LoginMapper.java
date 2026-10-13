package org.example.market.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.market.pojo.User;

@Mapper
public interface LoginMapper  {
    //根据用户名查询用户
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    //注册用户
    @Insert("insert into user (username, password, role) values (#{username}, #{password}, #{role})")
    void insert(User user);
}
