package com.studyhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class GoalPageQueryDTO {
    @Min(value = 1, message = "页码不能小于1")
    private int page=1;
    @Min(value = 1, message = "页大小不能小于1")
    @Max(value = 100, message = "页大小不能大于100")
    private int pageSize=10;
    private String title;
}
