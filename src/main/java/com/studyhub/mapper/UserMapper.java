package com.studyhub.mapper;

import com.studyhub.dto.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void register(RegisterDTO registerDTO);
}
