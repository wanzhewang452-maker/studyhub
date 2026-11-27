package com.studyhub.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalVO {
    private Integer id;

    private String title;

    private Integer targetMinutes;

    private Integer completedMinutes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
