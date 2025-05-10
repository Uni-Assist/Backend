package com.example.UniAssist;

import com.example.UniAssist.exception.LessonNotFound;
import com.example.UniAssist.mapper.LessonMapper;
import com.example.UniAssist.mapper.SolutionMapper;
import com.example.UniAssist.mapper.TaskMapper;
import com.example.UniAssist.model.dto.StudentLessonDTO;
import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.model.entity.Task;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.LessonProjection;
import com.example.UniAssist.service.LessonService;
import com.example.UniAssist.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тесты для LessonService")
@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock private GroupRepository groupRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private SolutionRepository solutionRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private LessonMapper lessonMapper;
    @Mock private TaskMapper taskMapper;
    @Mock private SolutionMapper solutionMapper;

    @InjectMocks
    private LessonService lessonService;

    private UUID lessonId;
    private LessonProjection lessonProj;
    private FullNameProjection fullNameProj;
    private LessonProjection teacherLessonProj;
    private String groupName;
    private Task rawTask;
    private Task rawTeacherTask;
    private StudentLessonDTO studentDTO;
    private TeacherLessonDTO teacherDTO;
    private com.example.UniAssist.model.dto.TaskDTO taskDTO;
    private com.example.UniAssist.model.dto.SolutionDTO solutionDTO;
    private List<Solution> rawSolutions;
    private List<com.example.UniAssist.model.dto.SolutionDTO> solutionDTOs;

    @BeforeEach
    void setUp() {
        lessonId = UUID.randomUUID();
        lessonProj = mock(LessonProjection.class);
        fullNameProj = mock(FullNameProjection.class);
        rawTask = new Task();
        rawTask.setId(UUID.randomUUID());
        taskDTO = new com.example.UniAssist.model.dto.TaskDTO();
        solutionDTO = new com.example.UniAssist.model.dto.SolutionDTO();
        studentDTO = new StudentLessonDTO();
        teacherDTO = new TeacherLessonDTO();
        rawSolutions = Collections.singletonList(new Solution());
        solutionDTOs = Collections.singletonList(solutionDTO);
    }

    @Test
    @DisplayName("getTeacherLesson: бросает LessonNotFound, если урок не найден")
    void getTeacherLesson_NotFound_ShouldThrow() {
        when(lessonRepository.findLessonProjectionById(lessonId)).thenReturn(null);

        assertThatThrownBy(() -> lessonService.getTeacherLesson(lessonId))
            .isInstanceOf(LessonNotFound.class)
            .hasMessage("Lesson not found");
    }

    @Test
    @DisplayName("getTeacherLesson: нет задания — возвращает пустой ответ")
    void getTeacherLesson_NoTask_ShouldReturnEmptyResponse() {
        when(lessonRepository.findLessonProjectionById(lessonId)).thenReturn(lessonProj);
        when(groupRepository.findGroupNameById(any())).thenReturn(groupName);
        when(lessonMapper.toDTO(lessonProj, groupName)).thenReturn(teacherDTO);
        when(taskRepository.findTaskByLessonId(lessonId)).thenReturn(null);

        TeacherLessonResponse resp = lessonService.getTeacherLesson(lessonId);

        assertThat(resp.getLesson()).isEqualTo(teacherDTO);
        assertThat(resp.getTask()).isNull();
        assertThat(resp.getSolutions()).isNullOrEmpty();
        verify(solutionRepository, never()).findSolutionsByTaskId(any());
    }

    @Test
    @DisplayName("getTeacherLesson: есть задание и решения — возвращает полный ответ")
    void getTeacherLesson_WithTaskAndSolutions_ShouldReturnFull() {
        when(lessonRepository.findLessonProjectionById(lessonId))
                .thenReturn(lessonProj);
        when(groupRepository.findGroupNameById(lessonProj.getGroupId()))
                .thenReturn(groupName);
        when(lessonMapper.toDTO(lessonProj, groupName))
                .thenReturn(teacherDTO);

        when(taskRepository.findTaskByLessonId(lessonId))
                .thenReturn(rawTask);
        taskDTO.setId(rawTask.getId());
        when(taskMapper.toDTO(rawTask))
                .thenReturn(taskDTO);

        when(solutionRepository.findSolutionsByTaskId(rawTask.getId()))
                .thenReturn(rawSolutions);
        when(solutionMapper.toDto(rawSolutions))
                .thenReturn(solutionDTOs);

        TeacherLessonResponse resp = lessonService.getTeacherLesson(lessonId);

        assertThat(resp.getLesson()).isEqualTo(teacherDTO);
        assertThat(resp.getTask()).isEqualTo(taskDTO);
        assertThat(resp.getSolutions()).isEqualTo(solutionDTOs);
    }

    @Test
    @DisplayName("getStudentLesson: бросает LessonNotFound, если урок не найден")
    void getStudentLesson_NotFound_ShouldThrow() {
        when(lessonRepository.findLessonProjectionById(lessonId)).thenReturn(null);

        assertThatThrownBy(() -> lessonService.getStudentLesson(lessonId))
            .isInstanceOf(LessonNotFound.class)
            .hasMessage("Lesson not found");
    }

    @Test
    @DisplayName("getStudentLesson: нет задания — возвращает пустой ответ")
    void getStudentLesson_NoTask_ShouldReturnEmptyResponse() {
        when(lessonRepository.findLessonProjectionById(lessonId)).thenReturn(lessonProj);
        when(teacherRepository.findFullNameByTeacherId(any())).thenReturn(fullNameProj);
        when(lessonMapper.toDTO(lessonProj, fullNameProj)).thenReturn(studentDTO);
        when(taskRepository.findTaskByLessonId(lessonId)).thenReturn(null);

        StudentLessonResponse resp = lessonService.getStudentLesson(lessonId);

        assertThat(resp.getLesson()).isEqualTo(studentDTO);
        assertThat(resp.getTask()).isNull();
        assertThat(resp.getSolution()).isNull();
    }

    @Test
    @DisplayName("getStudentLesson: есть задание и решение — возвращает полный ответ")
    void getStudentLesson_WithTaskAndSolution_ShouldReturnFull() {
        // 1) projection и репозитории
        when(lessonRepository.findLessonProjectionById(lessonId))
                .thenReturn(lessonProj);
        when(teacherRepository.findFullNameByTeacherId(lessonProj.getTeacherId()))
                .thenReturn(fullNameProj);
        // 2) маппер урока
        when(lessonMapper.toDTO(lessonProj, fullNameProj))
                .thenReturn(studentDTO);
        // 3) задача
        when(taskRepository.findTaskByLessonId(lessonId))
                .thenReturn(rawTask);
        // 4) прокидываем id в DTO и маппер задачи
        taskDTO.setId(rawTask.getId());
        when(taskMapper.toDTO(rawTask))
                .thenReturn(taskDTO);
        // 5) решение
        when(solutionRepository.findSolutionByTaskId(rawTask.getId()))
                .thenReturn(new Solution());
        when(solutionMapper.toDto(any(Solution.class)))
                .thenReturn(solutionDTO);

        // Выполняем
        StudentLessonResponse resp = lessonService.getStudentLesson(lessonId);

        // Проверяем
        assertThat(resp.getLesson()).isEqualTo(studentDTO);
        assertThat(resp.getTask()).isEqualTo(taskDTO);
        assertThat(resp.getSolution()).isEqualTo(solutionDTO);
    }
}
