package com.studyhub.service;

import com.studyhub.common.PageBean;
import com.studyhub.vo.TaskVO;
import com.studyhub.dto.TaskCreateDTO;
import com.studyhub.dto.TaskPageQueryDTO;

public interface TaskService {

    void createTask(Integer userId, TaskCreateDTO taskCreateDTO);

    PageBean<TaskVO> page(Integer userId, TaskPageQueryDTO dto);
}