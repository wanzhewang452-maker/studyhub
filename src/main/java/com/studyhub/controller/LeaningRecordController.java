package com.studyhub.controller;

import com.studyhub.common.PageBean;
import com.studyhub.common.Result;
import com.studyhub.dto.LearningRecordDTO;
import com.studyhub.dto.LearningRecordPageQueryDTO;
import com.studyhub.entity.LearningRecord;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.studyhub.service.LearningRecordService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/records")
public class LeaningRecordController {
    @Autowired
    private LearningRecordService learningRecordService;

    @PostMapping
    public Result<Integer> addLearningRecord(@AuthenticationPrincipal Integer userId, @Valid @RequestBody LearningRecordDTO learningRecordDTO) {
        int recordId = learningRecordService.createRecord(userId,learningRecordDTO);
        return Result.success(recordId);
    }
    @PostMapping("/page")
    public Result<PageBean<LearningRecord>> getLearningRecordPage(@AuthenticationPrincipal Integer userId, @Valid @RequestBody LearningRecordPageQueryDTO learningRecordPageQueryDTO) {
        return Result.success(learningRecordService.getLearningRecordPage(userId, learningRecordPageQueryDTO));
    }
    @DeleteMapping("/{recordId}")
    public Result<Void> deleteLearningRecord(@AuthenticationPrincipal Integer userId, @PathVariable Integer recordId) {
        learningRecordService.deleteLearningRecord(userId, recordId);
        return Result.success();
    }
}
