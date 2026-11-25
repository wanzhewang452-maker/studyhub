package com.studyhub.service.impl;

import com.studyhub.dto.RegisterDTO;
import com.studyhub.mapper.UserMapper;
import com.studyhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(RegisterDTO registerDTO) {
        userMapper.register(registerDTO);
    }
}
