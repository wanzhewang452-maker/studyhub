package com.studyhub.controller;

import com.studyhub.common.Result;
import com.studyhub.dto.RegisterDTO;
import com.studyhub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/users")
public class User {
    @Autowired
    private UserService userService;
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterDTO user) {
        log.info("注册用户");
        userService.register(user);
        return Result.success("Register successfully!");
    }
}
