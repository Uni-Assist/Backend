package com.example.UniAssist.service;

import com.example.UniAssist.mapper.StudentScheduleMapper;
import com.example.UniAssist.mapper.TeacherScheduleMapper;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.model.entity.Subject;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.SubjectRepository;
import com.example.UniAssist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public SubjectService(
            SubjectRepository subjectRepository,
            StudentRepository studentRepository,
            TaskRepository taskRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
    }

    public List<StudentScheduleDTO> getStudentSchedule(UUID studentId, LocalDate date) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Subject> subjects = subjectRepository.findSubjectsByGroupAndDate(groupId, date);
        if (subjects.isEmpty()) {
            throw new ScheduleNotFound("No subjects found");
        }

        List<UUID> subjectIds = subjects.stream().map(Subject::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(subjectIds);

        return subjects.stream()
                .map(subject -> StudentScheduleMapper.toDTO(subject, taskHeaders.getOrDefault(subject.getId(), null)))
                .collect(Collectors.toList());
    }

    public List<TeacherScheduleDTO> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<Subject> subjects = subjectRepository.findSubjectsByTeacherAndDate(teacherId, date);
        if (subjects.isEmpty()) {
            throw new ScheduleNotFound("No subjects found");
        }

        List<UUID> subjectIds = subjects.stream().map(Subject::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(subjectIds);

        return subjects.stream()
                .map(subject -> TeacherScheduleMapper.toDTO(subject, taskHeaders.getOrDefault(subject.getId(), null)))
                .collect(Collectors.toList());
    }

    private Map<UUID, String> fetchTaskHeaders(List<UUID> subjectIds) {
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(subjectIds);
        return rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getClassId, TaskHeaderProjection::getHeader));
    }
}