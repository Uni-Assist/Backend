package com.example.UniAssist;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.projection.FullNameProjection;
import com.example.UniAssist.projection.GroupNameProjection;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.repository.*;
import com.example.UniAssist.service.ScheduleService;
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

import static com.example.UniAssist.type.LessonType.PRACTICE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceIntegrationTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private final UUID STUDENT_ID = UUID.fromString("120ce5a6-d320-4c18-b8ff-f3c8ae962a5f");
    private final UUID TEACHER_ID = UUID.fromString("39f4a254-0619-4a30-a7d1-d49c0e7ad394");
    private final UUID GROUP_ID = UUID.fromString("9f7706b2-ce24-4042-af4a-4302a23d521f");
    private final UUID LESSON_ID = UUID.fromString("e01733c0-61f3-4d71-9713-15d874c2cd10");
    private final LocalDate TEST_DATE = LocalDate.of(2024, 12, 10);

    @Test
    void getStudentSchedule_ShouldReturnFullScheduleWithAllDetails() {
        StudentScheduleDTO baseSchedule = createStudentSchedule();
        when(studentRepository.findGroupIdByStudentId(STUDENT_ID)).thenReturn(GROUP_ID);
        when(scheduleRepository.findScheduleByGroupAndDate(GROUP_ID, TEST_DATE))
                .thenReturn(List.of(baseSchedule));
        when(taskRepository.findTaskHeadersByLessonIds(anyList()))
                .thenReturn(List.of(createTaskHeader()));
        when(teacherRepository.findFullNamesByTeacherIds(anyList()))
                .thenReturn(List.of(createTeacherFullName()));
        List<StudentScheduleDTO> result = scheduleService.getStudentSchedule(STUDENT_ID, TEST_DATE);
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(LESSON_ID);
                    assertThat(dto.getSubjectName()).isEqualTo("Сети ЭВМ и телекомуникации");
                    assertThat(dto.getFullName()).isEqualTo("Петров Иван Иванович");
                    assertThat(dto.getClassroom()).isEqualTo("101");
                    assertThat(dto.getType()).isEqualTo(PRACTICE);
                    assertThat(dto.getHeader()).isEqualTo("Написать эссе на тему, почему вы любите C++");
                });

        verifyRepositoriesInteraction();
    }

    @Test
    void getTeacherSchedule_ShouldIncludeAllGroupDetailsAndTasks() {
        TeacherScheduleDTO baseSchedule = createTeacherSchedule();
        when(scheduleRepository.findScheduleByTeacherAndDate(TEACHER_ID, TEST_DATE))
                .thenReturn(List.of(baseSchedule));
        when(taskRepository.findTaskHeadersByLessonIds(anyList()))
                .thenReturn(List.of(createTaskHeader()));
        when(groupRepository.findGroupNamesByIds(anyList()))
                .thenReturn(List.of(createGroupName()));
        List<TeacherScheduleDTO> result = scheduleService.getTeacherSchedule(TEACHER_ID, TEST_DATE);
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(LESSON_ID);
                    assertThat(dto.getSubjectName()).isEqualTo("Сети ЭВМ и телекомуникации");
                    assertThat(dto.getGroupName()).isEqualTo("ИВ-221");
                    assertThat(dto.getClassroom()).isEqualTo("101");
                    assertThat(dto.getType()).isEqualTo(PRACTICE);
                    assertThat(dto.getHeader()).isEqualTo("Написать эссе на тему, почему вы любите C++");
                });

        verifyTeacherRepositoriesInteraction();
    }

    private StudentScheduleDTO createStudentSchedule() {
        StudentScheduleDTO dto = new StudentScheduleDTO();
        dto.setId(LESSON_ID);
        dto.setSubjectName("Сети ЭВМ и телекомуникации");
        dto.setTeacherId(TEACHER_ID);
        dto.setStartTime(LocalTime.of(10, 0));
        dto.setEndTime(LocalTime.of(11, 30));
        dto.setClassroom("101");
        dto.setType(PRACTICE);
        return dto;
    }

    private TeacherScheduleDTO createTeacherSchedule() {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.setId(LESSON_ID);
        dto.setSubjectName("Сети ЭВМ и телекомуникации");
        dto.setGroupId(GROUP_ID);
        dto.setStartTime(LocalTime.of(10, 0));
        dto.setEndTime(LocalTime.of(11, 30));
        dto.setClassroom("101");
        dto.setType(PRACTICE);
        return dto;
    }

    private TaskHeaderProjection createTaskHeader() {
        return new TaskHeaderProjection() {
            @Override
            public UUID getLessonId() { return LESSON_ID; }
            @Override
            public String getHeader() { return "Написать эссе на тему, почему вы любите C++"; }
        };
    }

    private FullNameProjection createTeacherFullName() {
        return new FullNameProjection() {
            @Override
            public UUID getId() { return TEACHER_ID; }
            @Override
            public String getLastName() { return "Петров"; }
            @Override
            public String getFirstName() { return "Иван"; }
            @Override
            public String getMiddleName() { return "Иванович"; }
        };
    }

    private GroupNameProjection createGroupName() {
        return new GroupNameProjection() {
            @Override
            public UUID getGroupId() { return GROUP_ID; }
            @Override
            public String getGroupName() { return "ИВ-221"; }
        };
    }

    private void verifyRepositoriesInteraction() {
        verify(studentRepository).findGroupIdByStudentId(STUDENT_ID);
        verify(scheduleRepository).findScheduleByGroupAndDate(GROUP_ID, TEST_DATE);
        verify(taskRepository).findTaskHeadersByLessonIds(List.of(LESSON_ID));
        verify(teacherRepository).findFullNamesByTeacherIds(List.of(TEACHER_ID));
    }

    private void verifyTeacherRepositoriesInteraction() {
        verify(scheduleRepository).findScheduleByTeacherAndDate(TEACHER_ID, TEST_DATE);
        verify(taskRepository).findTaskHeadersByLessonIds(List.of(LESSON_ID));
        verify(groupRepository).findGroupNamesByIds(List.of(GROUP_ID));
    }

    @Test
    void getStudentSchedule_ShouldThrowException_WhenNoScheduleFound() {
        when(studentRepository.findGroupIdByStudentId(STUDENT_ID)).thenReturn(GROUP_ID);
        when(scheduleRepository.findScheduleByGroupAndDate(GROUP_ID, TEST_DATE))
                .thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> scheduleService.getStudentSchedule(STUDENT_ID, TEST_DATE))
                .isInstanceOf(ScheduleNotFound.class)
                .hasMessageContaining("No lessons found");
    }
}