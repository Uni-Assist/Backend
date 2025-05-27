package com.example.UniAssist.service;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.LessonMapper;
import com.example.UniAssist.mapper.SolutionMapper;
import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.dto.StudentLessonDTO;
import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.StudentSolutionDTO;
import com.example.UniAssist.model.dto.TaskDTO;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.model.dto.TeacherSolutionDTO;
import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.LessonProjection;
import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.repository.LessonRepository;
import com.example.UniAssist.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final LessonMapper lessonMapper;
    private final TaskService taskService;
    private final SolutionService solutionService;
    private final StudentService studentService;

    @Autowired
    public LessonService(
            GroupRepository groupRepository,
            LessonRepository lessonRepository,
            TeacherRepository teacherRepository,
            LessonMapper lessonMapper,
            TaskService taskService,
            SolutionService solutionService,
            StudentService studentService) {
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.lessonMapper = lessonMapper;
        this.taskService = taskService;
        this.solutionService = solutionService;
        this.studentService = studentService;
    }

    public TeacherLessonResponse getTeacherLesson(UUID lessonId) {
        LessonProjection lessonProjection = getLessonProjectionById(lessonId);
        String groupName = groupRepository.findGroupNameById(lessonProjection.getGroupId());
        TeacherLessonDTO lesson = lessonMapper.toDTO(lessonProjection, groupName);
        TeacherLessonResponse response = new TeacherLessonResponse(lesson, null, Collections.emptyList());

        Optional<TaskDTO> optionalTask = taskService.getTaskByLessonId(lessonId);
        if (optionalTask.isPresent()) {
            TaskDTO taskDTO = optionalTask.get();
            List<Solution> solutionEntity = solutionService.getSolutionsByTaskId(taskDTO.getId());
            if (!solutionEntity.isEmpty()) {
                List<UUID> studentIds = solutionEntity.stream().map(Solution::getStudentId).collect(Collectors.toList());
                Map<UUID, FullNameDTO> fullNames = studentService.fetchFullNames(studentIds);
                List<TeacherSolutionDTO> solutions = SolutionMapper.toDTO(solutionEntity, fullNames);
                response.setSolutions(solutions);
            }
            response.setTask(taskDTO);
        }

        return response;
    }

    public StudentLessonResponse getStudentLesson(UUID lessonId, UUID studentId) {
        LessonProjection lessonProjection = getLessonProjectionById(lessonId);
        FullNameProjection fullName = teacherRepository.findFullNameByTeacherId(lessonProjection.getTeacherId());
        StudentLessonDTO lesson = lessonMapper.toDTO(lessonProjection, fullName);
        StudentLessonResponse response = new StudentLessonResponse(lesson, null, null);

        Optional<TaskDTO> optionalTask = taskService.getTaskByLessonId(lessonId);
        if (optionalTask.isPresent()) {
            TaskDTO taskDTO = optionalTask.get();
            response.setTask(taskDTO);

            Optional<StudentSolutionDTO> optionalSolution = solutionService.getSolutionByTaskId(taskDTO.getId(), studentId);
            optionalSolution.ifPresent(response::setSolution);
        }

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
