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
    @Transactional(rollbackFor = Exception.class)
    public int deleteLearningRecord(Integer userId, Integer recordId) {

        // 1. 校验用户身份
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }

        // 2. 校验记录ID
        if (recordId == null || recordId <= 0) {
            throw new BusinessException(400, "记录ID不合法");
        }

        // 3. 查询学习记录，同时校验用户归属
        LearningRecord record =
                learningRecordMapper.getLearningRecordById(
                        userId, recordId
                );

        if (record == null) {
            throw new BusinessException(404, "学习记录不存在");
        }

        // 4. 删除学习记录
        int rows = learningRecordMapper.deleteRecord(userId, recordId);

        // 关键修改：必须确认本次请求真正删除了记录
        if (rows != 1) {
            throw new BusinessException(404, "学习记录不存在或已删除");
        }

        // 5. 扣减目标完成时长
        int goalRows = goalMapper.decreaseCompletedMinutes(
                userId,
                record.getGoalId(),
                record.getDurationMinutes()
        );

        // 6. 检查扣减结果，失败则回滚删除操作
        if (goalRows != 1) {
            throw new BusinessException(500, "扣减学习时长失败");
        }

        return rows;
    }

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