package com.studyhub.service;

import com.studyhub.common.PageBean;
import com.studyhub.vo.TaskVO;
import com.studyhub.dto.TaskCreateDTO;
import com.studyhub.dto.TaskPageQueryDTO;
import com.studyhub.dto.TaskUpdateDTO;

public interface TaskService {

    void createTask(Integer userId, TaskCreateDTO taskCreateDTO);

    PageBean<TaskVO> page(Integer userId, TaskPageQueryDTO dto);

    void delete(Integer userId, Integer id);

    void updateTask(Integer userId, Integer id, TaskUpdateDTO dto);
}