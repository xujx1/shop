package com.shop.userManager.services;

import com.shop.userManager.bean.User;

import java.util.List;

public interface UserService {
    List<User> selectAll();

    User selectUserById(Integer id);

    void updateUserInfoById(User user);
}
