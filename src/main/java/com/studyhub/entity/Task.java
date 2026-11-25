package com.studyhub.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private Integer done;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private Integer userId;
}
