package com.studyhub.mapper;

import com.studyhub.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM studyhub.users WHERE username = #{username}")
    User findByUsername(String username);
    @Insert("INSERT INTO studyhub.users (username, password_hash, age) VALUES (#{username}, #{passwordHash}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int register(User user);
}
