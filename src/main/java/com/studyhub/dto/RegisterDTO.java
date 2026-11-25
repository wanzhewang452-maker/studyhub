package com.studyhub.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 255, message = "用户名过长")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20,
            message = "密码长度必须在8-20位之间")
    @ToString.Exclude
    private String password;

    @Min(value = 1, message = "年龄不合法")
    @Max(value = 150, message = "年龄不合法")
    private Integer age;
}