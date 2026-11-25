package com.studyhub.vo;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskVO {
    private Integer id;
    private String title;
    private Boolean done;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
