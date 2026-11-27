package com.studyhub.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studyhub.common.PageBean;
import com.studyhub.dto.GoalCreateDTO;
import com.studyhub.dto.GoalPageQueryDTO;
import com.studyhub.vo.GoalVO;
import com.studyhub.entity.Goal;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.GoalMapper;
import com.studyhub.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalMapper goalMapper;

    @Override
    public PageBean<GoalVO> getGoalPage(Integer userId,GoalPageQueryDTO goalPageQueryDTO) {
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }
        PageHelper.startPage(goalPageQueryDTO.getPage(), goalPageQueryDTO.getPageSize());
        Page<Goal> goalPage = goalMapper.getGoalPage(userId, goalPageQueryDTO);
        var records = goalPage.getResult().stream()
                .map(goal -> new GoalVO(goal.getId(), goal.getTitle(), goal.getTargetMinutes(),
                        goal.getCompletedMinutes(), goal.getCreatedAt(), goal.getUpdatedAt()))
                .toList();
        return new PageBean<>(records, goalPage.getTotal());
    }

    @Override
    public int createGoal(GoalCreateDTO goalCreateDTO, Integer currentUserId) {
        if (currentUserId == null){
            throw new BusinessException(401,"用户未登录");
        }
        Goal goal = new Goal();
        goal.setTitle(goalCreateDTO.getTitle());
        goal.setTargetMinutes(goalCreateDTO.getTargetMinutes());
        goal.setUserId(currentUserId);
        goal.setCompletedMinutes(0);
        int row = goalMapper.createGoal(goal);
        if (row!=1){
            throw new BusinessException(500,"创建目标失败");
        }
        return goal.getId();
    }
}
