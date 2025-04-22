package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.model.dto.*;
import com.example.UniAssist.projection.FullNameProjection;
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

    public TeacherLessonDTO getTeacherLesson(UUID teacherId, UUID lessonId) {
        TeacherLessonDTO lesson = lessonRepository.findLessonByTeacherAndId(teacherId, lessonId);
        if (lesson == null) {
            throw new LessonNotFound("Lesson not found");
        }
        lesson.setGroupName(groupRepository.findGroupNameById(lesson.getGroupId()));
        TeacherTaskDTO task = taskRepository.findTaskByLessonIdForTeacher(lessonId);
        if (task == null) {
            return lesson;
        }
        List<TeacherSolutionDTO> solutions = solutionRepository.findSolutionsByTaskId(task.getId());
        task.setSolutions(solutions);
        lesson.setTask(task);
        return lesson;
    }

    public StudentLessonDTO getStudentLesson(UUID studentId, UUID lessonId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        StudentLessonDTO lesson = lessonRepository.findLessonByGroupAndId(groupId, lessonId);
        if (lesson == null) {
            throw new LessonNotFound("Lesson not found");
        }
        FullNameProjection fullName = teacherRepository.findFullNameByTeacherId(lesson.getTeacherId());
        lesson.setFullName(FullNameMapper.toDTO(fullName.getLastName(), fullName.getFirstName(), fullName.getMiddleName()));
        StudentTaskDTO task = taskRepository.findTaskByLessonIdForStudent(lessonId);
        if (task == null) {
            return lesson;
        }
        StudentSolutionDTO solution = solutionRepository.findSolutionByTaskId(task.getId());
        task.setSolution(solution);
        lesson.setTask(task);
        return lesson;
    }
}