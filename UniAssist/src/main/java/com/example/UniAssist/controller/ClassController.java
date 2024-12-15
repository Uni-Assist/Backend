package com.example.UniAssist.controller;

import com.example.UniAssist.model.ClassDTO;
import com.example.UniAssist.service.ClassService;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/student")
    public ResponseEntity<List<ClassDTO.StudentSchedule>> getStudentSchedule(
            @RequestParam("date") String date,
            @RequestParam("student_id") UUID studentId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Class> classes = classService.getStudentSchedule(groupId, LocalDate.parse(date));
        List<ClassDTO.StudentSchedule> result = classes.stream()
                .map(ClassDTO.StudentSchedule::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<ClassDTO.TeacherSchedule>> getTeacherSchedule(
            @RequestParam("date") String date,
            @RequestParam("teacher_id") UUID teacherId) {
        List<Class> classes = classService.getTeacherSchedule(teacherId, LocalDate.parse(date));
        List<ClassDTO.TeacherSchedule> result = classes.stream()
                .map(ClassDTO.TeacherSchedule::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}