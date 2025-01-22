package com.springboot.springbootApp.controllers;

import com.lambdaworks.crypto.SCryptUtil;
import com.springboot.springbootApp.dao.UserDao;
import com.springboot.springbootApp.models.User;
import com.springboot.springbootApp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader(value="Authorization") String token) {

        if (!validateToken(token)) {
            return null;
        }
        return userDao.getUsers();
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void postUsers(@RequestBody User user) {

        String hash = SCryptUtil.scrypt(user.getPassword(), 16, 16, 1);

        user.setPassword(hash);

        userDao.signInUsers(user);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {
        if (!validateToken(token)) {
            return;
        }
        userDao.delete(id);
    }

    private boolean validateToken(String token) {
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }
}
