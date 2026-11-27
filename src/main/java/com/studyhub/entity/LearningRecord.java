package com.studyhub.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LearningRecord {
    private Integer id;
    private Integer goalId;
    private String title;
    private Integer durationMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
