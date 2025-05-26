package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.exception.TaskAlreadyExists;
import com.example.UniAssist.mapper.TaskMapper;
import com.example.UniAssist.model.dto.TaskDTO;
import com.example.UniAssist.model.dto.TeacherTaskRequest;
import com.example.UniAssist.model.entity.Task;
import com.example.UniAssist.repository.LessonRepository;
import com.example.UniAssist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final LessonRepository lessonRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, LessonRepository lessonRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.lessonRepository = lessonRepository;
    }

    public Optional<TaskDTO> getTaskByLessonId(UUID lessonId) {
        return Optional.ofNullable(taskRepository.findTaskByLessonId(lessonId))
                .map(taskMapper::toDTO);
    }

    public void handleTeacherTask(TeacherTaskRequest request, UUID teacherId) {
        if (!lessonRepository.existsById(request.getLessonId())) {
            throw new LessonNotFound();
        } else if (taskRepository.existsByLessonId(request.getLessonId())) {
            throw new TaskAlreadyExists();
        }

        Task taskEntity = taskMapper.toEntity(request, teacherId);
        taskRepository.save(taskEntity);
    }
}
