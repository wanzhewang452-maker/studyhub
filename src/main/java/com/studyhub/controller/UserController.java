package com.studyhub.controller;

import com.studyhub.common.Result;
import com.studyhub.dto.LoginDTO;
import com.studyhub.dto.RegisterDTO;
import com.studyhub.service.UserService;
import com.studyhub.vo.UserVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterDTO user) {
        log.info("注册用户");
        userService.register(user);
        return Result.success();
    }
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginDTO user) {
        log.info("用户登录");
        String token = userService.login(user);
        return Result.success(token);
    }
    @GetMapping("/me/id")
    public Result<Integer> getCurrentUserId(
            @AuthenticationPrincipal Integer userId) {

        return Result.success(userId);
    }
}
