package com.studyhub.mapper;

import com.github.pagehelper.Page;
import com.studyhub.annotation.AutoFill;
import com.studyhub.dto.LearningRecordPageQueryDTO;
import com.studyhub.entity.LearningRecord;
import com.studyhub.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import com.studyhub.annotation.AutoFill;
import com.studyhub.entity.LearningRecord;
import com.studyhub.enumeration.OperationType;

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

    @Delete("""
    DELETE r
    FROM studyhub.learning_records r
    INNER JOIN studyhub.goals g
        ON r.goal_id = g.id
    WHERE g.user_id = #{userId}
      AND r.id = #{recordId}
    """)
    int deleteRecord(
            @Param("userId") Integer userId,
            @Param("recordId") Integer recordId
    );
    @Select("""
    SELECT r.*
    FROM studyhub.learning_records r
    INNER JOIN studyhub.goals g
        ON r.goal_id = g.id
    WHERE r.id = #{recordId}
      AND g.user_id = #{userId}
    """)
    LearningRecord getLearningRecordById(
            @Param("userId") Integer userId,
            @Param("recordId") Integer recordId
    );
}
