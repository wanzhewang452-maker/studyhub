package com.studyhub.service;

import com.studyhub.dto.LoginDTO;
import com.studyhub.dto.RegisterDTO;
import com.studyhub.entity.User;
import com.studyhub.vo.UserVO;

public interface UserService {
    void register(RegisterDTO registerDTO);
    User findByUsername(String username);

    String login(LoginDTO loginDTO);
}
