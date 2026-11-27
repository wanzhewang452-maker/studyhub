package com.studyhub.service;

import com.studyhub.common.PageBean;
import com.studyhub.dto.LearningRecordDTO;
import com.studyhub.dto.LearningRecordPageQueryDTO;
import com.studyhub.entity.LearningRecord;
import jakarta.validation.Valid;

public interface LearningRecordService {

    Integer createRecord(
            Integer userId,
            LearningRecordDTO dto
    );

    PageBean<LearningRecord> getLearningRecordPage(Integer userId, @Valid LearningRecordPageQueryDTO learningRecordPageQueryDTO);
}