package com.studyhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LearningRecordDTO {
    @NotNull(message = "目标 ID 不能为空")
    private Integer goalId;

    @NotBlank(message = "学习内容不能为空")
    @Size(max = 200, message = "学习内容不能超过200个字符")
    private String title;

    @NotNull(message = "学习时长不能为空")
    @Min(value = 1, message = "学习时长必须大于0")
    private Integer durationMinutes;
}
