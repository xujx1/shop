package com.shop.userManager.mapper;

import com.shop.userManager.bean.User;

import java.util.List;

public interface UserMapper {
    User selectUserById(Integer id);

    List<User> selectAll();

    void updateUserById(User user);
}
