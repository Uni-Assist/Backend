package com.example.UniAssist.controller;

import com.example.UniAssist.model.ClassDTO;
import com.example.UniAssist.projection.TaskHeaderProjection;
import com.example.UniAssist.service.ClassService;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TaskRepository;
import com.example.UniAssist.model.Class;
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

    private ClassService classService;
    private StudentRepository studentRepository;
    private TaskRepository taskRepository;

    @Autowired
    public ClassController(
        ClassService classService,
        StudentRepository studentRepository,
        TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
        this.classService = classService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public ResponseEntity<List<ClassDTO.StudentSchedule>> getStudentSchedule(
            @RequestParam("date") String date,
            @RequestParam("student_id") UUID studentId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Class> classes = classService.getStudentSchedule(groupId, LocalDate.parse(date));

        List<UUID> classIds = classes.stream().map(Class::getId).collect(Collectors.toList());
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(classIds);
        Map<UUID, String> taskHeaders = rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getClassId, TaskHeaderProjection::getHeader));

        List<ClassDTO.StudentSchedule> result = classes.stream()
                .map(clazz -> {
                    String taskHeader = taskHeaders.getOrDefault(clazz.getId(), null);
                    return ClassDTO.StudentSchedule.fromEntity(clazz, taskHeader);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<ClassDTO.TeacherSchedule>> getTeacherSchedule(
            @RequestParam("date") String date,
            @RequestParam("teacher_id") UUID teacherId) {
        List<Class> classes = classService.getTeacherSchedule(teacherId, LocalDate.parse(date));

        List<UUID> classIds = classes.stream().map(Class::getId).collect(Collectors.toList());
        List<TaskHeaderProjection> rawTaskHeaders = taskRepository.findTaskHeadersByClassIds(classIds);
        Map<UUID, String> taskHeaders = rawTaskHeaders.stream()
                .collect(Collectors.toMap(TaskHeaderProjection::getClassId, TaskHeaderProjection::getHeader));

        List<ClassDTO.TeacherSchedule> result = classes.stream()
                .map(clazz -> {
                    String taskHeader = taskHeaders.getOrDefault(clazz.getId(), null);
                    return ClassDTO.TeacherSchedule.fromEntity(clazz, taskHeader);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}