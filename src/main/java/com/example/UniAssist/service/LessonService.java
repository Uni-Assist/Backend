package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.LessonMapper;
import com.example.UniAssist.model.dto.SolutionDTO;
import com.example.UniAssist.model.dto.StudentLessonDTO;
import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.TaskDTO;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.LessonProjection;
import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.repository.LessonRepository;
import com.example.UniAssist.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LessonService {

    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final LessonMapper lessonMapper;
    private final TaskService taskService;
    private final SolutionService solutionService;

    @Autowired
    public LessonService(
            GroupRepository groupRepository,
            LessonRepository lessonRepository,
            TeacherRepository teacherRepository,
            LessonMapper lessonMapper,
            TaskService taskService,
            SolutionService solutionService) {
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.lessonMapper = lessonMapper;
        this.taskService = taskService;
        this.solutionService = solutionService;
    }

    public TeacherLessonResponse getTeacherLesson(UUID lessonId) {
        LessonProjection lessonProjection = getLessonProjectionById(lessonId);
        String groupName = groupRepository.findGroupNameById(lessonProjection.getGroupId());
        TeacherLessonDTO lesson = lessonMapper.toDTO(lessonProjection, groupName);
        TeacherLessonResponse response = new TeacherLessonResponse(lesson, null, null);

        TaskDTO taskDTO = taskService.getTaskByLessonId(lessonId);
        if (taskDTO == null) {
            return response;
        }

        List<SolutionDTO> solutions = solutionService.getSolutionsByTaskId(taskDTO.getId());
        response.setTask(taskDTO);
        response.setSolutions(solutions);
        return response;
    }

    public StudentLessonResponse getStudentLesson(UUID lessonId, UUID studentId) {
        LessonProjection lessonProjection = getLessonProjectionById(lessonId);
        FullNameProjection fullName = teacherRepository.findFullNameByTeacherId(lessonProjection.getTeacherId());
        StudentLessonDTO lesson = lessonMapper.toDTO(lessonProjection, fullName);
        StudentLessonResponse response = new StudentLessonResponse(lesson, null, null);

        TaskDTO taskDTO = taskService.getTaskByLessonId(lessonId);
        if (taskDTO == null) {
            return response;
        }

        SolutionDTO solution = solutionService.getSolutionByTaskId(taskDTO.getId(), studentId);
        response.setTask(taskDTO);
        response.setSolution(solution);
        return response;
    }

    private LessonProjection getLessonProjectionById(UUID lessonId) {
        LessonProjection projection = lessonRepository.findLessonProjectionById(lessonId);
        if (projection == null) {
            throw new LessonNotFound();
        }
        return projection;
    }
}
