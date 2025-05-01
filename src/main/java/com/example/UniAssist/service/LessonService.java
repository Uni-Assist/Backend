package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.LessonMapper;
import com.example.UniAssist.mapper.SolutionMapper;
import com.example.UniAssist.mapper.TaskMapper;
import com.example.UniAssist.model.dto.*;
import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.model.entity.Task;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.LessonProjection;
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
    private final TeacherRepository teacherRepository;
    private final LessonMapper lessonMapper;
    private final TaskMapper taskMapper;
    private final SolutionMapper solutionMapper;

    @Autowired
    public LessonService(
            GroupRepository groupRepository,
            LessonRepository lessonRepository,
            SolutionRepository solutionRepository,
            TaskRepository taskRepository,
            TeacherRepository teacherRepository,
            LessonMapper lessonMapper,
            TaskMapper taskMapper,
            SolutionMapper solutionMapper) {
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.solutionRepository = solutionRepository;
        this.taskRepository = taskRepository;
        this.teacherRepository = teacherRepository;
        this.lessonMapper = lessonMapper;
        this.taskMapper = taskMapper;
        this.solutionMapper = solutionMapper;
    }

    public TeacherLessonResponse getTeacherLesson(UUID lessonId) {
        LessonProjection lessonProjection = lessonRepository.findLessonProjectionById(lessonId);
        if (lessonProjection == null) {
            throw new LessonNotFound("Lesson not found");
        }
        String groupName = groupRepository.findGroupNameById(lessonProjection.getGroupId());
        TeacherLessonDTO lesson = lessonMapper.toDTO(lessonProjection, groupName);
        TeacherLessonResponse response = new TeacherLessonResponse(lesson, null, null);
        Task rawTask = taskRepository.findTaskByLessonId(lessonId);
        if (rawTask == null) {
            return response;
        }
        TaskDTO task = taskMapper.toDTO(rawTask);
        List<Solution> rawSolutions = solutionRepository.findSolutionsByTaskId(task.getId());
        List<SolutionDTO> solutions = solutionMapper.toDto(rawSolutions);
        response.setTask(task);
        response.setSolutions(solutions);
        return response;
    }

    public StudentLessonResponse getStudentLesson(UUID lessonId) {
        LessonProjection lessonProjection = lessonRepository.findLessonProjectionById(lessonId);
        if (lessonProjection == null) {
            throw new LessonNotFound("Lesson not found");
        }
        FullNameProjection fullName = teacherRepository.findFullNameByTeacherId(lessonProjection.getTeacherId());
        StudentLessonDTO lesson = lessonMapper.toDTO(lessonProjection, fullName);
        StudentLessonResponse response = new StudentLessonResponse(lesson, null, null);
        Task rawTask = taskRepository.findTaskByLessonId(lessonId);
        if (rawTask == null) {
            return response;
        }
        TaskDTO task = taskMapper.toDTO(rawTask);
        Solution rawSolution = solutionRepository.findSolutionByTaskId(task.getId());
        SolutionDTO solution = solutionMapper.toDto(rawSolution);
        response.setTask(task);
        response.setSolution(solution);
        return response;
    }
}