package com.example.UniAssist.service;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.mapper.ScheduleMapper;
import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.GroupNameProjection;
import com.example.UniAssist.model.projection.ScheduleProjection;
import com.example.UniAssist.model.projection.TaskHeaderProjection;
import com.example.UniAssist.repository.*;
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
    private final TeacherRepository teacherRepository;
    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final FullNameMapper fullNameMapper;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleService(
            ScheduleRepository scheduleRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            TaskRepository taskRepository,
            GroupRepository groupRepository,
            FullNameMapper fullNameMapper,
            ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.fullNameMapper = fullNameMapper;
        this.scheduleMapper = scheduleMapper;
    }

    public List<StudentScheduleDTO> getStudentSchedule(UUID studentId, LocalDate date) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<ScheduleProjection> schedule = scheduleRepository.findScheduleByGroupAndDate(groupId, date);
        if (schedule.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> lessonsIds = schedule.stream().map(ScheduleProjection::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(lessonsIds);

        List<UUID> teacherIds = schedule.stream().map(ScheduleProjection::getTeacherId).collect(Collectors.toList());
        Map<UUID, FullNameDTO> fullNames = fetchFullNames(teacherIds);

        return schedule.stream()
                .map(lesson -> scheduleMapper.toDTO(
                        lesson,
                        taskHeaders.getOrDefault(lesson.getId(), null),
                        fullNames.getOrDefault(lesson.getTeacherId(), null)))
                .collect(Collectors.toList());
    }

    public List<TeacherScheduleDTO> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<ScheduleProjection> schedule = scheduleRepository.findScheduleByTeacherAndDate(teacherId, date);
        if (schedule.isEmpty()) {
            throw new ScheduleNotFound("No lessons found");
        }

        List<UUID> lessonsIds = schedule.stream().map(ScheduleProjection::getId).collect(Collectors.toList());
        Map<UUID, String> taskHeaders = fetchTaskHeaders(lessonsIds);

        List<UUID> groupIds = schedule.stream().map(ScheduleProjection::getGroupId).collect(Collectors.toList());
        Map<UUID, String> groupNames = fetchGroupNames(groupIds);

        return schedule.stream()
                .map(lesson -> scheduleMapper.toDTO(
                        lesson,
                        taskHeaders.getOrDefault(lesson.getId(), null),
                        groupNames.getOrDefault(lesson.getGroupId(), null)))
                .collect(Collectors.toList());
    }

    private Map<UUID, String> fetchTaskHeaders(List<UUID> lessonIds) {
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByLessonIds(lessonIds);
        return rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getLessonId, TaskHeaderProjection::getHeader));
    }

    private Map<UUID, FullNameDTO> fetchFullNames(List<UUID> userIds) {
        List<FullNameProjection> rawFullNames = teacherRepository.findFullNamesByTeacherIds(userIds);
        return rawFullNames.stream()
                .collect(Collectors.toMap(FullNameProjection::getId, fullNameMapper::toDTO));
    }

    private Map<UUID, String> fetchGroupNames(List<UUID> groupIds) {
        List<GroupNameProjection> rawGroupNames = groupRepository.findGroupNamesByIds(groupIds);
        return rawGroupNames.stream()
                .collect(Collectors.toMap(GroupNameProjection::getGroupId, GroupNameProjection::getGroupName));
    }
}