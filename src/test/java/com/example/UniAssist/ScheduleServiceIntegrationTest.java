package com.example.UniAssist;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.mapper.FullNameMapper;                  // ИЗМЕНЕНИЕ 1: мокаем маппер
import com.example.UniAssist.mapper.ScheduleMapper;                  // ИЗМЕНЕНИЕ 2: мокаем маппер
import com.example.UniAssist.model.dto.FullNameDTO;                  // ИЗМЕНЕНИЕ 3: нужен DTO для fullNameMapper
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.GroupNameProjection;
import com.example.UniAssist.model.projection.ScheduleProjection;
import com.example.UniAssist.model.projection.TaskHeaderProjection;
import com.example.UniAssist.repository.*;
import com.example.UniAssist.service.ScheduleService;
import com.example.UniAssist.type.LessonType;
import org.junit.jupiter.api.BeforeEach;                            // ИЗМЕНЕНИЕ 4: добавили BeforeEach
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceIntegrationTest {

    @Mock private ScheduleRepository scheduleRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private GroupRepository groupRepository;
    @Mock private FullNameMapper fullNameMapper;          // ИЗМЕНЕНИЕ 1
    @Mock private ScheduleMapper scheduleMapper;          // ИЗМЕНЕНИЕ 2

    @InjectMocks private ScheduleService scheduleService;

    private final UUID STUDENT_ID = UUID.fromString("120ce5a6-d320-4c18-b8ff-f3c8ae962a5f");
    private final UUID TEACHER_ID = UUID.fromString("39f4a254-0619-4a30-a7d1-d49c0e7ad394");
    private final UUID GROUP_ID   = UUID.fromString("9f7706b2-ce24-4042-af4a-4302a23d521f");
    private final UUID LESSON_ID  = UUID.fromString("e01733c0-61f3-4d71-9713-15d874c2cd10");
    private final LocalDate TEST_DATE = LocalDate.of(2024, 12, 10);

    private ScheduleProjection proj;
    private TaskHeaderProjection taskHeader;
    private FullNameProjection fullNameProj;
    private GroupNameProjection groupNameProj;
    private FullNameDTO fullNameDTO;                       // ИЗМЕНЕНИЕ 3
    private StudentScheduleDTO expectedStudentDTO;          // ИЗМЕНЕНИЕ 5: ожидаемый результат
    private TeacherScheduleDTO expectedTeacherDTO;          // ИЗМЕНЕНИЕ 5

    @BeforeEach
    void init() {
        // фабрика проекции
        proj = new ScheduleProjection() {
            @Override public UUID getId()            { return LESSON_ID; }
            @Override public String getSubjectName() { return "Сети ЭВМ и телекомуникации"; }
            @Override public UUID getGroupId()       { return GROUP_ID; }
            @Override public UUID getTeacherId()     { return TEACHER_ID; }
            @Override public LocalTime getStartTime(){ return LocalTime.of(10,0); }
            @Override public LocalTime getEndTime()  { return LocalTime.of(11,30); }
            @Override public String getClassroom()   { return "101"; }
            @Override public LessonType getType()      { return PRACTICE; }
        };
        taskHeader = new TaskHeaderProjection() {
            @Override public UUID getLessonId() { return LESSON_ID; }
            @Override public String getHeader() { return "Написать эссе на тему, почему вы любите C++"; }
        };
        fullNameProj = new FullNameProjection() {
            @Override public UUID getId()          { return TEACHER_ID; }
            @Override public String getLastName()  { return "Петров"; }
            @Override public String getFirstName() { return "Иван"; }
            @Override public String getMiddleName(){ return "Иванович"; }
        };
        groupNameProj = new GroupNameProjection() {
            @Override public UUID getGroupId()    { return GROUP_ID; }
            @Override public String getGroupName(){ return "ИВ-221"; }
        };

        // DTO, которые вернут мапперы
        fullNameDTO = new FullNameDTO("Иван", "Петров", "Иванович");  // ИЗМЕНЕНИЕ 3
        expectedStudentDTO = new StudentScheduleDTO(                    // ИЗМЕНЕНИЕ 5
                LESSON_ID,
                "Сети ЭВМ и телекомуникации",
                fullNameDTO,
                LocalTime.of(10,0),
                LocalTime.of(11,30),
                "101",
                PRACTICE,
                "Написать эссе на тему, почему вы любите C++"
        );
        expectedTeacherDTO = new TeacherScheduleDTO(                  // ИЗМЕНЕНИЕ 5
                LESSON_ID,
                "Сети ЭВМ и телекомуникации",
                "ИВ-221",
                LocalTime.of(10,0),
                LocalTime.of(11,30),
                "101",
                PRACTICE,
                "Написать эссе на тему, почему вы любите C++"
        );
    }

    @Test
    void getStudentSchedule_ShouldReturnFullScheduleWithAllDetails() {
        when(studentRepository.findGroupIdByStudentId(STUDENT_ID)).thenReturn(GROUP_ID);
        when(scheduleRepository.findScheduleByGroupAndDate(GROUP_ID, TEST_DATE))
                .thenReturn(List.of(proj));
        when(taskRepository.findTaskHeadersByLessonIds(List.of(LESSON_ID)))
                .thenReturn(List.of(taskHeader));
        when(teacherRepository.findFullNamesByTeacherIds(List.of(TEACHER_ID)))
                .thenReturn(List.of(fullNameProj));

        // ИЗМЕНЕНИЕ 6: мокаем fullNameMapper
        when(fullNameMapper.toDTO(fullNameProj)).thenReturn(fullNameDTO);
        // ИЗМЕНЕНИЕ 6: мокаем scheduleMapper для студента
        when(scheduleMapper.toDTO(eq(proj), eq("Написать эссе на тему, почему вы любите C++"), eq(fullNameDTO)))
                .thenReturn(expectedStudentDTO);

        List<StudentScheduleDTO> result = scheduleService.getStudentSchedule(STUDENT_ID, TEST_DATE);

        assertThat(result).containsExactly(expectedStudentDTO);

        verify(studentRepository).findGroupIdByStudentId(STUDENT_ID);
        verify(scheduleRepository).findScheduleByGroupAndDate(GROUP_ID, TEST_DATE);
        verify(taskRepository).findTaskHeadersByLessonIds(List.of(LESSON_ID));
        verify(teacherRepository).findFullNamesByTeacherIds(List.of(TEACHER_ID));
        verify(fullNameMapper).toDTO(fullNameProj);               // ИЗМЕНЕНИЕ 6
        verify(scheduleMapper).toDTO(proj, "Написать эссе на тему, почему вы любите C++", fullNameDTO); // ИЗМЕНЕНИЕ 6
    }

    @Test
    void getTeacherSchedule_ShouldIncludeAllGroupDetailsAndTasks() {
        when(scheduleRepository.findScheduleByTeacherAndDate(TEACHER_ID, TEST_DATE))
                .thenReturn(List.of(proj));
        when(taskRepository.findTaskHeadersByLessonIds(List.of(LESSON_ID)))
                .thenReturn(List.of(taskHeader));
        when(groupRepository.findGroupNamesByIds(List.of(GROUP_ID)))
                .thenReturn(List.of(groupNameProj));

        // ИЗМЕНЕНИЕ 6: мокаем scheduleMapper для преподавателя
        when(scheduleMapper.toDTO(eq(proj), eq("Написать эссе на тему, почему вы любите C++"), eq("ИВ-221")))
                .thenReturn(expectedTeacherDTO);

        List<TeacherScheduleDTO> result = scheduleService.getTeacherSchedule(TEACHER_ID, TEST_DATE);

        assertThat(result).containsExactly(expectedTeacherDTO);

        verify(scheduleRepository).findScheduleByTeacherAndDate(TEACHER_ID, TEST_DATE);
        verify(taskRepository).findTaskHeadersByLessonIds(List.of(LESSON_ID));
        verify(groupRepository).findGroupNamesByIds(List.of(GROUP_ID));
        verify(scheduleMapper).toDTO(proj, "Написать эссе на тему, почему вы любите C++", "ИВ-221"); // ИЗМЕНЕНИЕ 6
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
