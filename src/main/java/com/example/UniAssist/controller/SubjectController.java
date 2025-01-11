package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.service.SubjectService;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TaskRepository;
import com.example.UniAssist.model.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/schedule")
public class ClassController {

    private SubjectService classService;
    private StudentRepository studentRepository;
    private TaskRepository taskRepository;

    @Autowired
    public ClassController(
        SubjectService classService,
        StudentRepository studentRepository,
        TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
        this.classService = classService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentScheduleDTO>> getStudentSchedule(
            @RequestParam("date") String date,
            @RequestParam("student_id") UUID studentId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Subject> subjects = classService.getStudentSchedule(groupId, LocalDate.parse(date));

        List<UUID> subjectIds = subjects.stream().map(Subject::getId).collect(Collectors.toList());
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(subjectIds);
        Map<UUID, String> taskHeaders = rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getClassId, TaskHeaderProjection::getHeader));

        List<StudentScheduleDTO> result = subjects.stream()
                .map(clazz -> {
                    String taskHeader = taskHeaders.getOrDefault(clazz.getId(), null);
                    return StudentScheduleDTO.fromEntity(clazz, taskHeader);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherScheduleDTO>> getTeacherSchedule(
            @RequestParam("date") String date,
            @RequestParam("teacher_id") UUID teacherId) {
        List<Subject> subjects = classService.getTeacherSchedule(teacherId, LocalDate.parse(date));

        List<UUID> subjectIds = subjects.stream().map(Subject::getId).collect(Collectors.toList());
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(subjectIds);
        Map<UUID, String> taskHeaders = rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getClassId, TaskHeaderProjection::getHeader));

        List<TeacherScheduleDTO> result = subjects.stream()
                .map(clazz -> {
                    String taskHeader = taskHeaders.getOrDefault(clazz.getId(), null);
                    return TeacherScheduleDTO.fromEntity(clazz, taskHeader);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}