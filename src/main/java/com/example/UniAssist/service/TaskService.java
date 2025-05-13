package com.example.UniAssist.service;

import com.example.UniAssist.mapper.TaskMapper;
import com.example.UniAssist.model.dto.TaskDTO;
import com.example.UniAssist.model.entity.Task;
import com.example.UniAssist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO getTaskByLessonId(UUID lessonId) {
        Task taskEntity = taskRepository.findTaskByLessonId(lessonId);
        return taskEntity != null ? taskMapper.toDTO(taskEntity) : null;
    }
}
