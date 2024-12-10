package com.example.UniAssist.controller;

import com.example.UniAssist.service.ClassService;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.model.Views;
import com.example.UniAssist.model.Class;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private  StudentRepository studentRepository;

    @JsonView(Views.WithoutTeacherId.class)
    @GetMapping("/student")
    public ResponseEntity<List<Class>> getStudentSchedule(
            @RequestParam("date") String date,
            @RequestParam("student_id") UUID studentId) {
        UUID groupId = studentRepository.findGroupIdByStudentId(studentId);
        List<Class> classes = classService.getStudentSchedule(groupId, LocalDate.parse(date));
        return ResponseEntity.ok(classes);
    }

    @JsonView(Views.WithoutGroupId.class)
    @GetMapping("/teacher")
    public ResponseEntity<List<Class>> getTeacherSchedule(
            @RequestParam("date") String date,
            @RequestParam("teacher_id") UUID teacherId) {
        List<Class> classes = classService.getTeacherSchedule(teacherId, LocalDate.parse(date));
        return ResponseEntity.ok(classes);
    }
}