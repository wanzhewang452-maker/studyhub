package com.studyhub.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20,message = "8-20位之间")
    private String password;
    @Min(value = 1, message = "年龄不合法")
    @Max(value = 150, message = "年龄不合法")
    private Integer age;
}
