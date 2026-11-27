package com.studyhub.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studyhub.common.PageBean;
import com.studyhub.dto.LearningRecordDTO;
import com.studyhub.dto.LearningRecordPageQueryDTO;
import com.studyhub.entity.LearningRecord;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.GoalMapper;
import com.studyhub.mapper.LearningRecordMapper;
import com.studyhub.service.LearningRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 导入项目中已有的 BusinessException

@Service
public class LearningRecordServiceImpl
        implements LearningRecordService {

    @Autowired
    private LearningRecordMapper learningRecordMapper;

    @Autowired
    private GoalMapper goalMapper;

    @Override
    public PageBean<LearningRecord> getLearningRecordPage(Integer userId, LearningRecordPageQueryDTO learningRecordPageQueryDTO) {
        if (userId == null){
            throw new BusinessException(401, "用户未登录");
        }
        if (learningRecordPageQueryDTO == null){
            throw new BusinessException(400, "查询参数为空");
        }
        PageHelper.startPage(learningRecordPageQueryDTO.getPage(), learningRecordPageQueryDTO.getPageSize());
        Page<LearningRecord> page = learningRecordMapper.getLearningRecordPage(userId, learningRecordPageQueryDTO);
        return new PageBean<>(page.getResult(), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createRecord(
            Integer userId,
            LearningRecordDTO dto) {

        // 1. 检查用户身份
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }

        // 2. 更新目标的完成时长，同时检查目标归属
        int goalRows = goalMapper.increaseCompletedMinutes(
                dto.getGoalId(),
                userId,
                dto.getDurationMinutes()
        );

        if (goalRows != 1) {
            throw new BusinessException(404, "学习目标不存在");
        }

        // 3. 创建学习记录对象
        LearningRecord record = new LearningRecord();

        record.setGoalId(dto.getGoalId());
        record.setTitle(dto.getTitle().trim());
        record.setDurationMinutes(dto.getDurationMinutes());

        // 4. 插入学习记录
        int recordRows = learningRecordMapper.insert(record);

        if (recordRows != 1) {
            throw new BusinessException(500, "创建学习记录失败");
        }

        // 5. 检查主键是否回填
        if (record.getId() == null) {
            throw new BusinessException(500, "获取学习记录ID失败");
        }

        // 6. 返回新记录ID
        return record.getId();
    }
}