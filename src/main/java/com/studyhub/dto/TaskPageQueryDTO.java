package com.studyhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPageQueryDTO {
    @Min(value = 1, message = "页码必须大于等于 1")
    private int page = 1;

    @Min(value = 1, message = "每页数量必须大于等于 1")
    @Max(value = 100, message = "每页数量不能超过 100")
    private int pageSize = 10;

    // null 表示全部，true 表示已完成，false 表示未完成。
    private Boolean done;

    private String title;
}
