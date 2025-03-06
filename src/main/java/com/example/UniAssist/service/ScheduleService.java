package com.example.UniAssist.service;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.mapper.StudentScheduleMapper;
import com.example.UniAssist.mapper.TeacherScheduleMapper;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.repository.ScheduleRepository;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ScheduleService(
            ScheduleRepository scheduleRepository,
            StudentRepository studentRepository,
            TaskRepository taskRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
    }

    public List<StudentScheduleDTO> getStudentSchedule(UUID studentId, LocalDate date) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<StudentScheduleDTO> schedule = scheduleRepository.findScheduleByGroupAndDate(groupId, date);
        if (schedule.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> lessonsIds = schedule.stream().map(StudentScheduleDTO::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(lessonsIds);

        return schedule.stream()
                .map(lesson -> StudentScheduleMapper.toDTO(lesson, taskHeaders.getOrDefault(lesson.getId(), null)))
                .collect(Collectors.toList());
    }

    public List<TeacherScheduleDTO> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<TeacherScheduleDTO> schedule = scheduleRepository.findScheduleByTeacherAndDate(teacherId, date);
        if (schedule.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> lessonsIds = schedule.stream().map(TeacherScheduleDTO::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(lessonsIds);

        return schedule.stream()
                .map(lesson -> TeacherScheduleMapper.toDTO(lesson, taskHeaders.getOrDefault(lesson.getId(), null)))
                .collect(Collectors.toList());
    }

    private Map<UUID, String> fetchTaskHeaders(List<UUID> lessonIds) {
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByLessonIds(lessonIds);
        return rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getLessonId, TaskHeaderProjection::getHeader));
    }
}