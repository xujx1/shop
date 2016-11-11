package com.shop.userManager.services.impl;

import com.shop.userManager.bean.User;
import com.shop.userManager.mapper.UserMapper;
import com.shop.userManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectUserById(Integer id) {
        User user = new User();
        if (null != id) {
            user = userMapper.selectUserById(id);
        }
        return user;
    }

    public void updateUserInfoById(User user) {
        if (null != user && null != user.getId()) {
            userMapper.updateUserById(user);
        }
    }
}
