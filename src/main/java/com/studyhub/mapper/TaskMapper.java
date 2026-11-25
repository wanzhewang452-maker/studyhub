package com.studyhub.mapper;

import com.github.pagehelper.Page;
import com.studyhub.entity.Task;
import com.studyhub.vo.TaskVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskMapper {

    @Insert("""
            INSERT INTO studyhub.tasks (title, done, user_id)
            VALUES (#{title}, #{done}, #{userId})
            """)
    int insert(Task task);

    Page<TaskVO> page(@Param("userId") Integer userId, @Param("done") Boolean done,
                      @Param("title") String title);
}
