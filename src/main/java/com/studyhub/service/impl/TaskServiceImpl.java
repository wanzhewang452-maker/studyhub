package com.studyhub.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studyhub.common.PageBean;
import com.studyhub.dto.TaskCreateDTO;
import com.studyhub.dto.TaskPageQueryDTO;
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