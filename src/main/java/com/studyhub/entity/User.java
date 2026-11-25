package com.studyhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String username;

    @ToString.Exclude
    private String passwordHash;

    private Integer age;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}