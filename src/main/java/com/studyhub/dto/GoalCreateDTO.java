package com.studyhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GoalCreateDTO {

    @NotBlank(message = "学习目标标题不能为空")
    @Size(max = 200, message = "标题不能超过200个字符")
    private String title;

    @NotNull(message = "目标学习时长不能为空")
    @Min(value = 1, message = "目标学习时长必须大于0")
    private Integer targetMinutes;
}