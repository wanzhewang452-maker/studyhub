package com.studyhub.controller;

import com.studyhub.common.PageBean;
import com.studyhub.common.Result;
import com.studyhub.dto.GoalCreateDTO;
import com.studyhub.dto.GoalPageQueryDTO;
import com.studyhub.vo.GoalVO;
import com.studyhub.service.GoalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/goals")
@Slf4j
public class GoalController {
    @Autowired
    private GoalService goalService;// Changed from private GoalServiceImpl goalService;
    @PostMapping
    public Result createGoal(@Valid @RequestBody GoalCreateDTO goalCreateDTO,@AuthenticationPrincipal Integer currentUserId) {
        int goalId = goalService.createGoal(goalCreateDTO, currentUserId);
        return Result.success(goalId);
    }
    @PostMapping("/page")
    public Result<PageBean<GoalVO>> getGoalPage(@AuthenticationPrincipal Integer currentUserId, @Valid @RequestBody GoalPageQueryDTO goalPageQueryDTO) {
        return Result.success(goalService.getGoalPage(currentUserId, goalPageQueryDTO));
    }
}
