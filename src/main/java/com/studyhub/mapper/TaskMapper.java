package com.studyhub.mapper;

import com.github.pagehelper.Page;
import com.studyhub.annotation.AutoFill;
import com.studyhub.entity.Task;
import com.studyhub.enumeration.OperationType;
import com.studyhub.vo.TaskVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TaskMapper {

    @AutoFill(OperationType.INSERT)
    @Insert("""
    INSERT INTO studyhub.tasks
        (title, done, user_id, created_at, updated_at)
    VALUES
        (#{title}, #{done}, #{userId},
         #{createdAt}, #{updatedAt})
    """)
    int insert(Task task);

    Page<TaskVO> page(@Param("userId") Integer userId, @Param("done") Boolean done,
                      @Param("title") String title);

    @AutoFill(OperationType.UPDATE)
    int updateTask(Task task);

    int countOwnedTask(@Param("userId") Integer userId, @Param("id") Integer id);

    int delete(@Param("userId") Integer userId, @Param("id") Integer id);
}
