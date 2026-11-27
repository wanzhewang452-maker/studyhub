package com.studyhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    private Integer id;

    private Integer userId;

    private String title;

    private Integer targetMinutes;

    private Integer completedMinutes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}