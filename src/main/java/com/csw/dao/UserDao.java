package com.csw.dao;

import com.csw.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserDao {

    public void addUser(@Param("user") User user);

    public User selectUserByUsername(@Param("username") String username);

    public void changeUserStatus(@Param("userId") String userId);
}
