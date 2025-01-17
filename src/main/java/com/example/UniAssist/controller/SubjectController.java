package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.service.SubjectService;
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
@RequestMapping("/api/v1/schedule")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentScheduleDTO>> getStudentSchedule(
            @RequestParam("date") String date,
            @RequestParam("student_id") UUID studentId) {
        List<StudentScheduleDTO> schedule = subjectService.getStudentSchedule(studentId, LocalDate.parse(date));
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherScheduleDTO>> getTeacherSchedule(
            @RequestParam("date") String date,
            @RequestParam("teacher_id") UUID teacherId) {
        List<TeacherScheduleDTO> schedule = subjectService.getTeacherSchedule(teacherId, LocalDate.parse(date));
        return ResponseEntity.ok(schedule);
    }
}