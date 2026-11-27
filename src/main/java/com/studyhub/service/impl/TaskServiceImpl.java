package com.studyhub.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studyhub.common.PageBean;
import com.studyhub.dto.TaskCreateDTO;
import com.studyhub.dto.TaskPageQueryDTO;
import com.studyhub.dto.TaskUpdateDTO;
import com.studyhub.entity.Task;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.TaskMapper;
import com.studyhub.service.TaskService;
import com.studyhub.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public PageBean<TaskVO> page(Integer userId, TaskPageQueryDTO dto) {
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<TaskVO> page = taskMapper.page(userId, dto.getDone(), dto.getTitle());
        return new PageBean<>(page.getResult(), page.getTotal());
    }

    @Override
    public void updateTask(Integer userId, Integer id, TaskUpdateDTO dto) {
        checkIdentityAndId(userId, id);
        if (dto == null || !dto.isUpdatePresent() || !dto.isTitleValid()) {
            throw new BusinessException(400, "请提供有效的任务标题或完成状态");
        }
        String title = dto.getTitle() == null ? null : dto.getTitle().trim();
        Task task = new Task();

        task.setId(id);
        task.setUserId(userId);
        task.setTitle(title);

// 如果 Task 实体中的 done 是 Integer
        task.setDone(
                dto.getDone() == null
                        ? null
                        : (dto.getDone() ? 1 : 0)
        );

        int rows = taskMapper.updateTask(task);
        // Some JDBC settings report zero for an unchanged row. Distinguish that
        // from a missing task without exposing another user's task.
        if (rows == 0 && taskMapper.countOwnedTask(userId, id) == 0) {
            throw new BusinessException(404, "任务不存在");
        }
    }

    @Override
    public void delete(Integer userId, Integer id) {
        checkIdentityAndId(userId, id);
        if (taskMapper.delete(userId, id) == 0) {
            throw new BusinessException(404, "任务不存在");
        }
    }

    private void checkIdentityAndId(Integer userId, Integer id) {
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }
        if (id == null || id < 1) {
            throw new BusinessException(400, "任务 ID 不合法");
        }
    }

    @Override
    public void createTask(Integer userId,
                           TaskCreateDTO taskCreateDTO) {

        // 1. 检查用户身份
        if (userId == null) {
            throw new BusinessException(401, "用户未登录");
        }

        // 2. 创建任务实体
        Task task = new Task();

        task.setTitle(taskCreateDTO.getTitle().trim());
        task.setUserId(userId);
        task.setDone(0);

        // 3. 插入数据库
        int rows = taskMapper.insert(task);

        // 4. 判断是否插入成功
        if (rows != 1) {
            throw new BusinessException(500, "创建任务失败");
        }
    }
}