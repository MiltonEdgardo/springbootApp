package com.springboot.springbootApp.dao;

import com.springboot.springbootApp.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();


    void delete(Long id);

    void signInUsers(User user);

    User getUserByCredentials(User user);
}
