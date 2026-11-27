package com.studyhub.service;

import com.studyhub.common.PageBean;
import com.studyhub.dto.GoalCreateDTO;
import com.studyhub.dto.GoalPageQueryDTO;
import com.studyhub.vo.GoalVO;
import jakarta.validation.Valid;

public interface GoalService {
    int createGoal(@Valid GoalCreateDTO goalCreateDTO, Integer currentUserId);

    PageBean<GoalVO> getGoalPage(Integer userId, @Valid GoalPageQueryDTO goalPageQueryDTO);
}
