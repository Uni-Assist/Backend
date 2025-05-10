package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentScheduleDTO>> getStudentSchedule(
            @RequestParam("date") String date,
            @AuthenticationPrincipal String studentId) {
        List<StudentScheduleDTO> schedule = scheduleService.getStudentSchedule(UUID.fromString(studentId), LocalDate.parse(date));
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherScheduleDTO>> getTeacherSchedule(
            @RequestParam("date") String date,
            @AuthenticationPrincipal String teacherId) {
        List<TeacherScheduleDTO> schedule = scheduleService.getTeacherSchedule(UUID.fromString(teacherId), LocalDate.parse(date));
        return ResponseEntity.ok(schedule);
    }
}