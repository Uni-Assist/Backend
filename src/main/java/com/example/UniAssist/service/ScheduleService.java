package com.example.UniAssist.service;

import com.example.UniAssist.mapper.StudentScheduleMapper;
import com.example.UniAssist.mapper.TeacherScheduleMapper;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.model.entity.Lesson;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.LessonRepository;
import com.example.UniAssist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ScheduleService(
            LessonRepository lessonRepository,
            StudentRepository studentRepository,
            TaskRepository taskRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
    }

    public List<StudentScheduleDTO> getStudentSchedule(UUID studentId, LocalDate date) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Lesson> lessons = lessonRepository.findLessonsByGroupAndDate(groupId, date);
        if (lessons.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> subjectIds = lessons.stream().map(Lesson::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(subjectIds);

        return lessons.stream()
                .map(lesson -> StudentScheduleMapper.toDTO(lesson, taskHeaders.getOrDefault(lesson.getId(), null)))
                .collect(Collectors.toList());
    }

    public List<TeacherScheduleDTO> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<Lesson> lessons = lessonRepository.findLessonsByTeacherAndDate(teacherId, date);
        if (lessons.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> subjectIds = lessons.stream().map(Lesson::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(subjectIds);

        return lessons.stream()
                .map(lesson -> TeacherScheduleMapper.toDTO(lesson, taskHeaders.getOrDefault(lesson.getId(), null)))
                .collect(Collectors.toList());
    }

    private Map<UUID, String> fetchTaskHeaders(List<UUID> lessonIds) {
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(lessonIds);
        return rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getLessonId, TaskHeaderProjection::getHeader));
    }
}