package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.model.dto.*;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LessonService {

    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final SolutionRepository solutionRepository;
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public LessonService(
            GroupRepository groupRepository,
            LessonRepository lessonRepository,
            SolutionRepository solutionRepository,
            TaskRepository taskRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository) {
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.solutionRepository = solutionRepository;
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public TeacherLessonResponse getTeacherLesson(UUID teacherId, UUID lessonId) {
        TeacherLessonDTO lesson = lessonRepository.findLessonByTeacherAndId(teacherId, lessonId);
        if (lesson == null) {
            throw new LessonNotFound("Lesson not found");
        }
        lesson.setGroupName(groupRepository.findGroupNameById(lesson.getGroupId()));
        TeacherLessonResponse response = new TeacherLessonResponse(lesson, null, null);
        TaskDTO task = taskRepository.findTaskByLessonId(lessonId);
        if (task == null) {
            return response;
        }
        List<SolutionDTO> solutions = solutionRepository.findSolutionsByTaskId(task.getId());
        response.setTask(task);
        response.setSolutions(solutions);
        return response;
    }

    public StudentLessonResponse getStudentLesson(UUID studentId, UUID lessonId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        StudentLessonDTO lesson = lessonRepository.findLessonByGroupAndId(groupId, lessonId);
        if (lesson == null) {
            throw new LessonNotFound("Lesson not found");
        }
        FullNameProjection fullName = teacherRepository.findFullNameByTeacherId(lesson.getTeacherId());
        lesson.setFullName(FullNameMapper.toDTO(fullName.getLastName(), fullName.getFirstName(), fullName.getMiddleName()));
        StudentLessonResponse response = new StudentLessonResponse(lesson, null, null);
        TaskDTO task = taskRepository.findTaskByLessonId(lessonId);
        if (task == null) {
            return response;
        }
        SolutionDTO solution = solutionRepository.findSolutionByTaskId(task.getId());
        response.setTask(task);
        response.setSolution(solution);
        return response;
    }
}