package com.studyhub.mapper;

import com.github.pagehelper.Page;
import com.studyhub.annotation.AutoFill;
import com.studyhub.dto.GoalPageQueryDTO;
import com.studyhub.entity.Goal;
import com.studyhub.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GoalMapper {
    @AutoFill(OperationType.INSERT)
    @Insert("INSERT INTO studyhub.goals (title, target_minutes, user_id,created_at,updated_at) VALUES (#{title}, #{targetMinutes}, #{userId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createGoal(Goal goal);

    Page<Goal> getGoalPage(@Param("userId") Integer userId, @Param("goalPageQueryDTO")GoalPageQueryDTO goalPageQueryDTO);

    @Update("""
    UPDATE studyhub.goals
    SET completed_minutes = completed_minutes + #{durationMinutes},
        updated_at = CURRENT_TIMESTAMP
    WHERE id = #{goalId}
      AND user_id = #{userId}
    """)
    int increaseCompletedMinutes(
            @Param("goalId") Integer goalId,
            @Param("userId") Integer userId,
            @Param("durationMinutes") Integer durationMinutes
    );
    @Update("""
    UPDATE studyhub.goals
    SET completed_minutes = completed_minutes - #{durationMinutes},
        updated_at = CURRENT_TIMESTAMP
    WHERE user_id = #{userId}
      AND id = #{goalId}
      AND completed_minutes >= #{durationMinutes}
    """)
    int decreaseCompletedMinutes(
            @Param("userId") Integer userId,
            @Param("goalId") Integer goalId,
            @Param("durationMinutes") Integer durationMinutes
    );
}
