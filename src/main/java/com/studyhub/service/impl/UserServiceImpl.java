package com.studyhub.service.impl;

import com.studyhub.dto.LoginDTO;
import com.studyhub.entity.User;
import com.studyhub.dto.RegisterDTO;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.UserMapper;
import com.studyhub.service.UserService;
import com.studyhub.utils.JwtUtil;
import com.studyhub.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public String login(LoginDTO loginDTO) {
//        查询数据库找到对应的用户判断是否存在；判断密码是否正确
        User user = userMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(401, "密码错误");
        }
        UserVO userVO = new UserVO(user.getId(),user.getUsername(), user.getAge());
        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        //检查用户名是否存在
        if (userMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new BusinessException(409, "用户名已存在");
        }
        if (registerDTO.getPassword().getBytes(StandardCharsets.UTF_8).length > 72){
            throw new BusinessException(400, "密码长度不能超过72个字符");
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setAge(registerDTO.getAge());
        String hashPassword = passwordEncoder.encode(registerDTO.getPassword());
        user.setPasswordHash(hashPassword);
        try {
            int rows = userMapper.register(user);
            if (rows != 1) {
                throw new BusinessException(500, "注册失败");
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(409, "用户名已经存在，注册失败");
        }
    }
}
