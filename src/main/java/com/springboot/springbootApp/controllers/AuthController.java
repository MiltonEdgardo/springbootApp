package com.springboot.springbootApp.controllers;

import com.springboot.springbootApp.dao.UserDao;
import com.springboot.springbootApp.models.User;
import com.springboot.springbootApp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {

        User userActive = userDao.getUserByCredentials(user);
        if (userActive != null) {
            return getTokenJwt(userActive);
        }
        return "FAIL";
    }

    private String getTokenJwt(User user) {
        return jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
    }
}
