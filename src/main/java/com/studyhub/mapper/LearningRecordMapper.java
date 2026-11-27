package com.studyhub.mapper;

import com.github.pagehelper.Page;
import com.studyhub.annotation.AutoFill;
import com.studyhub.dto.LearningRecordPageQueryDTO;
import com.studyhub.entity.LearningRecord;
import com.studyhub.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.studyhub.annotation.AutoFill;
import com.studyhub.entity.LearningRecord;
import com.studyhub.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface LearningRecordMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("""
            INSERT INTO studyhub.learning_records
            (goal_id, title, duration_minutes,
             created_at, updated_at)
            VALUES
            (#{goalId}, #{title}, #{durationMinutes},
             #{createdAt}, #{updatedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LearningRecord record);

    Page<LearningRecord> getLearningRecordPage(@Param("userId") Integer userId, @Param("learningRecordPageQueryDTO") LearningRecordPageQueryDTO learningRecordPageQueryDTO);
}
