package com.studyhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateDTO {

    @NotBlank(message = "任务标题不能为空")
    @Size(max = 200, message = "任务标题不能超过200个字符")
    private String title;
}