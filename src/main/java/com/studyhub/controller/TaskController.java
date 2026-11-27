package com.studyhub.controller;

import com.studyhub.common.PageBean;
import com.studyhub.common.Result;
import com.studyhub.dto.TaskCreateDTO;
import com.studyhub.dto.TaskPageQueryDTO;
import com.studyhub.dto.TaskUpdateDTO;
import com.studyhub.service.TaskService;
import com.studyhub.vo.TaskVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Result<Void> createTask(
            @AuthenticationPrincipal Integer userId,
            @Valid @RequestBody TaskCreateDTO taskCreateDTO) {

        log.info("创建学习任务，userId={}", userId);

        taskService.createTask(userId, taskCreateDTO);

        return Result.success();
    }
    @GetMapping
    public Result<PageBean<TaskVO>> page(@AuthenticationPrincipal Integer userId,
                                         @Valid @ModelAttribute TaskPageQueryDTO dto) {

        return Result.success(taskService.page(userId, dto));
    }
    @DeleteMapping("/{id}")
    public Result<Void> delete(@AuthenticationPrincipal Integer userId,
                               @PathVariable Integer id) {
        taskService.delete(userId, id);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> updateTask(
            @AuthenticationPrincipal Integer userId,
            @PathVariable Integer id,
            @Valid @RequestBody TaskUpdateDTO dto) {

        log.info("更新学习任务，userId={}", userId);
        taskService.updateTask(userId, id, dto);
        return Result.success();
    }
}