package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.dto.TeacherResponseDTO;
import com.example.UniAssist.model.dto.TeacherTaskDTO;
import com.example.UniAssist.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LessonService {

    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final ResponseRepository responseRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public LessonService(
            GroupRepository groupRepository,
            LessonRepository lessonRepository,
            ResponseRepository responseRepository,
            TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.responseRepository = responseRepository;
        this.taskRepository = taskRepository;
    }

    public TeacherLessonDTO getTeacherLesson(UUID teacherId, UUID lessonId) {
        TeacherLessonDTO lesson = lessonRepository.findLessonByTeacherAndId(teacherId, lessonId);
        if (lesson == null) {
            throw new LessonNotFound("Lesson not found");
        }
        lesson.setName(groupRepository.findGroupNameById(lesson.getGroupId()));
        TeacherTaskDTO task = taskRepository.findTaskByLessonId(lessonId);
        if (task == null) {
            return lesson;
        }
        List<TeacherResponseDTO> responses = responseRepository.findResponsesByTaskId(task.getId());
        task.setResponses(responses);
        lesson.setTask(task);
        return lesson;
    }
}