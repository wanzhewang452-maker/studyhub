package com.studyhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class LearningRecordPageQueryDTO {
    @Min(value = 1, message = "页码不能小于1")
    private int page = 1;
    @Min(value = 1, message = "每页数量不能小于1")
    @Max(value = 100, message = "每页数量不能大于100")
    private int pageSize = 10;
    @Min(value = 1, message = "目标ID不能小于1")
    private Integer goalId;
    private String title;
}
